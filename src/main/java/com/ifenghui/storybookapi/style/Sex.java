package com.ifenghui.storybookapi.style;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 价格分布
 * Created by wslhk on 2016/12/22.
 */
public enum Sex {
    MAN(1,"男"),
    WOMAN(0,"女");



    int id;
    String title;

    /**
     * 通过类型获得所有价格
     * @return
     */
    public static List<Sex> getPrices(){
        List<Sex> prices=new ArrayList();
        for(Sex price: Sex.values()){

                prices.add(price);

        }
        return  prices;
    }


    Sex(int id, String title){
        this.id=id;
        this.title=title;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public static Sex getById(int id){
        for(Sex androidPrice: Sex.getPrices()){
            if(androidPrice.getId()==id){
                return androidPrice;
            }
        }
        return null;
    }


}
