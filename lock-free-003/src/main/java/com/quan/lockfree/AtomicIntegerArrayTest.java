package com.quan.lockfree;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicIntegerArrayTest {

    public static void main(String[] args) {
        // 初始化数组的长度为3
        AtomicIntegerArray array = new AtomicIntegerArray(3);
        array.set(0, 100); // 指定数组的下标元素赋值
        array.set(1, 200);
        array.set(2, 300);
        System.out.println(array.get(0)); // 根据索引获取值
        System.out.println(array.get(1));
        System.out.println(array.get(2));
    }

    @Test
    public void test(){
        AtomicReferenceArray<String> array = new AtomicReferenceArray<>(3);
        array.set(0,"abc");
        array.set(1,"def");
        array.set(2,"ghi");
        System.out.println(array);
    }
}
