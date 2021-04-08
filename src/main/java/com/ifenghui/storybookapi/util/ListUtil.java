package com.ifenghui.storybookapi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {

    /**
     * 去掉空数据，如果传入null，返回new arraylist
     * @param oldList
     * @param <T>
     * @return
     */
    public static <T> List<T> removeNull(List<? extends T> oldList) {
        if(oldList==null){
            return new ArrayList<>();
        }
        // 你没有看错，真的是有 1 行代码就实现了
        oldList.removeAll(Collections.singleton(null));
        return (List<T>) oldList;

    }

//    public static void main(String[] args){
//        List<Integer> cc=new ArrayList<>();
////        cc.add(11);
//        cc.add(null);
////        cc.add(22);
//        List a=removeNull(cc);
//        a=a;
//    }
}
