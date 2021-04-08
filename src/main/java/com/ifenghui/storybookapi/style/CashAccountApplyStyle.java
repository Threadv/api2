package com.ifenghui.storybookapi.style;

public enum CashAccountApplyStyle {

    DEFAULT(0,"默认无效果", WalletStyle.DEFAULT, ""),
    ALI_PAY_REFUND(1, "支付宝提现", WalletStyle.DEFAULT, ""),
    WECHAT_PAY_REFUND(2, "微信提现", WalletStyle.DEFAULT, ""),
    IOS_WALLET_REFUND(3, "充值提现（Ios）", WalletStyle.IOS_WALLET, "返现余额充值（Ios）"),
    ANDROID_WALLET_REFUND(4, "充值提现（安卓）", WalletStyle.ANDROID_WALLET, "返现余额充值（安卓）"),
    XIAOCHENGXU_PAY_REFUND(5, "小程序提现", WalletStyle.DEFAULT, "");

    int id;
    String name;
    WalletStyle walletStyle;
    String rechargeIntro;
    CashAccountApplyStyle(int id, String name, WalletStyle walletStyle, String rechargeIntro){
        this.id = id;
        this.name = name;
        this.walletStyle = walletStyle;
        this.rechargeIntro = rechargeIntro;
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

    public WalletStyle getWalletStyle() {
        return walletStyle;
    }

    public void setWalletStyle(WalletStyle walletStyle) {
        this.walletStyle = walletStyle;
    }

    public String getRechargeIntro() {
        return rechargeIntro;
    }

    public void setRechargeIntro(String rechargeIntro) {
        this.rechargeIntro = rechargeIntro;
    }

    public static CashAccountApplyStyle getById(int id){
        for(CashAccountApplyStyle style:CashAccountApplyStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }

}
