package com.ifenghui.storybookapi.style;

public enum CheckCodeStyle {

    REGISTER_CODE(1, "注册验证码", "SMS_12830144"),
    UNENG(2, "优能活动", "SMS_151997013"),
    BAOBAO(3,"宝宝会读","SMS_154500203"),
    EXPRESS_CENTER_PHONE(4,"物流中心短信验证","SMS_159773404");//物流中心手机绑定短信验证码
    int id;
    String name;
    String mode;

    CheckCodeStyle(int id, String name, String mode) {
        this.id = id;
        this.name = name;
        this.mode = mode;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public static CheckCodeStyle getByMode(String mode){
        for(CheckCodeStyle style:CheckCodeStyle.values()){
            if(style.mode.equals(mode)){
                return style;
            }
        }
        return null;
    }
}
