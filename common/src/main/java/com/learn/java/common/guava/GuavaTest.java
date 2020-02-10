package com.learn.java.common.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.base.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import com.google.common.io.Files;
import com.learn.java.common.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.collect.ArrayListMultimap;
import org.apache.curator.shaded.com.google.common.collect.Multimap;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName Collection
 * @Description:Guava操作
 * @Author lfq
 * @Date 2020/2/10
 **/
@Slf4j
public class GuavaTest {
    /**
     * 非可变集合(线程安全)
     */
    @Test
    public void immutableCollection() {
        ImmutableList<Integer> integerImmutableList = ImmutableList.of(1, 2, 3);
        integerImmutableList.stream().forEach(System.out::println);
        ImmutableSet<String> stringImmutableSet = ImmutableSet.of("hello", "world", "welcome", "china");
        ImmutableMap<String, Integer> immutableMap = ImmutableMap.of("k1", 10, "k2", 20);
        boolean flag = stringImmutableSet.contains("china");
        System.out.println(flag);

        List<Integer> numberList = Lists.newArrayList();
        Integer[] arr = integerImmutableList.toArray(new Integer[integerImmutableList.size()]);
        for (int i = 0; i < arr.length; i++) {
            numberList.add(arr[i]);
        }
        numberList.stream().forEach(System.out::println);
        for (Map.Entry<String, Integer> entry : immutableMap.entrySet()) {
            log.info("key:{},value:{}", entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void multiMap() {
        Multimap<String, Integer> map = ArrayListMultimap.create();
        map.put("name", 1);
        map.put("name", 2);

        List<Integer> nameList = (List<Integer>) map.get("name");
        System.out.println(JSON.toJSONString(nameList));
    }

    @Test
    public void table() {
        Table<String, Integer, Integer> table = TreeBasedTable.create();
        table.put("name", 1, 23);
        table.put("address", 2, 24);
        table.put("name", 3, 25);
        Set<Integer> set = table.columnKeySet();
        Set<String> rowKey = table.rowKeySet();
        Map<String, Map<Integer, Integer>> map = table.rowMap();
        System.out.println(JSON.toJSONString(map));
    }

    /**
     * 集合转换成字符串
     */
    @Test
    public void collectionJoinToString() {
        List<String> list = Lists.newArrayList("hello", "world", "nice", "to", "meet", "you");
        String s = Joiner.on("-").join(list);
        System.out.println(s);

        Map<String, Integer> map = Maps.newHashMap();
        map.put("fq", 26);
        map.put("lq", 27);

        String str = Joiner.on(",").withKeyValueSeparator(":").join(map);
        System.out.println(str);
    }

    /**
     * 字符串转成集合
     */
    @Test
    public void StringToCollection() {
        String s = "hello:welcome:wo:ni:welcome";
        List<String> list = Splitter.on(":").splitToList(s);
        list.stream().forEach(System.out::println);

        String s2 = "hello-  world--   you";
        List<String> list1 = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(s2);
        list1.stream().forEach(System.out::println);

        String s3 = "key1=3,key2=4";
        Map<String, String> stringMap = Splitter.on(",").withKeyValueSeparator("=").split(s3);
        System.out.println(JSON.toJSONString(stringMap));

        //指定正则表达式分隔
        String input = "aa.dd,,ff,,.";
        List<String> result = Splitter.onPattern("[.|,]").omitEmptyStrings().splitToList(input);
        System.out.println(JSON.toJSONString(result));

        // 判断匹配结果
        boolean result1 = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('l');
        // 保留数字文本
        String s4 = CharMatcher.digit().retainFrom("abc 123 efg");
        // 删除数字文本
        String s5 = CharMatcher.digit().removeFrom("abc 123 efg");
        log.info("result:{},s4:{},s5:{}", result1, s4, s5);
    }

    /**
     * 集合过滤迭代
     */
    @Test
    public void iterateCollection() {
        ImmutableList<String> nameList = ImmutableList.of("fq", "lq", "zh");
        Iterable<String> fitered = nameList.stream().filter(Predicates.or(Predicates.equalTo("fq"), Predicates.equalTo("zh"))::apply).collect(Collectors.toList());
        System.out.println(fitered);

        //使用自定义回调方法对Map的每个Value进行操作
        ImmutableMap<String, Integer> m = ImmutableMap.of("begin", 12, "code", 15);
        // Function<F, T> F表示apply()方法input的类型，T表示apply()方法返回类型
        Map<String, Integer> m2 = Maps.transformValues(m, input -> {
            if (input > 12) {
                return input * 2;
            } else {
                return input + 1;
            }
        });
        System.out.println(m2);
    }

    /**
     * 集合运算
     */
    @Test
    public void setCompute() {
        HashSet setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet setB = Sets.newHashSet(4, 5, 6, 7, 8);

        Sets.SetView view = Sets.union(setA, setB);
        System.out.println(JSON.toJSONString(view));

        view = Sets.difference(setA, setB);
        System.out.println(JSON.toJSONString(view));

        view = Sets.intersection(setA, setB);
        System.out.println(JSON.toJSONString(view));
    }

    @Test
    public void mapCompute() {
        ImmutableMap<String, Integer> mapA = ImmutableMap.of("key1", 1, "key2", 2);
        ImmutableMap<String, Integer> mapB = ImmutableMap.of("key1", 1, "key2", 2, "key3", 3);

        MapDifference differenceMap = Maps.difference(mapB, mapA);
        System.out.println(JSON.toJSONString(differenceMap));
        Map entriesDiffering = differenceMap.entriesDiffering();
        Map entriesOnlyOnLeft = differenceMap.entriesOnlyOnLeft();
        Map entriesOnlyOnRight = differenceMap.entriesOnlyOnRight();
        Map entriesInCommon = differenceMap.entriesInCommon();
        System.out.println(JSON.toJSONString(entriesDiffering));
        System.out.println(JSON.toJSONString(entriesOnlyOnLeft));
        System.out.println(JSON.toJSONString(entriesInCommon));
        System.out.println(JSON.toJSONString(entriesOnlyOnRight));
    }

    @Test
    public void object2String() {
        User user = new User("fq", 25);
        String str = MoreObjects.toStringHelper("User").add("age", user.getAge()).
                add("name", user.getName()).
                toString();
        System.out.println(str);
    }

    @Test
    public void ordering() {
        User user1 = new User("fq", 25);
        User user2 = new User("tt", 28);
        Ordering<User> byOrdering = Ordering.natural().nullsFirst().onResultOf((Function<User, String>) user -> user.getAge().toString());
        System.out.println(byOrdering.compare(user1, user2));
    }

    @Test
    public void stopTime() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100000; i++) {

        }
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }

    @Test
    public void fileOperate() {
        File file = new File("/test.txt");
        List<String> list = null;
        try {
            list = Files.readLines(file, Charsets.UTF_8);
        } catch (Exception e) {
        }
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void cache() {
        LoadingCache<String, String> cacheBuilder = CacheBuilder
                .newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue = "hello " + key + "!";
                        return strProValue;
                    }
                });
        try {
            System.out.println(cacheBuilder.get("begincode"));
            System.out.println(cacheBuilder.get("wen"));
            cacheBuilder.put("begin", "code");
            System.out.println(cacheBuilder.get("begin"));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
            String resultVal = cache.get("code", () -> "begin " + "code" + "!");
            String var2 = cache.get("code2", () -> "welcome");
            System.out.println("value : " + resultVal);
            System.out.println("var2 : " + var2);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接多个字符串并追加到StringBuilder
     */
    @Test
    public void stringOperate() {
        StringBuilder stringBuilder = new StringBuilder("hello");
        // 字符串连接器，以|为分隔符，同时去掉null元素
        Joiner joiner1 = Joiner.on("|").skipNulls();
        // 构成一个字符串foo|bar|baz并添加到stringBuilder
        stringBuilder = joiner1.appendTo(stringBuilder, "foo", "bar", null, "baz");
        System.out.println(stringBuilder);
    }

    /**
     * 连接List元素并写到文件流
     */
    @Test
    public void list2File() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("./tmp.txt"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<User> dateList = new ArrayList<>();
        dateList.add(new User("fq", 25));
        dateList.add(null);
        dateList.add(new User());
        // 构造连接器：如果有null元素，替换为no string
        Joiner joiner2 = Joiner.on(";").useForNull("no string");
        try {
            // 将list的元素的tostring()写到fileWriter，是否覆盖取决于fileWriter的打开方式，默认是覆盖，若有true，则是追加
            joiner2.appendTo(fileWriter, dateList);
            // 必须添加close()，否则不会写文件
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void map() {
        Map<String, String> testMap = Maps.newLinkedHashMap();
        testMap.put("Cookies", "12332");
        testMap.put("Content-Length", "30000");
        testMap.put("Date", "2016.12.16");
        testMap.put("Mime", "text/html");
        // 用:分割键值对，并用#分割每个元素，返回字符串
        String returnedString = Joiner.on(",").withKeyValueSeparator("=").join(testMap);
        System.out.println(returnedString);
    }

    @Test
    public void checkArgument(){
        int data = 10;
        Preconditions.checkArgument(data < 100, "data must be less than 100");
    }


}
