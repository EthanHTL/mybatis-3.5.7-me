package com.mybatis.test;

import com.google.common.collect.Lists;
import lombok.Data;
import org.junit.Test;
import org.myatis.example.entity.Blog;
import org.myatis.example.lock.NickleLock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 黄天亮
 * @create: 2021-06-23 11:23
 **/
public class MapperTest {
    private static int size = 1000000;
    private static final Map<Class<?>,Object> objectMap = new HashMap<>(size);
    // private static final Map<Class<?>,Object> objectMap = new ConcurrentHashMap<>(size);

    private static NickleLock lock = new NickleLock();

    @Data
    static class MapperScanner {
        private String mapperLocation;

        public List<Class<?>> scanMapper(){
            List<Class<?>> objects = Lists.newArrayList();
            for (int i = 0; i < size; i++) {
                objects.add(Blog.class);
            }
            return objects;
        }
    }
    // 多线程  synchronized
    public static   void put(Class<?> clazz, Object obj){
        lock.lock();
        objectMap.put(clazz, obj);
        lock.unlock();
    }

    @Test
    public void testScanMapper(){
        String mapperLocation = "classpath:mapper/*.xml";
        MapperScanner mapperScanner = new MapperScanner();

        mapperScanner.setMapperLocation(mapperLocation);

        /**
         * 一千个
         */
        List<Class<?>> classes = mapperScanner.scanMapper();
        long start = System.currentTimeMillis();
        classes.forEach(clazz -> put(clazz,new Object()));
        // classes.parallelStream().forEach(clazz -> put(clazz,new Object()));  // 多线程
        long end = System.currentTimeMillis();

        System.out.println(end-start);

    }
}
