package com.ifenghui.storybookapi.util;

/**
 * Created by jia on 2016/12/28.
 */
public class SetXMLUtil {

    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";

    }
}
