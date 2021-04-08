package com.ifenghui.storybookapi.style;

public enum CashAccountApplyStatusStyle {
    DEFAULT(0, "默认无效果"),
    USER_APPLY(1, "用户申请中"),
    WAIT_PAY_CASH(2, "申请通过等待付款"),
    SUCCESS_FINISH(3, "已完成"),
    FAIL_FINISH(4, "已驳回");

    int id;
    String name;

    CashAccountApplyStatusStyle(int id, String name){
        this.id = id;
        this.name = name;
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

    public static CashAccountApplyStatusStyle getById(int id){
        for(CashAccountApplyStatusStyle style:CashAccountApplyStatusStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }

}
