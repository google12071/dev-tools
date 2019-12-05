import java.util.StringJoiner;

public class StringJoinerTest {

    public static String[] arr = new String[]{"hello", "world", "nice", "to", "meet", "you"};

    public static String getSplitStr(String split, String[] arr) {
        StringJoiner joiner = new StringJoiner(split, "1", "!");
        for (String s : arr) {
            joiner.add(s);
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        String s1 = getSplitStr(",", arr);
        String s2 = getSplitStr(";", arr);
        System.out.println(s1);
        System.out.println(s2);
    }
}
