package com.quan.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

public class ThreadPoolException {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

//      方式1：手动try 异常
        scheduledExecutorService.schedule(() -> {
//            int i = 1 / 0; // 不抛抛出异常，
            try {
                int i = 1 / 0;
                System.out.println("runnableTask");
            } catch (Exception e) {
                e.printStackTrace();
            }
        },1, TimeUnit.SECONDS);


//       方式2：使用Future
        ScheduledFuture<Boolean> futureTask = scheduledExecutorService.schedule(() -> {
            System.out.println("futureTask");
            int i = 1 / 0;
            return true;
        }, 1, TimeUnit.SECONDS);

        System.out.println(futureTask.get()); // 异常信息
    }
}
