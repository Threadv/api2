package com.ifenghui.storybookapi.style;

/**
 * 快递类型
 * Created by wslhk on 2016/12/22.
 */
public enum ExpressStyle {

    DEFAULT_NULL(0,"默认无",""),
    YUAN_TONG(1,"圆通快递","//storybook.oss-cn-hangzhou.aliyuncs.com/shop/expressLogo/kd_yt.png"),
    EMS(2,"EMS","//storybook.oss-cn-hangzhou.aliyuncs.com/shop/expressLogo/kd_ems.png"),
    ZHONG_TONG(3,"中通快递","//storybook.oss-cn-hangzhou.aliyuncs.com/shop/expressLogo/kd_zt.png"),
    YUN_DA(4,"韵达快递","//storybook.oss-cn-hangzhou.aliyuncs.com/shop/expressLogo/kd_yd.png"),
    SHUN_FENG(5,"顺丰快递","//storybook.oss-cn-hangzhou.aliyuncs.com/shop/expressLogo/kd_sf.png"),
    BSHT(6,"百世快递","//storybook.oss-cn-hangzhou.aliyuncs.com/shop/expressLogo/kd_bsht.png");


    int id;
    String name;
    String logo;
    ExpressStyle(int id, String name,String logo){
        this.id=id;
        this.name=name;
        this.logo=logo;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public static ExpressStyle getById(int id){
        switch (id){
            case 0:
                return DEFAULT_NULL;
            case 1:
                return YUAN_TONG;
            case 2:
                return EMS;
            case 3:
                return ZHONG_TONG;
            case 4:
                return YUN_DA;
            case 5:
                return SHUN_FENG;
            case 6:
                return BSHT;
            default:
                return null;
        }
    }
}
