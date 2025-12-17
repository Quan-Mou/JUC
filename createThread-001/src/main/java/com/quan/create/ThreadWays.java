package com.quan.create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 线程的常用方法
 */
@Slf4j
public class ThreadWays {


    public static void main(String[] args) throws InterruptedException {
        FutureTask<Void> task = new FutureTask<>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i < 5; i++) {
                    log.info(Thread.currentThread().getName() + ": = " + i);
                    Thread.sleep(1000); // 当前线程休眠1秒，让出CPU时间片，不释放锁。
                }
                log.info("{} Id = {}",Thread.currentThread().getName(),Thread.currentThread().getId());
                return null;
            }
        });

        Thread t1 = new Thread(task, "TaskThread");
        t1.start();
//        t1.join(); // 等待线程t1执行完毕，当前线程阻塞。只有这个线程执行完成，后面的线程才会执行

        for (int i = 0; i < 5; i++) {
            log.info(Thread.currentThread().getName() + ": = " + i);
        }
        log.info("{} Id = {}",Thread.currentThread().getName(),Thread.currentThread().getId());
    }







}
