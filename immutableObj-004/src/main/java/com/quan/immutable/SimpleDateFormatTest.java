package com.quan.immutable;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Slf4j
public class SimpleDateFormatTest {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "abcd";
//        str.value
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.info("{}",sdf.parse("2025-12-31"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }

    }
}
