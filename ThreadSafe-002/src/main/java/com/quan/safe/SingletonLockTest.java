package com.quan.safe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingletonLockTest {


    public static void main(String[] args) {
        for(int i = 0;i<100;i++) {
            new Thread(() -> {
                log.info("{}",Singleton.getInstance());
            },"t"+i).start();
        }
    }




}


class Singleton {
    private static volatile Singleton instance = null;

    private Singleton() {}


    public static Object getInstance()  {
        if(instance != null) {
            return instance;
        }
        synchronized(Singleton.class) {
            if(instance != null) {
                return instance;
            }
            instance = new Singleton();
            return instance;
        }
    }
}



//class Singleton {
//    private static Singleton instance = null;
//
//    private Singleton() {}
//
//
//    public static Object getInstance()  {
//        synchronized(Singleton.class) {
//            if(instance != null) {
//                return instance;
//            }
//            instance = new Singleton();
//            return instance;
//        }
//    }
//}


/**
 * 懒汉式单例模式（线程安全）
 */
class Singleton4 {
    private Singleton4() {}
    private static volatile Singleton4 instance = null;

    public static Singleton4 getInstance() {
        if(instance == null) { // 添加volatile禁止指令重排
            synchronized (Singleton4.class) {
                if(instance == null) {
                    instance = new Singleton4();
                    return instance;
                }
                return  instance;
            }
        }
        return instance;
    }
}


/**
 * 懒汉式单例模式（）
 */
class Singleton2 {
    private Singleton2() {}

    private static  Singleton2 instance = null;

    public static Singleton2 getInstance() {
        if(instance == null) {
           instance =  new Singleton2();
        }
        return instance;
    }


}


/**
 * 饿汉式单例模式（线程安全-纯天然，在类加载阶段就创建好了！）
 */
class Singleton1 {

    private Singleton1(){}

    private static Singleton1 instance = new Singleton1();
    public static  Singleton1 getInstance(){
        return instance;
    }
}




