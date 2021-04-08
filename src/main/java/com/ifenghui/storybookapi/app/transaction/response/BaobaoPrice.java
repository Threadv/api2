package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaobaoPrice {

    String title;

    String content;
//真实交易价格
    Integer price;
//原始价格
    Integer originalPrice;

    Integer onlineOnly;


    @JsonProperty("iosSubPrice")
    public String getIosSubPrice() {
        return "subscription_auto_a_year";
    }

    public BaobaoPrice() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getOnlineOnly() {
        return onlineOnly;
    }

    public void setOnlineOnly(Integer onlineOnly) {
        this.onlineOnly = onlineOnly;
    }

}
