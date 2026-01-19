package com.quan.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ShutdownThreadPool {
    public static void main(String[] args) {
//        创建3个线程的线程池，使用无界队列
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 3,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        for (int i = 0; i < 100; i++) {
            int temp = i;
            threadPoolExecutor.execute(() -> {
                log.info("{} 执行任务{}", Thread.currentThread().getName(), temp);

            });
        }


        log.info("关闭线程池");
//        threadPoolExecutor.shutdownNow();
        threadPoolExecutor.shutdown();
//        threadPoolExecutor.execute(() -> {
//                log.info("执行关闭线程以后就不在接收任务了！");
//        });
        log.info("isShutdown：{}", threadPoolExecutor.isShutdown());
        log.info("isTerminated：{}", threadPoolExecutor.isTerminated());
        System.out.println(threadPoolExecutor.isTerminated());

        System.out.println(threadPoolExecutor.getCompletedTaskCount());
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
//        queue.forEach(Runnable::run);
        System.out.println(queue);
        log.info("关闭线程池结束");

    }

}
