public class NumberTest {
    public static void main(String[] args) {
        long startUid = 9211000L;
        long endUid = 9211100L;
        for (long i = startUid; i <= endUid; i++) {
            long dbMod = i % 32;
            long tbMod = i / 32 % 32;
            System.out.println("dbMod:" + dbMod + ",tbMod:" + tbMod);
        }
    }
}
