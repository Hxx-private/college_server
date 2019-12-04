package com.hxx.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Hxx
 */
public class DateUtils {
    public static String getSysTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = dateFormat.format(new Date());
        return date;
    }

}
