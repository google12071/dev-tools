import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectionUtils {

    public static void main(String[] args) {
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int r = new Random().nextInt(100) + 1;
            if (!numberList.contains(r)) {
                numberList.add(r);
            }
        }

        System.out.println("original list:" + numberList);

        List<Integer> targetList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int r = new Random().nextInt(100) + 1;
            if (!targetList.contains(r)) {
                targetList.add(r);
            }
        }

        int location = new Random().nextInt(10) + 1;
        numberList.addAll(location, targetList);


        System.out.println("targetList:" + targetList);

        System.out.println("location:" + location + ",resultList:" + numberList);
    }
}
