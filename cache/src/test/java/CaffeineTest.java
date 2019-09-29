import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import com.learn.java.common.pojo.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CaffeineTest {

    @Test
    public void setCacheManual() {
        //手动填充数据
        Cache<String, User> userCache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
                .initialCapacity(10)
                .maximumSize(100)
                .build();
        String key = "user";
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            userCache.put(key + i, new User((long) i, "fq", "浙江杭州", true, 1));
            keys.add(key + i);
        }


        Map<String, User> userMap = userCache.getAllPresent(keys);
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            System.out.println("key:" + entry.getKey() + ",user:" + entry.getValue());
        }


        CacheStats stats = userCache.stats();
        System.out.println(stats);
    }


    @Test
    public void getCacheBatch() {

    }
}
