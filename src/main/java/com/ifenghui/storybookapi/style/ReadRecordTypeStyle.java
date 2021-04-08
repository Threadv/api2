package com.ifenghui.storybookapi.style;

/**
 * 阅读记录类型
 */
public enum ReadRecordTypeStyle {

    STORY(1, "阅读故事类型"),
    MAGAZINE(2, "杂志类型"),
    OTHER(3, "其他");

    int id;
    String name;
    ReadRecordTypeStyle(int id, String name) {
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

    public static ReadRecordTypeStyle getById(int id){
        for(ReadRecordTypeStyle style:ReadRecordTypeStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
