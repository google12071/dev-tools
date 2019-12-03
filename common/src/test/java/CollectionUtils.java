import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static void main(String[] args) {
        List<Integer> numberList = Arrays.asList(1, 1, 3, 5, 3, 1,5,2,3,7, 9);
        Set<Integer> numberSet = new HashSet<>();

        numberList = numberList.stream().filter(x -> numberSet.add(x)).collect(Collectors.toList());

        System.out.println(numberList);
    }
}
