package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadSafeQuestion {

    static  int count = 0;


    /**
     *  synchronized 有三种写法：
     *  1. 修饰普通方法，锁对象是当前对象
     *  2. 修饰静态方法，锁对象是当前类的Class对象
     *  3. 同步代码块，锁对象可以是任意对象，我们自己指定
     *
     */

    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();


        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
               synchronized (obj) {
                   count++;
               }
            }
        }, "小王");


        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (obj) {
                    count--;
                }
            }
        }, "小张");


        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("count:{}",count); // 预期结果是0，实际不是。
    }

}
