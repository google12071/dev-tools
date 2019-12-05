import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learn.java.common.pojo.User;

import java.util.List;

public class GsonTest {
    private final static Gson gson=new Gson();

    public static void main(String[] args) {
        User user1 = new User(1L, "a", "AA", true, 1);
        User user2 = new User(2L, "b", "BB", false, 0);
        User user3 = new User(3L, "c", "CC", true, 1);


        List<User> userList = Lists.newArrayList(user1, user2, user3);
        String str = gson.toJson(userList);

        userList = gson.fromJson(str, new TypeToken<List<User>>() {
        }.getType());

        userList.forEach(System.out::println);
    }
}
