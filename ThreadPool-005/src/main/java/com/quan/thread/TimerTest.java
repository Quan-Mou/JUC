package com.quan.thread;

import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class TimerTest {
        public static void main(String[] args) {
            Timer timer = new Timer();


            TimerTask timerTask1 = new TimerTask() {
                @Override
                public void run() {
                    log.info("定时任务1执行");
                }
            };

            TimerTask timerTask2 = new TimerTask() {
                @Override
                public void run() {
                    log.info("定时任务2执行");
                }
            };

            /**
             * 这种方式是单线程执行的  任务2必须在任务1执行之后再执行。
             */

            log.info("start---");
            timer.schedule(timerTask1, 1000);
            timer.schedule(timerTask2, 1000);
    }
}
