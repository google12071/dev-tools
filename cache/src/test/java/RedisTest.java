import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learn.java.common.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
@SuppressWarnings("all")

public class RedisTest {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    private List<User> userList = Arrays.asList(new User(1L, "fq", "安徽省宿州市", true, 1),
                    new User(2L, "zh", "浙江省桐乡市", false, 0),
                    new User(3L, "dd", "黑龙江省哈尔滨市", true, 0));

    private char[] words = {'a', 'b', 'c', 'd', 'f', 'e', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'F', 'E', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private int[] digital = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    private boolean[] flag = {true, false};

      @Test
      public void redisOps(){

          String key = "user";
          userList.stream().forEach(x -> {
              redisTemplate.opsForList().leftPush(key, new Gson().toJson(x));
          });

          long size = redisTemplate.opsForList().size(key);

          while (size > 0) {
              String userStr = (String) redisTemplate.opsForList().rightPop(key);
              User user = new Gson().fromJson(userStr, User.class);
              System.out.println(user);
              size--;
          }

      }

      @Test
      public void redisPerformance() {
          Gson gson = new Gson();
          List<Integer> numberList = new ArrayList<>();
          for (int i = 0; i < 1000000; i++) {
              numberList.add(i);
          }

          long start1 = System.currentTimeMillis();
          redisTemplate.opsForValue().set("stringKey", gson.toJson(numberList));
          System.out.println("StringOps set cost:" + (System.currentTimeMillis() - start1) + ":ms");

          long start2 = System.currentTimeMillis();
          redisTemplate.opsForList().leftPushAll("listKey", numberList);
          System.out.println("listOps push cost:" + (System.currentTimeMillis() - start2) + ":ms");


          long start3=System.currentTimeMillis();
          String cache = (String) redisTemplate.opsForValue().get("stringKey");
          numberList = gson.fromJson(cache, new TypeToken<List<Integer>>() {
          }.getType());
          System.out.println("StringOps get cost:" + (System.currentTimeMillis() - start3) + ":ms");

          long start4 = System.currentTimeMillis();
          List<Object> list = redisTemplate.opsForList().range("listKey", 0, -1);
          numberList = (List<Integer>) (Object) list;
          System.out.println("listOps lrange cost:" + (System.currentTimeMillis() - start4) + ":ms");
      }

}
