package com.quan.safe;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Mode {
    public static void main(String[] args) throws InterruptedException {
        Container container = new Mode().new Container();
        new Thread(()->{
            log.info("开始获取数据");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Object object = container.getObject();
            log.info("获取到了:{}",object);
        },"t1").start();
        new Thread(()->{
            container.setObject("str");
            log.info("str:{}",container.getObject());
        },"t2").start();
    }


    @Data
    class Container {
        private Object object;

        public Object getObject() {
            synchronized (this) {
                while(object == null){
                    try {
                        this.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return object;
            }
        }

        public void setObject(Object object) {
            synchronized (this) {
                this.object = object;
                this.notifyAll();
            }
        }
    }
}
