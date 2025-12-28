package com.quan.safe;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ParkAlternateTest {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 3;  i++) {
                log.info("{}:{}",Thread.currentThread().getName(),i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LockSupport.park();
        }, "T1");
        t1.start();


        Thread t2 = new Thread(() -> {
            LockSupport.park();
            for (int i = 0; i < 3; i++) {
                log.info("{}:{}",Thread.currentThread().getName(),i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LockSupport.unpark(t1);
        }, "T2");
        t2.start();


    }
}

