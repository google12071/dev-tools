import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName OnStackTest
 * @Description:对于占用空间较小的对象，可以打开逃逸分析和标量替换来进行优化，反之产生大量GC
 * @Author lfq
 * @Date 2020/2/6
 **/
@Slf4j
public class OnStackTest {
    /**
     * user内部类
     */
    public static class User {
        public int id = 0;
        public String name = "";
    }

    /**
     * 分配空间
     */
    public static void alloc() {
        User user = new User();
        user.id = 5;
        user.name = "tom";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        log.info("alloc cost:{}", (System.currentTimeMillis() - start) + ":ms");
    }
}
