package com.learn.java.common.annotation;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @ClassName SQLBuilder
 * @Description:基于注解构建SQL创建schema语句
 * @Author lfq
 * @Date 2020/5/7
 **/
public class SQLBuilder {
    public static String builderSql(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        DBTable dbTable = clazz.getAnnotation(DBTable.class);
        if (dbTable == null) {
            return null;
        }

        String tableName = dbTable.name();
        if (tableName.length() < 1) {
            tableName = clazz.getName().toLowerCase();
        }

        List<String> columnRefList = Lists.newArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
            if (fieldAnnotations.length > 0) {
                for (Annotation fieldAnnotation : fieldAnnotations) {
                    String columnStr = "";
                    if (fieldAnnotation instanceof SQLInteger) {
                        SQLInteger sqlInteger = (SQLInteger) fieldAnnotation;
                        String name = sqlInteger.name();
                        if (StringUtils.isEmpty(name)) {
                            name = field.getName().toLowerCase();
                        }
                        columnStr += "`" + name + "`" + "int ";
                        Constraint constraint = sqlInteger.constraint();
                        columnStr += getConstraints(constraint);
                        Integer defaultValue = sqlInteger.value();
                        columnStr += " DEFAULT" + "'" + defaultValue + "`";

                        boolean increment = sqlInteger.idIncrement();
                        if (increment) {
                            columnStr += " AUTO_INCREMENT";
                        }

                        String comment = sqlInteger.comment();
                        if (StringUtils.isNotEmpty(comment)) {
                            columnStr += " COMMENT" + "`" + comment + "'";
                        }
                    } else if (fieldAnnotation instanceof SQLString) {
                        SQLString sqlString = (SQLString) fieldAnnotation;
                        String name =sqlString.name();
                        if (StringUtils.isEmpty(name)) {
                            name = field.getName().toLowerCase();
                        }
                        columnStr += "`" + name + "`" + " varchar("+sqlString.length()+")";

                        Constraint constraint = sqlString.constraint();
                        columnStr += getConstraints(constraint);
                        String defaultValue = sqlString.value();
                        if (StringUtils.isNotEmpty(defaultValue)) {
                            columnStr += " DEFAULT " + "'" + defaultValue + "`";
                        }
                        String comment = sqlString.comment();
                        if (StringUtils.isNotEmpty(comment)) {
                            columnStr += " COMMENT " + "`" + comment + "'";
                        }
                    }
                    columnRefList.add(columnStr);
                }
            }
        }

        //数据库表构建语句
        StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
        for (String columnDef : columnRefList) {
            createCommand.append("\n    " + columnDef + ",");
        }

        String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");\n";

        return tableCreate;
    }


    public static String getConstraints(Constraint constraint) {
        StringBuilder sb = new StringBuilder();
        if (!constraint.allowNull()) {
            sb.append("NOT NULL");
        }
        if (constraint.unique()) {
            sb.append("UNIQUE");
        }
        if (constraint.primary()) {
            sb.append(" PRIMARY KEY");
        }
        return sb.toString();
    }

    public static void main(String[] args)throws Exception {
        String classPatch = "com.learn.java.common.annotation.pojo.User";
        String createSql = builderSql(classPatch);
        System.out.println(createSql);
    }
}
