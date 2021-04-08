package com.ifenghui.storybookapi.util;

import java.text.SimpleDateFormat;

public class UserUtil {

    public static int getUidBySid(String sid){
        String sid1=sid.substring(3,sid.length());
        return Integer.parseInt(sid1)-123;
    }
    /**
     * if(this.createTime==null){
     *             return "";
     *         }
     *         int suid =(int) (this.id + 123);
     *         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy");
     *         String nowYear = simpleDateFormat.format(this.getCreateTime());
     *         return "s" + nowYear + suid;
     */
}
