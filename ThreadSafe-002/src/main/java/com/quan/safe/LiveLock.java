package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiveLock {

    private static Integer num = 10000000;
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        new Thread(() -> {
            synchronized (lock1) {
              while (true) {
                  if(num >=100000000 ){
                      log.info("退出T1线程");
                      break;
                  }
                  num++;
              }
            }
        },"T1").start();

        new Thread(() -> {
            synchronized (lock2) {
                while (true) {
                    if(num < 2000 ){
                        log.info("退出T2线程");
                        break;
                    }
                    num--;
                }
            }
        },"T2").start();
    }
}
