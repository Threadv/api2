package com.ifenghui.storybookapi.util;

import java.math.BigInteger;
import java.util.Random;

/**
 * vip卡号生成工具类
 * Created by wslhk on 2016/12/29.
 */
public class VipCardUtil {

    public static String idToCode(long id){
        if(id>999999999){
            return "error";
        }
        String idStr=id+"";
        int key=new Random().nextInt(999);
        while(idStr.length()<10){
            idStr="0"+idStr;
        }
        while(key<100){
            key=key*10;
        }
        idStr=key+idStr;
        idStr=new BigInteger(idStr).toString(36);
        return idStr;
    }

    public static long codeToId(String code){
        String src=new BigInteger(code,36).toString(10);
        return Long.parseLong(src.substring(4,13));

    }


    public static void main(String[] args){
        String bb=idToCode(1999999999);
        int l=bb.length();
        long cc=codeToId(bb);
//        String aa=new BigInteger("10000000000000000").toString(36);

//        bb=bb;
//        String str=baseString(new BigInteger("10000000000000000"),36);
//        str=str;
    }






}
