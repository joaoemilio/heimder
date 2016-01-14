package com.heimder.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JoaoEmilio on 29/12/2015.
 */
public class DateUtil {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hhmmss");
    private static DateUtil me;

    private DateUtil() {

    }

    public String getNow() {
        return sdf.format(new Date());
    }

    public static void main(String args[]) {
        System.out.println(new DateUtil().getNow());
    }

    public static DateUtil getInstance() {
        if(me == null) {
            me = new DateUtil();
        }
        return me;
    }
}
