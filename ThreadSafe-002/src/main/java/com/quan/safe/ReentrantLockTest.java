package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ReentrantLockTest {

    private static final ReentrantLock lock = new ReentrantLock();

   private static Queue<Object> queue = new LinkedList<>();

       private static Condition fullWait = lock.newCondition();
       private static Condition emptyWait = lock.newCondition();
       private static final int queueCapacity = 2;

       public static void produce(Object data) {
           lock.lock();
           try {
               while (queue.size()>=queueCapacity) {
                   try {
                       log.info("队列数据已满，等待消费者消费");
                       fullWait.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               log.info("生产了一条消息：{}",data);
    //         添加数据到队列尾部
               queue.offer(data);
               emptyWait.signal();
           } finally {
               lock.unlock();
           }
       }

       public static Object consume() {
            try {
                lock.lock();
    //      出队，查看队列首元素，如果不存在则返回null
                while (queue.isEmpty()) {
                    try {
                        log.info("正在等待生产者生产消息");
                        emptyWait.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Object poll = queue.poll();
                fullWait.signal();
                return poll;
            } finally {
                lock.unlock();
            }
       }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for(int i=0;i<10;i++) {
                produce(i);
            }
        },"生产者线程").start();

        new Thread(() -> {
//            for(int i=0;i<10;i++) {
//                Object consume = consume();
//                log.info("消费消息：{}",consume);
//            }
            while(true){
                Object consume = consume();
                log.info("消费消息：{}",consume);
            }
        },"消费者线程").start();




//        Object lock1 = new Object();
//        Object lock2 = new Object();
//        ReentrantLock lock = new ReentrantLock(true);
//        new Thread(() -> {
//            lock.lock();
//            log.info("获取到锁，睡眠五秒");
//            try {
//                Thread.sleep(5000);
//                log.info("睡眠结束");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                lock.unlock();
//            }
//        },"t1").start();
//
//        Thread.sleep(1000);
//        boolean acquired = false;
//        log.info("主线程尝试获取lock锁：");
//        acquired = lock.tryLock(1, TimeUnit.SECONDS);
//        System.out.println("result" + acquired);
//        if(acquired){
//            log.info("主线程到获取lock锁：");
//        } else {
//            log.info("主线程放弃获取lock锁：");
//        }











//        new Thread(() -> {
//
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"t2").start();






        // T1 先拿锁并长时间持有
//        Thread t1 = new Thread(() -> {
//            try {
//            lock.lock();
//                log.info("T1 拿到锁，睡5秒...");
//                while (true){}
//            }  finally {
//                lock.unlock();
//            }
//        }, "T1");
//
//
//        t1.start();
//        Thread.sleep(500);
//        log.info("t1执行中断");
//        t1.interrupt();



//      公平锁
//        ReentrantLock lock3 = new ReentrantLock(true);



//        new Thread(() -> {
//            synchronized (lock1) {
//                log.info("t1获取到锁");
//                synchronized (lock1) {
//                    log.info("t1再次获取到锁");
//
//                }
//            }
//        },"t1").start();

//        new Thread(() -> {
//            try {
//                lock.lock();
//                log.info("t2获取到锁");
//            } finally {
//                lock.unlock();
//            }
//        },"t1").start();






//        ReentrantLock lock = new ReentrantLock();

//        new Thread(() -> {
//            try {
//                lock.lock();
//                log.info("t1获取到锁");
//                lock.lock();
//                log.info("t1再次获取到锁");
//            } finally {
//                log.info("t1释放锁");
//                lock.unlock();
//            }
//        },"t1").start();
//
//        new Thread(() -> {
//            try {
//                lock.lock();
//                log.info("t2获取到锁");
//            } finally {
//                lock.unlock();
//            }
//        },"t1").start();
    }

}
