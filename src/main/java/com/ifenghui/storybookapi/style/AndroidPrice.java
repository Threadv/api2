package com.ifenghui.storybookapi.style;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 价格分布
 * Created by wslhk on 2016/12/22.
 */
public enum AndroidPrice {
    ANDROID_TEST(-1,1)
    ,ANDROID_0(0,0)
    ,ANDROID_100(1,100)
    ,ANDROID_200(2,200)
    ,ANDROID_300(3,300)
    ,ANDROID_400(4,400)
    ,ANDROID_500(5,500)
    ,ANDROID_600(6,600)
    ,ANDROID_700(7,700)
    ,ANDROID_800(8,800)
    ;

    int id;
    int price;

    /**
     * 通过类型获得所有价格
     * @return
     */
    public static List<AndroidPrice> getPrices(){
        List<AndroidPrice> prices=new ArrayList();
        for(AndroidPrice price: AndroidPrice.values()){

                prices.add(price);

        }
        return  prices;
    }


    AndroidPrice( int id, int price){
        this.id=id;
        this.price=price;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public float getPriceFloat() {
        BigDecimal b  =   new BigDecimal(((float)price)/100);
        float   f1   =  b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        return f1;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static class Output{
        int id;
        int price;
        public Output(){}
        public Output(int id,int price){
            this.id=id;
            this.price=price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public AndroidPrice.Output jsonObject(){
            AndroidPrice.Output jsonObj=new AndroidPrice.Output(this.id,this.price);

            return jsonObj;
        }
    }

    public static AndroidPrice getById(int id){
        for(AndroidPrice androidPrice:AndroidPrice.getPrices()){
            if(androidPrice.getId()==id){
                return androidPrice;
            }
        }
        return null;
    }

    public AndroidPrice.Output jsonObject(){
        AndroidPrice.Output jsonObj=new AndroidPrice.Output(this.id,this.price);

        return jsonObj;
    }
}
