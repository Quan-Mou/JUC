package com.quan.lockfree;

import lombok.Data;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) throws InterruptedException {
        Student student = new Student();

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 1000000; i++) {
                student.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 1000000; i++) {
                student.increment();
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(student.getAge());
    }

}

@Data
class Student {
    private volatile int age; // 成员变量必须是volatile、非static、非final


    private final static AtomicIntegerFieldUpdater<Student> updater = AtomicIntegerFieldUpdater.newUpdater(Student.class, "age");

    public void increment() {
        updater.incrementAndGet(this);
    }

    public int getAge() {
        return age;
    }

}
