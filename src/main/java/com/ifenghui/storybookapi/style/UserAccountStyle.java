package com.ifenghui.storybookapi.style;

/**
 * 3方账号
 * Created by wslhk on 2016/12/22.
 */
public enum UserAccountStyle {
    PHONE(0, "电话号码"),WEIXIN(1,"微信"),QQ(2,"QQ"),WEIBO(3,"微博"),GUEST(4,"游客"),HUAWEI(5,"华为"),ALI(6,"支付宝");

    int id;
    String name;
    UserAccountStyle(int id, String name){
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

    public static UserAccountStyle getById(int id){
        for(UserAccountStyle accountStyle:UserAccountStyle.values()){
            if(accountStyle.getId()==id){
                return accountStyle;
            }
        }
        return null;
    }
}
