package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeadLock {

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        new Thread(() -> {
            synchronized (lock1) {
                log.info("获取到lock1锁，正在等待获取lock2锁...");
                synchronized (lock2) {
                    log.info("获取到lock2锁");
                }
            }
        },"T1").start();

        new Thread(() -> {
            synchronized (lock2) {
                log.info("获取到lock2锁，正在等待获取lock1锁...");
                synchronized (lock1) {
                    log.info("获取到lock1锁");
                }
            }
        },"T2").start();
    }
}
