package com.ifenghui.storybookapi.style;

/**
 * 3方账号
 * Created by wslhk on 2016/12/22.
 */
public enum OrderStatusStyle {
    WAIT_PAY(0,"等待支付"),PAY_SUCCESS(1,"支付成功"),PAY_BACK(2,"已退款/已撤销");

    int id;
    String name;
    OrderStatusStyle(int id, String name){
        this.id=id;
        this.name=name;
    }

    public static OrderStatusStyle getById(int id){
        switch (id){
            case 0:
                return WAIT_PAY;
            case 1:
                return PAY_SUCCESS;
            case 2:
                return PAY_BACK;
            default:
                return null;
        }
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
}
