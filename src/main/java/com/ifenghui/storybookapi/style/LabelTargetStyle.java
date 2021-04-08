package com.ifenghui.storybookapi.style;

public enum LabelTargetStyle {

    Story(1, "故事"),
    Video(2, "视频"),
    Magazine(3, "杂志");
    int id;
    String name;

    LabelTargetStyle(int id, String name) {
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

    public static LabelTargetStyle getById(int id){
        for(LabelTargetStyle style:LabelTargetStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
