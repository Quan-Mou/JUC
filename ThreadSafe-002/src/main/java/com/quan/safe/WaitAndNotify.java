package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitAndNotify {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        new Thread(() -> {
            log.info("t1 running...");
            synchronized (lock) {
                try {
                    log.info("t1 start");
                    lock.wait();
                    log.info("t1 end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

        new Thread(() -> {
            log.info("t2 running...");
            synchronized (lock) {
                try {
                    log.info("t2 start");
                    lock.wait();
                    log.info("t2 end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2").start();
//        主线程获取锁调用notify只会唤醒其中一个线程
        Thread.sleep(1000);
        synchronized (lock) {
//            lock.notify();
            lock.notifyAll();
        }
    }

}
