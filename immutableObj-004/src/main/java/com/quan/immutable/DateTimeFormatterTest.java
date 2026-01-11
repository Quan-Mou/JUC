package com.quan.immutable;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateTimeFormatterTest {

    public static void main(String[] args) {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                String format = now.format(date);
                log.info("{}", format);
            },"t" + i).start();
        }
    }
}
