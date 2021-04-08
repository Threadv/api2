package com.ifenghui.storybookapi.app.presale.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.config.MyEnv;

import javax.persistence.*;


@Entity
@Table(name = "story_pre_sale_goods")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PreSaleGoods {

//    public static String ossUrl;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    Integer activityId;

    String content;

    /**图片*/
    String icon;

    /**消息类型*/
    Integer price;

    /**类型 1没有实物 2有实物*/
    Integer type;

    /**库存*/
    Integer storage;

    /**兑换码类型*/
    Integer codeType;

    //是否需要物流中心配送
    Integer isExpressCenter;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIconUrl() {
        if (icon.contains("http")) {
            return icon;
        } else {
            return MyEnv.env.getProperty("oss.url") + icon;
        }
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getIsExpressCenter() {
        return isExpressCenter;
    }

    public void setIsExpressCenter(Integer isExpressCenter) {
        this.isExpressCenter = isExpressCenter;
    }
}
