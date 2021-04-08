package com.ifenghui.storybookapi.util;

import java.util.Random;

/**
 * 兑换码生成工具
 */
public class CodeUtil {
    /**
     * 根据id创建兑换码，算法模拟php之前的算法
     * @param id
     * @return
     */
    public static String createCoce(Integer id){
//        Integer x = 666;
//        String hex = x.toHexString(x);

        Integer r = new Random().nextInt(80)+10;
        String pre="";
        for(int i=0;i<8-(""+id).length();i++){
            pre = pre+"0";
        }
        String newCode = r+pre+id;
        Long codesrc=Long.parseLong(newCode);
        String code=codesrc.toHexString(codesrc);
        return  code;
    }

//    public static void main(String[] args){
//        String code=createCoce(199999999);
//        System.out.println(code);
//    }
}
