package com.quan.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /**
         * 线程池参数说明
         * int CorePoolSize:核心线程数，不会被回收的线程数量
         * int maximumPoolSize:最大线程数，当任务队列满了，且线程数量小于最大线程数时，就会创建新的线程，是一个救急线程
         * int keepAliveTime: 非核心线程的存活时间（maximumPoolSize - CorePoolSize）超过这个时间的救急线程会被回收
         * TimeUnit timeUnit: 非核心线程的存活时间单位
         * BlockingQueue<Runnable> workQueue: 任务队列，用于存储等待的任务
         * ThreadFactory threadFactory ：线程个工厂，可以为线程创建时起个好名字
         * RejectedExecutorHandler handler: 拒绝策略：有四种：...
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 3, 5, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(20), new ThreadFactory() {
             int count = 1;
            @Override
            public Thread newThread(Runnable r) {

                log.info("线程工厂：{}",r);
                return new Thread(r,"custom-thread-" + count++);
            }
        });

//            threadPool.execute(() -> {
//                log.info("begin：{}",Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("end：{}",Thread.currentThread().getName());
//            });
//
//            threadPool.execute(() -> {
//                log.info("begin：{}",Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("end：{}",Thread.currentThread().getName());
//            });
//
//            threadPool.execute(() -> {
//                log.info("begin：{}",Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("end：{}",Thread.currentThread().getName());
//            });



//        for (int i = 0; i < 5; i++) {
//            int temp = i;
//            Future<String> submit = threadPool.submit(() -> {
//                log.info("start：异步执行返回值任务");
//                Thread.sleep(1000);
//                log.info("end：异步执行返回值任务");
//                return "success" + temp;
//            });
//
//            log.info("异步任务返回值：{}",submit.get()); // 获取时是阻塞的，如果不获取则不是阻塞的
//        }


        List<Future<String>> tasks = threadPool.invokeAll(Arrays.asList(
                () -> {
                    log.info("任务1");
                    return "任务1success";
                },
                () -> {
                    log.info("任务2");
                    return "任务2success";
                },
                () -> {
                    log.info("任务3");
                    return "任务3success";
                }
        ));

        tasks.forEach(taskItem -> {
            try {

                log.info("{}", taskItem.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        List<Future<String>> tasks2 = threadPool.invokeAll(
                Arrays.asList(
                        () -> {
                            log.info("任务1");
                            return "任务1success";
                        },
                        () -> {
                            log.info("任务2");
                            return "任务2success";
                        },
                        () -> {
                            log.info("任务3");
                            Thread.sleep(1000);
                            return "任务3success";
                        }
                ), 1, TimeUnit.SECONDS
        );

        tasks2.forEach(taskItem -> {
            try {
                log.info("{}",taskItem.get());
//                log.info("{}",taskItem.isDone());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        java.lang.String result  = threadPool.invokeAny(Arrays.asList(
                () -> {
                    log.info("begin DB");
                    Thread.sleep(2000);
                    log.info("end DB");
                    return "Result from DB"; // 模拟慢数据库
                },
                () -> {
                    log.info("begin Redis");
                    Thread.sleep(500);
                    log.info("end Redis");
                    return "Result from Redis"; // 快缓存
                },
                () -> {
                    log.info("begin miss");
                    Thread.sleep(1000);
                    log.info("end miss");
                    throw new RuntimeException("Cache miss"); // 失败的缓存
                }
        ));

        log.info("result:{}", result);


    }

}
