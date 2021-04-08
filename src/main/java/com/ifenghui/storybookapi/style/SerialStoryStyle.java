package com.ifenghui.storybookapi.style;

public enum SerialStoryStyle {

    /**
     * 系类故事分类
     */
    INDEX_SERIAL(1, "首页故事集"),
    SUBSCRIPTION_SERIAL(2, "订阅故事集"),
    SMART_GAME_SERIAL(3, "益智游戏故事集"),
    PARENT_LESSON_SERIAL(4, "家长课"),
    SUBJECT_SERIAL(5, "专题"),
    IP_STORY_SERIAL(6, "ip故事"),
    AUDIO_SERIAL(7, "音频")
    ;

    int id;
    String name;

    SerialStoryStyle(int id, String name) {
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

    public static SerialStoryStyle getById(int id){
        for(SerialStoryStyle style:SerialStoryStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
