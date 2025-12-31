package com.quan.lockfree;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//     只能通过反射获取Unsafe对象
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Field count = Counter.class.getDeclaredField("count");
        long countOffset = unsafe.objectFieldOffset(count); // 获取Counter类中名为count的实例字段在对象内存中的偏移地址
        Counter counter = new Counter();
//      将counter对象的count执行一次比较并交换，如果该字段的值等于expected，就把它原子的改为x。否则不做任何操作
        unsafe.compareAndSwapInt(counter,countOffset,0,100);
        System.out.println(counter.getCount());
    }

}

@Data
class Counter {
    volatile int count;
}
