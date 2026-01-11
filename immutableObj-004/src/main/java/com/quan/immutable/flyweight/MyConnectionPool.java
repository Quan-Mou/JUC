package com.quan.immutable.flyweight;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Slf4j
public class MyConnectionPool {

//    池大小
    private final Integer maxConnections;
//    池对象
    private final Connection[] connections;
//    池对象状态 0-空闲 1-使用中
    private AtomicIntegerArray status;
    public MyConnectionPool(Integer maxConnections) {
        this.maxConnections = maxConnections;
        this.connections = new Connection[maxConnections];
        status = new AtomicIntegerArray(maxConnections);
        for (int i = 0; i < this.maxConnections; i++) {
            connections[i] = new MockConnection();
        }
    }


    public static void main(String[] args) {
        MyConnectionPool myConnectionPool = new MyConnectionPool(3);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Connection connection = myConnectionPool.getConnection();
                log.info("获取到：{}", connection);
            },"t" + i).start();
        }


    }

    public Connection getConnection() {
        while (true) {
            for (int i = 0; i < maxConnections; i++) {
                if(status.get(i) == 0) { // 存在空闲
                    status.compareAndSet(i, 0, 1);
//                    log.info("Connection {} has been established", connections[i]);
                    return connections[i];
                }
            }

        }
    }


    public void freeConnection(Connection connection) {
        for (int i = 0; i < this.maxConnections; i++) {
            if(this.connections[i].equals(connection)) {
                status.set(i, 0);
            }
        }
    }

// 容器池。



}
