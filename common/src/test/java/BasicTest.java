import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

import static org.springframework.data.repository.init.ResourceReader.Type.JSON;

/**
 * @ClassName BasicTest
 * @Description:
 * @Author lfq
 * @Date 2020/2/16
 **/
@Slf4j
public class BasicTest {

    public static int divideZero() {
        int a = 1;
        int b = 0;
        int c = 0;
        try {
            c = a / b;
            return c;
        } catch (Exception e) {
            log.error("throw invoke:");
            throw e;
        }
    }

    public static void arrays2List() {
        String[] strings = new String[]{"hello", "world"};
        List<String> stringList = Arrays.asList(strings);
        strings[1] = "welcome";
        System.out.println(stringList.get(1));

        List list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        list.add(1);
        list.stream().forEach(System.out::println);

    }

    /***
     * 不要在foreach中调用remove方法，请使用迭代器进行参数
     */
    @Test
    public void remove(){
        List<Integer> list = Lists.newArrayList(1, 3, 4, 5);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            log.info("value:{}", value);
            iterator.remove();
        }
        log.info("after list:{}", list);
    }


    public static void main(String[] args) {
        int c = 0;
        try {
            c = divideZero();
        } catch (Exception e) {
            log.info("main method deal with Exception..");
            e.printStackTrace();
        }
        log.info("c:{}", c);

        arrays2List();
    }
}
