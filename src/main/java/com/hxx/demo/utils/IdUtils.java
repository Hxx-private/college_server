package com.hxx.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Hxx
 */
public class IdUtils {
    /**
     * @return java.lang.String
     * @Author Hxx
     * @Description //TODO 根据当天日期生成流水号
     * @Date 14:39 2019/11/6
     * @Param []
     **/
    public static String getNumberForPK() {
        String id = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String temp = sf.format(new Date());
        int random = (int) (Math.random() * 10000);
        id = temp + random;
        return id;
    }

    /**
     * @return java.lang.String
     * @Author Hxx
     * @Description //TODO 生成工号
     * @Date 10:58 2019/11/7
     * @Param []
     **/
    public static String getNumber() {
        String number = "";
        String temp = "100";
        int random = (int) (Math.random() * 100);
        number = temp + random;
        return number;
    }

}
