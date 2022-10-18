package com.db.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    private static final DateTimeFormatter fTime = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final DateTimeFormatter fDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public static String getTime(){
        return LocalDateTime.now().format(fTime);
    }

    public static String getDate(){
        return LocalDate.now().format(fDate);
    }


}
