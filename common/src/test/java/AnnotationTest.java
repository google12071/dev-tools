import com.learn.java.common.javabase.annotation.NeedAOP;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @ClassName AnnotationTest
 * @Description:判断方法是否包含自定义注解
 * @Author lfq
 * @Date 2020/2/10
 **/
@Slf4j
public class AnnotationTest {
    @NeedAOP(needAop = true, value = 20)
    public void sayHello(String message) {
        log.info("hello," + message);
    }

    @NeedAOP(needAop = false)
    public void sayHello(String message, int age) {
        log.info("hello:{},age:{}", message, age);
    }

    public static void main(String[] args) {
        AnnotationTest annotationTest = new AnnotationTest();
        Class<?> testClass = annotationTest.getClass();
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            log.info("method:{}", method.getName());
            NeedAOP needAOP = method.getDeclaredAnnotation(NeedAOP.class);
            if (needAOP != null && needAOP.needAop()) {
                log.info("needAOP:{},value:{}", needAOP.needAop(), needAOP.value());
            }
        }
    }
}
