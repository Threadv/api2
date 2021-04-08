package com.ifenghui.storybookapi.util;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jia on 2016/12/29.
 */
public class Map2StringUtil {
    /**
     * 方法名称:transMapToString
     * 传入参数:map
     * 返回值:String 形如 username'chenziwen^password'1234
     */
    public static String transMapToString(Map map){
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (java.util.Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":
                    entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }

    public static String mapToString(Map map){
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (java.util.Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":
                    entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");
        }
        return sb.toString();
    }
}
