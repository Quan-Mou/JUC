package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程交替执行
 */
@Slf4j
public class ThreadSequenceExecute {

    private static ReentrantLock lock = new ReentrantLock();
    private static int currentThreadExecute = 1; // T1:1，T2：2，T3：3
    private static int executeCount = 2; // 每个线程内执行的次数
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private static final Condition condition3 = lock.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 3; i++) { // 交替执行3次

                try {
                    lock.lock();
                    if(currentThreadExecute == 1){
                        for(int j = 0;j<executeCount;j++){
                            log.info("当前线程:{},执行:{}",Thread.currentThread().getName(),j);
                        }
                        currentThreadExecute = 2;
                        condition2.signal();
                    }
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } finally {
                    lock.unlock();
                }
            }
        },"T1").start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    lock.lock();
                    if(currentThreadExecute == 2){
                        for(int j = 0;j<executeCount;j++){
                            log.info("当前线程:{},执行:{}",Thread.currentThread().getName(),j);
                        }
                        currentThreadExecute = 3;
                        condition3.signal();
                    }
                    try {
                        condition2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }
        },"T2").start();

        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    lock.lock();
                    if(currentThreadExecute == 3){
                        for(int j = 0;j<executeCount;j++){
                            log.info("当前线程:{},执行:{}",Thread.currentThread().getName(),j);
                        }
                        currentThreadExecute = 1;
                        condition1.signal();
                    }
                    try {
                        condition3.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            }
        },"T3").start();
    }



}
