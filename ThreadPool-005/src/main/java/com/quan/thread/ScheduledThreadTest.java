package com.quan.thread;

import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledThreadTest {
    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorThread = Executors.newScheduledThreadPool(2);
        delay(scheduledExecutorThread);

//        定时任务：每周6的00:00 开始执行任务。
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = now.with(DayOfWeek.SATURDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);

        Runnable task = () -> {
            log.info("记录日志...");
        };

        if(now.isAfter(targetTime)){ // 如果本周大于周六了，则跳到小一周
            targetTime = targetTime.plusWeeks(1);
        }

        long between = ChronoUnit.SECONDS.between(now, targetTime); // 当前时间到 周六00:00的间隔时间
        /**
         * 参数1：执行的任务
         * 参数2：启动后延迟多久执行任务
         * 参数3：每个任务之间的间隔时间  60*60*24*7  7天
         * 参数4：时间单位
         */

        scheduledExecutorThread.scheduleAtFixedRate(task,between,60*60*24*7,TimeUnit.SECONDS);
    }

    private static void delay(ScheduledExecutorService scheduledExecutorThread) {
        log.info("start...");
        scheduledExecutorThread.schedule(() -> {
//            try {
//                Thread.sleep(2000);
//                log.info("task1执行");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            log.info("task1执行");
        },2, TimeUnit.SECONDS);

        scheduledExecutorThread.schedule(() -> {
            log.info("task2执行");
        },2, TimeUnit.SECONDS);
    }

}
