package com.learn.java.cache.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 利用Redis自增原子性，生成递增的唯一ID，每日零点过期
 */
public class IdGenerateUtil {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 生成唯一key
     *
     * @param key       Redis Key
     * @param prefix    key前缀
     * @param expired   是否过期
     * @param minLength 生成key最小长度
     * @return
     */
    public String generateCode(String key, String prefix, Boolean expired, Integer minLength) {
        try {
            Date date = null;
            Long id = null;
            if (expired) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                date = calendar.getTime();
            }
            id = generateId(key, date);
            return format(id, prefix, date, minLength);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 返回自增ID
     *
     * @param key
     * @param date
     * @return
     */
    private Long generateId(String key, Date date) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.expireAt(date);
        return counter.incrementAndGet();
    }

    /**
     * 格式化日期格式
     * @param id 自增ID
     * @param prefix 前缀
     * @param date 日期
     * @param minLength 最小长度
     * @return
     */
    private String format(Long id, String prefix, Date date, Integer minLength) {
        StringBuffer sb = new StringBuffer();
        sb.append(prefix);
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            sb.append(df.format(date));
        }
        String strId = String.valueOf(id);
        int length = strId.length();
        if (length < minLength) {
            for (int i = 0; i < minLength - length; i++) {
                sb.append("0");
            }
            sb.append(strId);
        } else {
            sb.append(strId);
        }

        return sb.toString();
    }

}
