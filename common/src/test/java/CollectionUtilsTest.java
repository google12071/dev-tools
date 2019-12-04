import com.google.common.collect.ArrayListMultimap;

public class CollectionUtilsTest {

    public static ArrayListMultimap<String, Integer> multimap = ArrayListMultimap.create();


    public static void main(String[] args) {
        multimap.put("test", 1);
        multimap.put("test", 2);
        multimap.put("test", 3);
        multimap.put("test2",4);

        multimap.asMap().forEach((k, v) -> {
            System.out.println("k:" + k + ",v:" + v);
        });
    }
}
