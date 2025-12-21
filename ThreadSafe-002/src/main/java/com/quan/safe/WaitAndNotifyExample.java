package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitAndNotifyExample {
//    锁
    static final Object lock = new Object();
//    是否烟送到了
    static boolean isSmoke = false;

    public static void main(String[] args) throws InterruptedException {
//        三个角色：小南、其他人、送烟的隔壁老王
        new Thread(()->{
            synchronized(lock) {
                log.info("烟来了嘛？ {}",isSmoke);
                while(!isSmoke){
                    log.info("烟没有送过来，继续等待 {}",isSmoke);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("收到香烟，开始干活... {}",isSmoke);
            }
        },"小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                synchronized (lock) {
                    log.info("其他人开始干活...");
                }
            },"其他人").start();
        }

        Thread.sleep(1000);
        new Thread(()->{
            synchronized (lock) {
                isSmoke = true;
                lock.notify();
                log.info("烟送到了! {}",isSmoke);
            }
        },"送烟的隔壁老王").start();
    }

//    public static void main(String[] args) throws InterruptedException {
////        三个角色：小南、其他人、送烟的隔壁老王
//        new Thread(()->{
//            synchronized(lock) {
//                log.info("烟来了嘛？");
//                while(!isSmoke){
//                    log.info("烟没有送过来，继续等待");
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                log.info("收到香烟，开始干活...");
//            }
//        },"小南").start();
//
//        for (int i = 0; i < 5; i++) {
//            new Thread(()->{
//                synchronized (lock) {
//                    log.info("其他人开始干活...");
//                }
//            },"其他人").start();
//        }
//
//        Thread.sleep(1000);
//        new Thread(()->{
////            如果小南使用的是sleep这里一定不能加锁，因为sleep不会释放锁，这里永远也执行不到
////            synchronized (lock) {
//                isSmoke = true;
//                log.info("烟送到了!");
////            }
//        },"送烟的隔壁老王").start();
//    }
}
