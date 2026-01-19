package com.quan.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 工作线程-饥饿现象
 */
@Slf4j
public class HungerTest {

//    菜单
    static List<String> MENUS = Arrays.asList("宫保鸡丁", "鱼香肉丝", "红烧肉", "青椒肉丝");
//    烹饪菜品
    static String cooking() {
        return MENUS.get(new Random().nextInt(MENUS.size()));
    }

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.execute(() -> {
            log.info("处理点餐");
            Future<String> cooking = threadPool.submit(() -> {
                log.info("做菜");
                return cooking();
            });

            try {
                String result = cooking.get();
                log.info("上菜:{}", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        threadPool.execute(() -> {
            log.info("处理点餐");
            Future<String> cooking = threadPool.submit(() -> {
                log.info("做菜");
                return cooking();
            });

            try {
                String result = cooking.get();
                log.info("上菜:{}", result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



}
