package com.quan.lockfree;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterTest {

    public static void main(String[] args) {
        Student2 student2 = new Student2();
        student2.setName("权");
        student2.updateName(student2.getName(),"韩");
        System.out.println(student2.getName());
    }

}

@Data
class Student2 {
    private volatile String name;
//    这个两个泛型的意思：被更新对象的类型，被更新字段的类型
    public final static AtomicReferenceFieldUpdater<Student2,String> updater = AtomicReferenceFieldUpdater.newUpdater(Student2.class, String.class, "name");

    public void updateName(String from, String to) {
        while(true){
            if (updater.compareAndSet(this,from,to)) {
                break;
            }
        }
    }
}
