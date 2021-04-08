package com;

import com.alipay.sign.Base64;
import com.huawei.pay.callback.demo.util.RSA;
import com.ifenghui.storybookapi.app.system.service.MessageService;
import com.ifenghui.storybookapi.app.system.service.impl.MessageServiceImpl;
import com.ifenghui.storybookapi.style.CheckCodeStyle;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wslhk on 2017/9/27.
 */

public class TestChedi {





//
//    @Test
//    public void testaa() throws UnsupportedEncodingException {
////char[] a='\0xe5';
////        String ss=new String(byte('\0xe5'),"");
////        byte[] b = {(byte)0xE5,(byte)0x85,(byte)0x83,(byte)0xE5,(byte)0xAE,(byte)0x9D };
////        try {
////            System.out.println(new String(b, "UTF-8"));
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        }
//
////        String a1="\\xE5\\x85\\x83\\xE5\\xAE\\x9D";
////        String str=hexToStringUTF("xE5");
////        String object=new String(str.getBytes(),"utf-8");
//
//        String str = "E58583E5AE9D";// 需要转换的字符串
//        byte[] b = new byte[str.length() / 2];// 每两个字符为一个十六进制确定数字长度
//        for (int i = 0; i < b.length; i++) {
//            // 将字符串每两个字符做为一个十六进制进行截取
//            String a = str.substring(i * 2, i * 2 + 2);
//            b[i] = (byte) Integer.parseInt(a, 16);// 将如e4转成十六进制字节，放入数组
//        }
//
//        try {
//            // 将字节数字以utf-8编码以字符串形式输出
//            System.out.println(new String(b, "UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testSms(){
        String phones="";

        String codes="";

//        phones="13736019415";
//        codes="1c4fed158";
        MessageServiceImpl messageService=new MessageServiceImpl();
        String[] phone=phones.split("\n");//{"13611220831","13691167501"};
        String[] code=codes.split("\n");
//        messageService.
        for(int i=0;i<phone.length;i++){
            Map map=new HashMap();
            map.put("phone",phone[i].trim());
            map.put("code",code[i].trim());
            System.out.println(phone[i].trim()+":"+code[i].trim());
//            continue;

            /**
             * cmsconfig.oss.accesskeyid=sjxy5uSVzStt7jAj
             cmsconfig.oss.accesskeysecret=vpUFxbGclQpsg0fvK04wfvf5PYhfZO
             */
//            messageService.setAccessKeyId("sjxy5uSVzStt7jAj");
//            messageService.setAccessKeySecret("vpUFxbGclQpsg0fvK04wfvf5PYhfZO");
//            messageService.sendSms((String)map.get("phone"), CheckCodeStyle.BAOBAO.getMode(), map);
        }

    }

    public static String hexToStringUTF(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
            return "";
        }
        return s;
    }
}
