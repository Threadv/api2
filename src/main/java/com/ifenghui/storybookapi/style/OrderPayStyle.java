package com.ifenghui.storybookapi.style;

/**
 * 订单类型
 * Created by wslhk on 2016/12/22.
 */
public enum OrderPayStyle {

    DEFAULT_NULL(0,"默认无支付状态"),
    WEIXINP_PAY(1,"微信支付"),
    ALI_PAY(2,"阿里支付"),
    IOS_BLANCE(3,"ios余额支付"),
    CODE(4,"用券支付"),
    HUAWEI_PAY(5,"华为支付"),
    ZHIJIAN_PAY(6,"指尖支付"),
    ANDRIOD_BLANCE(7, "安卓余额"),
    STORY_COUPON(8, "故事兑换券");

    int id;
    String name;
    OrderPayStyle(int id, String name){
        this.id=id;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static OrderPayStyle getById(int id){
        switch (id){
            case 0:
                return DEFAULT_NULL;
            case 1:
                return WEIXINP_PAY;
            case 2:
                return ALI_PAY;
            case 3:
                return IOS_BLANCE;
            case 4:
                return CODE;
            case 5:
                return HUAWEI_PAY;
            case 6:
                return ZHIJIAN_PAY;
            case 7:
                return ANDRIOD_BLANCE;
            case 8:
                return STORY_COUPON;
            default:
                return null;
        }
    }
}
