package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

public class MultipartLock {

    public static void main(String[] args) {
        BigRoom room = new BigRoom();
        new Thread(()->{
            room.sleep();
        },"t1").start();


        new Thread(()->{
            room.study();
        },"t2").start();
    }

}


@Slf4j
class BigRoom {

//  卧室锁
    private final Object bedRoomLock = new Object();
//  书房锁
    private final Object bookRoomLock = new Object();

    public void sleep() {
      synchronized (bedRoomLock) {
          log.info("小南在睡觉");
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
    }

    public void study() {
        synchronized (bookRoomLock) {
            log.info("小女在学习");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
