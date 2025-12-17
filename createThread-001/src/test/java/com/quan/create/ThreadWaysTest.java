package com.quan.create;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程的常用方法
 */
@Slf4j
public class ThreadWaysTest {


    public static void main(String[] args) {
        /**
         * 测试yield和priority
         */
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                int count =0;
                for(;;) {
                    count++;
                    log.info("{} -> {}",Thread.currentThread().getName(),count);
                }
            }
        };

        Thread thread1 = new Thread(t1, "t1");
        thread1.setPriority(10);
        thread1.start();

        Runnable t2 = new Runnable() {
            @Override
            public void run() {
                int count =0;
                for(;;) {
                    Thread.currentThread().yield();
                    count++;
                    log.info("{} -> {}",Thread.currentThread().getName(),count);
                }
            }
        };

        Thread thread2= new Thread(t2, "t2");
        thread2.setPriority(6);

        thread2.start();

        log.info("{} -> {}",Thread.currentThread().getName(),thread2.getState());
    }

    @Test
    public void yieldTest() {
        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                int count =0;
                for(int i= 0;i<2000;i++) {
                    count++;
                    log.info("{} -> {}",Thread.currentThread().getName(),count);
                }
            }
        };

        Thread thread1 = new Thread(t1, "t1");
        thread1.start();

        Runnable t2 = new Runnable() {
            @Override
            public void run() {
                int count =0;
                for(int i= 0;i<2000;i++) {
                    count++;
                    log.info("{} -> {}",Thread.currentThread().getName(),count);
                }
            }
        };

        Thread thread2= new Thread(t2, "t2");
        thread2.start();

        log.info("{} -> {}",Thread.currentThread().getName(),thread2.getState());
    }




    @Test
    public  void SetGetStatusTest() throws InterruptedException, ExecutionException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += i;
                    log.info(Thread.currentThread().getName() + ": = " + i);
                    log.info("{} Status = {}",Thread.currentThread().getName(),Thread.currentThread().getState());
                }

                return sum;
            }
        });

        Thread t1 = new Thread(task, "TaskThread");
        t1.start();
        for (int i = 0; i < 5; i++) {
            log.info(Thread.currentThread().getName() + ": = " + i);
        }

        log.info("{} Status = {}",Thread.currentThread().getName(),Thread.currentThread().getState());
        log.info("{} Status = {}",Thread.currentThread().getName(),Thread.currentThread().getState());
    }


    static int i = 0;
    @Test
    public void joinTest() throws InterruptedException {

        Runnable t1 = new Runnable() {
            @Override
            public void run() {
                log.info("t1 begin");
                i = 100;
                log.info("t1 end");
            }
        };

        Thread thread1 = new Thread(t1, "t1");
        thread1.start();
        thread1.join();
        log.info("i = : {}",i);
    }

    @Test
    public void interruptTest() throws InterruptedException {
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                log.info("{} begin",Thread.currentThread().getName());
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("{} end",Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(task1, "t1");
        t1.start();


        Thread.currentThread().sleep(1000); // 主线程休眠1秒
        t1.interrupt();
        log.info("打断状态：{}",t1.isInterrupted());

        t1.join();


//      正常打断的情况：
        Thread t3 = new Thread(() -> {
            while (true) {

            }
        }, "t3");
        t3.start();


        Thread.currentThread().sleep(1000); // 主线程休眠1秒
        t3.interrupt(); // 主线程打断t3线程

    }



    @Test
    public void parkTest() throws InterruptedException {

        Thread task = new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                log.info("{}", j);
                if (j == 5) {
                    LockSupport.park(); // 执行到五次后阻塞当前线程
                    log.info("继续执行");
                }
            }
        }, "t1");
        task.start();

        log.info("{}",Thread.currentThread().getName());
        Thread.currentThread().sleep(2000);
        task.interrupt(); // 主线程打断t1线程
        log.info("打断task");
    }


    @Test
    public void daemonTest() throws InterruptedException {

        Thread task = new Thread(() -> {
            for (int j = 0; j < 10; j++) {
                log.info("{}", j);
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "daemon");
        task.setDaemon(true); // 设置为守护线程，必须在start之前执行。
        task.start();

        log.info("{}",Thread.currentThread().getName());

    }







    



}
