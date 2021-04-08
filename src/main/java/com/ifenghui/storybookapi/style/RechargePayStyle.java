package com.ifenghui.storybookapi.style;

/**
 * 充值订单类型
 * Created by wslhk on 2016/12/22.
 */
public enum RechargePayStyle {
    DEFAULT_NULL(0,"默认无支付状态"),
    WEIXINP_PAY(1,"微信支付"),
    ALI_PAY(2,"阿里支付"),
    IOS_PAY(3,"IOS"),
    HUAWEI_PAY(4,"华为支付"),
    ZHIJIAN_PAY(5,"指尖支付");

    int id;
    String name;
    RechargePayStyle(int id, String name){
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

    public static RechargePayStyle getById(int id){
        switch (id){
            case 1:
                return WEIXINP_PAY;
//                break;
            case 2:
                return ALI_PAY;
//                break;
            case 3:
                return IOS_PAY;
//                break;
            case 4:
                return HUAWEI_PAY;
//                break;
            default:
                return null;
        }
    }
}
