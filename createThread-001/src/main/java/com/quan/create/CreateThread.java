package com.quan.create;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class CreateThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThread t1 = new MyThread();
        t1.setName("MyThread"); // 设置线程名
        t1.start(); // 调用start方法才是真正启用一个线程执行

        Thread t2 = new Thread(new MyRunnableThread());
        t2.setName("MyRunnableThread");
        t2.start();


        FutureTask<Integer> t3Task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                for (int i = 0; i < 10; i++) {
                    log.info("{}:{}", Thread.currentThread().getName(), i);
                }
                return 100;
            }
        });

        Thread t3 = new Thread(t3Task, "FutureTaskThread");
        t3.start();
        log.info("{}",t3Task.get()); // 获取线程执行的返回值，是一个阻塞方法，直到获取了返回值，否则一直阻塞
    }


    /**
     * 创建线程的第二种方式：实现Runnable接口，重写run方法
     */
    static class MyRunnableThread implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                log.info("{}:{}", Thread.currentThread().getName(), i);
            }
        }
    }


    /**
     * 创建线程的第一种方式：继承Thread类，重写run方法
     */
    static class MyThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                log.info("{}:{}", Thread.currentThread().getName(), i);
            }
        }
    }




}
