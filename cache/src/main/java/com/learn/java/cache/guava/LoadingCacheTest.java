package com.learn.java.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName LoadingCacheTest
 * @Description:Google Cache测试类
 * 利用CacheBuilder，采用builder模式创建Cache
 * 缓存加载、获取、刷新机制
 * 缓存基于容量、时间、权重，软弱引用回收
 * @Author lfq
 * @Date 2020/2/11
 **/
@Slf4j
public class LoadingCacheTest {

    /**
     * 定义线程工厂类
     */
    private static ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("thread-cache-%d").build();

    /***
     * 定义线程池
     */
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 15, 5,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), factory, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 定义Guava缓存线程池
     */
    private static ListeningExecutorService service = MoreExecutors.listeningDecorator(executor);

    /**
     * loadingCache
     */
    private static final LoadingCache<String, String> LOADING_CACHE = CacheBuilder.newBuilder().maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.SECONDS).build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    System.out.println(Thread.currentThread().getName() + "==load start==" + "，时间=" + new Date());
                    // 模拟同步重载耗时2秒
                    Thread.sleep(2000);
                    String value = "load-" + new Random().nextInt(10);
                    System.out.println(Thread.currentThread().getName() + "==load end==同步耗时2秒重载数据-key=" + key + ",value=" + value + "，时间=" + new Date());
                    return value;
                }

                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    System.out.println(Thread.currentThread().getName() + "==reload ==异步重载-key=" + key + "，时间=" + new Date());
                    return service.submit(() -> {
                        /* 模拟异步重载耗时2秒 */
                        Thread.sleep(2000);
                        String value = "reload-" + new Random().nextInt(10);
                        System.out.println(Thread.currentThread().getName() + "==reload-callable-result=" + value + ",oldValue=" + oldValue + "，时间=" + new Date());
                        return value;
                    });
                }
            });

    /**
     * 倒计时器，控制并发
     */
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            System.out.println("启动-设置缓存" + "，时间=" + new Date());
            LOADING_CACHE.put("name", "张三");
            System.out.println("缓存是否存在=" + LOADING_CACHE.getIfPresent("name"));
            //休眠
            Thread.sleep(2000);
            System.out.println("2秒后，缓存是否存在=" + LOADING_CACHE.getIfPresent("name"));
            //启动3个线程
            for (int i = 0; i < 3; i++) {
                startThread(i);
            }
            //模拟串行读缓存
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "休眠5秒后，读缓存=" + LOADING_CACHE.get("name") + "，时间=" + new Date());
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "距离上一次读0.5秒后，读缓存=" + LOADING_CACHE.get("name") + "，时间=" + new Date());
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "距离上一次读2秒后，读缓存=" + LOADING_CACHE.get("name") + "，时间=" + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            // -1直接=0，唤醒所有线程读取缓存,模拟并发访问缓存
            latch.countDown();
        }
    }

    private static void startThread(int id) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "...begin" + "，时间=" + new Date());
                    //休眠，当倒计时器=0时唤醒线程
                    latch.await();
                    //读缓存
                    System.out.println(Thread.currentThread().getName() + "并发读缓存=" + LOADING_CACHE.get("name") + "，时间=" + new Date());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setName("Thread-" + id);
        t.start();
    }
}
