package com.ifenghui.storybookapi.style;

/**
 * 钱包类型
 */
public enum WalletStyle {
    DEFAULT(0,"隐藏钱包"),
    IOS_WALLET(1,"IOS钱包"),
    ANDROID_WALLET(2,"安卓钱包"),
    SMALL_PROGRAM_WALLET(3, "小程序钱包"),
    SHARE_CASH_WALLET(4, "现金返现钱包");

    int id;
    String name;
    WalletStyle(int id, String name){
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

    public static WalletStyle getById(int id){
        for(WalletStyle style:WalletStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
