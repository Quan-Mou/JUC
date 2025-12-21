package com.quan.safe;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class Mode2 {
    public static void main(String[] args) throws InterruptedException {

        MessageQueue queue = new MessageQueue();

        for (int i = 0; i < 5; i++) {
            int j = i;
            new Thread(() -> {
                    queue.put("消息" + j);
            },"生产者" + i).start();
        }




        new Thread(() -> {
            while(true){
                Object poll = queue.poll();
                log.info("消费消息:{}",poll);
            }

        },"消费者").start();

    }
}

@Slf4j
class MessageQueue<T> {
  final Queue<T> queue = new ArrayDeque<>();
   private final int capaCity = 3;


   public void put(T data) {
       synchronized (this) {
           while (queue.size() >= capaCity) {
               try {
                   this.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           log.info("正在生产消息:{}",data);
           queue.offer(data);
           this.notifyAll();
       }
   }

   public T poll() {
       synchronized (this) {
           while(queue.isEmpty()) {
               log.info("正在等待消息产生");
               try {
                   this.wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           T poll = queue.poll();
           this.notifyAll();
           return poll;
       }
   }


}


















