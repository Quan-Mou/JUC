package com.quan.create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Work {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                log.info("洗水壶");
                Thread.currentThread().sleep(1000);
                log.info("烧开水");
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "小王");

        Thread t2 = new Thread(() -> {
            try {
                log.info("洗茶壶");
                Thread.currentThread().sleep(1000);
                log.info("洗水杯");
                Thread.currentThread().sleep(1000);
                log.info("拿茶叶");
                t1.join();
                log.info("泡茶");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "小周");

        t1.start();
        t2.start();
    }




}
