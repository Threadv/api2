package com.test;


import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.junit.Test;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2019/10/27.
 */
public class test {

    private Map<String,String> map = new HashMap<>();

    @Test
    public void test(){

        map.put("ssss","dfd");
        System.out.println(map.get("ssss"));

    }

   class test2{

       void tt(){
           String s = map.get("ssss");

           System.out.println(s);
       }
   }

}
