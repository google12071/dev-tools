import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.learn.java.cache.caffeine.DataObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CaffeineTest {

    @Test
    public void  setCacheManual(){
        //手动填充数据
        Cache<String, DataObject> dataObjectCache = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        String key = "A";
        DataObject obj = dataObjectCache.getIfPresent(key);
        Assert.assertNull(obj);

        dataObjectCache.put(key, new DataObject("hello"));
        obj = dataObjectCache.getIfPresent(key);
        Assert.assertNotNull(obj);

    }
}
