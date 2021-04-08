package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.style.VipPriceStyle;

public class SvipPrice {

    Integer  id;

    String name;

    Integer  days;

    Integer price;

    Integer priceId;

    @JsonProperty("content")
    public String getContent(){
        VipPriceStyle vipPriceStyle = VipPriceStyle.getById(this.id);
        if(vipPriceStyle != null){
            return "故事飞船会员—" + vipPriceStyle.getName();
        } else {
            return null;
        }
    }

    @JsonProperty("iosSubPrice")
    public String getIosSubPrice() {
        VipPriceStyle vipPriceStyle = VipPriceStyle.getById(this.id);
        if(vipPriceStyle != null){
            return vipPriceStyle.getIosSubPrice();
        } else {
            return null;
        }
    }

    public SvipPrice(int id, String name, int days, int price, int priceId) {
        this.id = id;
        this.name = name;
        this.days = days;
        this.price = price;
        this.priceId = priceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    @JsonProperty("imgPath")
    public String getImgPath() {
        return "http://storybook.oss-cn-hangzhou.aliyuncs.com/vip/card.png";
    }
}
