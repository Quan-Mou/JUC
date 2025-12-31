package com.quan.lockfree;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AtomicIntegerTest {

//    private static int sum = 0;
    private static AtomicInteger sum = new AtomicInteger(0);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> task1 = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("{}: 执行了",Thread.currentThread().getName());
                for(int i=0;i<1000000;i++){
                    sum.incrementAndGet(); // 自增1
//                    System.out.println(sum.addAndGet(1));
                }
                return "返回值1";
            }
        });
        FutureTask<String> task2 = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("{}: 执行了",Thread.currentThread().getName());
                for(int i=0;i<1000000;i++){
                    sum.incrementAndGet();
                }
                return "返回值2";
            }
        });

        Thread t1 = new Thread(task1, "task1");
        Thread t2 = new Thread(task2, "task2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("sum：{}",sum);
    }


    @Test
    public void test1() {
        AtomicInteger value = new AtomicInteger(0);
        System.out.println(value.addAndGet(10)); // 添加并获取
        System.out.println(value.getAndAdd(10)); // 先获取，再添加，返回的是添加之前的值
        System.out.println(value.get()); // 获取值

        int v = value.updateAndGet((item) -> {
            return item * 10;
        }); // 具体计算由自己实现。item参数为当前的值。
        System.out.println(v);
        System.out.println(value.get());
    }

//    @Test
//    public void test2() {
//        AtomicReference<Integer> value = new AtomicReference<>(0);
//        value.
//    }

}
