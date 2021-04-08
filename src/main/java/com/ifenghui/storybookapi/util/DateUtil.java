package com.ifenghui.storybookapi.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date addOneYear() {

        //获取时间加一年或加一月或加一天
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        //设置起时间
        cal.setTime(date);
        //增加一年
        cal.add(Calendar.YEAR, 1);
        System.out.println("输出::"+cal.getTime());
        return cal.getTime();
    }


}
