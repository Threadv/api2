package com.ifenghui.storybookapi.style;

public enum SignRecordStyle {
    /**
     * 类型
     */
    SHARE_TYPE(1,"周计划分享"),
    READ_TYPE(2,"第一份阅读报告");

    int id;
    String name;

    SignRecordStyle(int id, String name){
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

    public static SignRecordStyle getById(int id){
        for(SignRecordStyle style: SignRecordStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
