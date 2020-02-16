import lombok.extern.slf4j.Slf4j;

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

    public static void main(String[] args) {
        int c = 0;
        try {
            c = divideZero();
        } catch (Exception e) {
            log.info("main method deal with Exception..");
            e.printStackTrace();
        }
        log.info("c:{}",c);
    }
}
