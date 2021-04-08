package com.ifenghui.storybookapi.style;

import java.util.HashMap;
import java.util.Map;

/**
 * 价格分布
 * Created by wslhk on 2016/12/22.
 */
public enum DealerStyle {
    BIGGER(1,40,"实体卡经销商")
    ,MIDDLE(2,20,"电子卡经销商")
    ;

    int id;
    int profitPercent;
    String title;
    DealerStyle(){

    }
    DealerStyle(int id, int profitPercent, String title){
        this.id=id;
        this.profitPercent=profitPercent;
        this.title=title;

    }
    /**
     * 通过类型获得所有价格

     * @return
     */
    static Map dealers=null;
    public static Map<Integer,DealerStyle> getMaps(){
        if(dealers==null){
            dealers=new HashMap();
            for(DealerStyle dealerStyle: DealerStyle.values()){

                dealers.put(dealerStyle.getId(),dealerStyle);

            }
        }

        return  dealers;
    }

    public static class Output{

        int id;
        int profitPercent;
        String title;
        public Output(){

            this.id=0;
            this.profitPercent=0;
            this.title="";
        }
        public Output(int id,int profitPercent,String title){
            this.id=id;
            this.profitPercent=profitPercent;
            this.title=title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProfitPercent() {
            return profitPercent;
        }

        public void setProfitPercent(int profitPercent) {
            this.profitPercent = profitPercent;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public Output jsonObject(){
        Output jsonObj=new Output(this.id,this.profitPercent,this.title);

        return jsonObj;
    }


    /**
     * 通过id获取经销商类型
     * @param id
     * @return
     */
    public static DealerStyle getById(int id){

       return DealerStyle.getMaps().get(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfitPercent() {
        return profitPercent;
    }

    public float getProfitPercentFloat() {
        return ((float) profitPercent)/(float) 100;
    }

    public void setProfitPercent(int profitPercent) {
        this.profitPercent = profitPercent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
