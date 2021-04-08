package com.ifenghui.storybookapi.style;

public enum SerialStoryDetailStyle {

    /**
     * 系类故事分类
     */
    WULALA_SERIAL(1,"乌拉拉探索文明古国"),
    AUDIO_SERIAL(2,"故事飞船精选音频"),
            ;

    int id;
    String name;

    SerialStoryDetailStyle(int id, String name) {
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

    public static SerialStoryDetailStyle getById(int id){
        for(SerialStoryDetailStyle style: SerialStoryDetailStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
