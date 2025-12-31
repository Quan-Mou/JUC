package com.quan.lockfree;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference解决ABA问题
 */
@Slf4j
public class AtomicStampedReferenceTest {


    public static void main(String[] args) {
        AtomicStampedReference<String> value = new AtomicStampedReference<String>("A",0);
        int firstStamp = value.getStamp();
        String firstValue = value.getReference();
        log.info("firstStamp = {}, firstValue = {}", firstStamp, firstValue);
//       参数：期望的值，修改的值，期望的版本号，修改后的版本号
        System.out.println(value.compareAndSet("A", "B", 0, 1));
        System.out.println(value.compareAndSet("B", "A", 1, 2));

    }

    @Test
    public void test(){
        AtomicReference<String> v = new AtomicReference<>("A");
        System.out.println(v.compareAndSet("A", "B"));
        System.out.println(v.compareAndSet("B", "A"));
        System.out.println(v.get());
    }
}
