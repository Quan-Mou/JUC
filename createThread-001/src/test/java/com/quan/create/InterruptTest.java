package com.quan.create;

import lombok.extern.slf4j.Slf4j;
import org.junit.experimental.theories.Theories;

@Slf4j
public class InterruptTest {

    private Thread monitor;

    public static void main(String[] args) throws InterruptedException {
        InterruptTest test = new InterruptTest();

        test.start();
        Thread.currentThread().sleep(5000); // 主线程休眠5秒
         // 主线程打断监控线程
        test.stop();
    }


    public void start() {
        //        两阶段终止模式：
        monitor  = new Thread(() -> {
            while (true) {
                Thread thread = Thread.currentThread();
                boolean interrupted = thread.isInterrupted();
                if (interrupted) { // 被打断了，处理后事
                    log.info("被打断了：{}，处理后事，线程退出", interrupted);
                    break;
                }
//                没有被打断，睡眠两秒
                try {
                    Thread.currentThread().sleep(2000);
                    log.info("监控处理执行...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                   这里被打断了，需要重置打断标志位
                    thread.interrupt();
                }
            }
        }, "t1");
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }


        //      正常打断的情况：
//        Thread t3 = new Thread(() -> {
//            while (true) {
//                boolean interrupted = Thread.currentThread().isInterrupted();
//                if(interrupted){
//                    log.info("打断，退出执行：{}",interrupted);
//                    break;
//                }
//            }
//        }, "t3");
//        t3.start();
//
//
//        Thread.currentThread().sleep(1000); // 主线程休眠1秒
//        t3.interrupt(); // 主线程打断t3线程,注意打算后线程并不会停止,只是设置了打断标志，你可以根据打断标记，选择是继续执行还是退出
//    }
}
