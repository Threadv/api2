package com.ifenghui.storybookapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    public static String isPhone(String phone) {
//        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        String regex = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        if (phone.length() != 11) {
            return "请输入正确的手机号";
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return "请填入正确的手机号";
            }
            return "";
        }
    }
}


