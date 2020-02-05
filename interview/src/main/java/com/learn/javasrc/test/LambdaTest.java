package com.learn.javasrc.test;

import com.alibaba.fastjson.JSON;
import com.learn.pojo.Course;
import com.learn.pojo.StudentDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * lambda表达式测试
 *
 * @author lfq
 */
@Slf4j
public class LambdaTest {

    private List<StudentDTO> studentDTOList = StudentDTO.studentDTOList;

    @Before
    public void init() {
        log.info("studentDTO init");
        studentDTOList = StudentDTO.studentDTOList;
    }


    @Test
    public void simple() {
        Runnable r = () -> log.info(Thread.currentThread().getName() + ",say Hello");
        r.run();
    }

    @Test
    public void testFilter() {
        studentDTOList.stream().filter(stu -> stu.getId() < 3).collect(Collectors.toList()).
                forEach(System.out::println);
    }

    @Test
    public void testMap() {
        List<Integer> ids = studentDTOList.stream()
                .mapToInt(stu -> Integer.parseInt(stu.getId() + ""))
                // 一定要有 mapToObj，因为 mapToInt 返回的是 IntStream，因为已经确定是 int 类型了
                // 所有没有泛型的，而 Collectors.toList() 强制要求有泛型的流，所以需要使用 mapToObj
                // 方法返回有泛型的流
                .boxed()
                .collect(Collectors.toList());
        log.info("TestMapToInt result  {}", JSON.toJSONString(ids));

        // 计算学生总分
        Double sumScope = studentDTOList.stream()
                .mapToDouble(StudentDTO::getScope)
                // DoubleStream/IntStream 有许多 sum（求和）、min（求最小值）、max（求最大值）、average（求平均值）等方法
                .sum();
        log.info("TestMapToInt 学生总分为：  {}", sumScope);

        Double max = studentDTOList.stream().mapToDouble(StudentDTO::getScope).max().getAsDouble();
        Double min = studentDTOList.stream().mapToDouble(StudentDTO::getScope).min().getAsDouble();
        Double average = studentDTOList.stream().mapToDouble(StudentDTO::getScope).average().getAsDouble();
        log.info("TestMapToInt 学生最高分为：{}，最低分为: {},平均分为：{}", max, min, average);
    }

    @Test
    public void testFlatMap() {
        // 计算学生所有的学习课程，flatMap 返回 List<课程> 格式
        List<Course> courses = studentDTOList.stream().flatMap(s -> s.getCourseList().stream())
                .collect(Collectors.toList());
        log.info("TestMapToInt flatMap 计算学生的所有学习课程如下 {}", JSON.toJSONString(courses));

        // 计算学生所有的学习课程，map 返回两层课程嵌套格式
        List<List<Course>> courses2 = studentDTOList.stream().map(StudentDTO::getCourseList)
                .collect(Collectors.toList());
        log.info("TestMapToInt map 计算学生的所有学习课程如下 {}", JSON.toJSONString(courses2));

        List<Stream<Course>> courses3 = studentDTOList.stream().map(s -> s.getCourseList().stream())
                .collect(Collectors.toList());
        log.info("TestMapToInt map 计算学生的所有学习课程如下  {}", JSON.toJSONString(courses3));
    }

    @Test
    public void testDistinct() {
        // 得到学生所有的名字，要求是去重过的
        List<String> beforeNames = studentDTOList.stream().map(StudentDTO::getName).collect(Collectors.toList());
        log.info("TestDistinct 没有去重前的学生名单 {}", JSON.toJSONString(beforeNames));

        List<String> distinctNames = beforeNames.stream().distinct().collect(Collectors.toList());
        log.info("TestDistinct 去重后的学生名单 {}", JSON.toJSONString(distinctNames));

        // 连起来写
        List<String> names = studentDTOList.stream()
                .map(StudentDTO::getName)
                .distinct()
                .collect(Collectors.toList());
        log.info("TestDistinct 去重后的学生名单 {}", JSON.toJSONString(names));
    }

    @Test
    public void testSorted() {
        // 学生按照学号排序
        List<String> beforeCodes = studentDTOList.stream().map(StudentDTO::getCode).collect(Collectors.toList());
        log.info("TestSorted 按照学号排序之前 {}", JSON.toJSONString(beforeCodes));

        List<String> sortedCodes = beforeCodes.stream().sorted().collect(Collectors.toList());
        log.info("TestSorted 按照学号排序之后 is {}", JSON.toJSONString(sortedCodes));

        // 直接连起来写
        List<String> codes = studentDTOList.stream()
                .map(StudentDTO::getCode)
                // 等同于.sorted(Comparator.naturalOrder()) 自然排序
                .sorted()
                .collect(Collectors.toList());
        log.info("TestSorted 自然排序 is {}", JSON.toJSONString(codes));

        // 自定义排序器
        List<String> codes2 = studentDTOList.stream()
                .map(StudentDTO::getCode)
                // 反自然排序
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        log.info("TestSorted 反自然排序 is {}", JSON.toJSONString(codes2));
    }

    @Test
    public void testPeek() {
        studentDTOList.stream().map(StudentDTO::getCode)
                .peek(s -> log.info("当前循环的学号是{}", s))
                .collect(Collectors.toList());
    }

    @Test
    public void testLimit() {
        List<String> beforeCodes = studentDTOList.stream().map(StudentDTO::getCode).collect(Collectors.toList());
        log.info("TestLimit 限制之前学生的学号为 {}", JSON.toJSONString(beforeCodes));

        List<String> limitCodes = beforeCodes.stream()
                .limit(2L)
                .collect(Collectors.toList());
        log.info("TestLimit 限制最大限制 2 个学生的学号 {}", JSON.toJSONString(limitCodes));

        // 直接连起来写
        List<String> codes = studentDTOList.stream()
                .map(StudentDTO::getCode)
                .limit(2L)
                .collect(Collectors.toList());
        log.info("TestLimit 限制最大限制 2 个学生的学号 {}", JSON.toJSONString(codes));
    }

    @Test
    public void testReduce() {
        // 计算一下学生的总分数
        Double sum = studentDTOList.stream()
                .map(StudentDTO::getScope)
                // scope1 和 scope2 表示循环中的前后两个数
                .reduce(Double::sum)
                .orElse(0D);
        log.info("总成绩为 {}", sum);

        Double sum1 = studentDTOList.stream()
                .map(StudentDTO::getScope)
                // 第一个参数表示成绩的基数，会从 100 开始加
                .reduce(100D, Double::sum);
        log.info("总成绩为 {}", sum1);
    }

    @Test
    public void testFindFirst() {
        Long id = studentDTOList.stream()
                .filter(s -> StringUtils.equals(s.getName(), "小美"))
                // 同学中有两个叫小美的，这里匹配到第一个就返回
                .findFirst()
                .get().getId();

        log.info("testFindFirst 小美同学的 ID {}", id);

        // 防止空指针
        Long id2 = studentDTOList.stream()
                .filter(s -> StringUtils.equals(s.getName(), "小天"))
                .findFirst()
                // orElse 表示如果 findFirst 返回 null 的话，就返回 orElse 里的内容
                .orElse(new StudentDTO()).getId();
        log.info("testFindFirst 小天同学的 ID {}", id2);

        Optional<StudentDTO> student = studentDTOList.stream()
                .filter(s -> StringUtils.equals(s.getName(), "小天"))
                .findFirst();
        // isPresent 为 true 的话，表示 value != null，即 student.get() != null
        if (student.isPresent()) {
            log.info("testFindFirst 小天同学的 ID {}", student.get().getId());
            return;
        }
        log.info("testFindFirst 找不到名为小天的同学");
    }

    @Test
    public void testListToMap(){
        // 学生根据名字进行分类
        Map<String, List<StudentDTO>> map1 = studentDTOList.stream()
                .collect(Collectors.groupingBy(StudentDTO::getName));
        log.info("testListToMap groupingBy 学生根据名字进行分类 result is Map<String,List<StudentDTO>> {}",
                JSON.toJSONString(map1));

        // 统计姓名重名的学生有哪些
        Map<String, Set<String>> map2 = studentDTOList.stream()
                .collect(Collectors.groupingBy(StudentDTO::getName,
                        Collectors.mapping(StudentDTO::getCode, Collectors.toSet())));
        log.info("testListToMap groupingBy 统计姓名重名结果 is {}",
                JSON.toJSONString(map2));

        // 学生转化成学号为 key 的 map
        Map<String, StudentDTO> map3 = studentDTOList.stream()
                //第一个入参表示 map 中 key 的取值
                //第二个入参表示 map 中 value 的取值
                //第三个入参表示，如果前后的 key 是相同的，是覆盖还是不覆盖，(s1,s2)->s1 表示不覆盖，(s1,s2)->s2 表示覆盖
                .collect(Collectors.toMap(s -> s.getCode(), s -> s, (s1, s2) -> s1));
        log.info("testListToMap groupingBy 学生转化成学号为 key 的 map result is{}",
                JSON.toJSONString(map3));
    }

}
