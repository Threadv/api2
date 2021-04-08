package com.ifenghui.storybookapi.style;

public enum SvipStyle {

    /**
     * VIP 和宝宝会读（优能计划）
     */
    DEFAULT_VIP(0, "默认",10),
    LEVEL_TWO(2, "七天提现权限", 10),
    LEVEL_ONE(1, "内部", 10),
    LEVEL_THREE(3, "VIP会员卡", 10),
    LEVEL_FOUR(4, "赠送会员卡", 10),
    ABILITY_PLAN(7, "全年宝宝会读", 10);

    int id;
    String name;
    int discount;

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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    SvipStyle(int id, String name, int discount){
        this.id = id;
        this.name = name;
        this.discount = discount;
    }

    public static SvipStyle getById(int id){
        for(SvipStyle style:SvipStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
