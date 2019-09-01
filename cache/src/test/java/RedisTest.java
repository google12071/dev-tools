import com.google.gson.Gson;
import com.learn.java.common.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
@SuppressWarnings("all")

public class RedisTest {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    private List<User> userList = Arrays.asList(new User(1L, "fq", "安徽省宿州市", true, 1),
                    new User(2L, "zh", "浙江省桐乡市", false, 0),
                    new User(3L, "dd", "黑龙江省哈尔滨市", true, 0));

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

}
