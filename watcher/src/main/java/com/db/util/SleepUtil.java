package com.db.util;


import java.util.concurrent.TimeUnit;

public class SleepUtil {


    public static void sleep(long mills){
        if(mills <= 0) return;
        try {
            TimeUnit.MILLISECONDS.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
