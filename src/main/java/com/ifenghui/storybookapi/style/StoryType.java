package com.ifenghui.storybookapi.style;

/**
 * Created by wslhk on 2016/12/22.
 */
public enum StoryType {
    //1故事 2音频 3游戏  5飞船识字课 6艺术 7找不同 8图片认知 9
    SINGLE(1,"故事"),AUDIO(2,"音频"),GAME(3,"游戏"),LESSON_WORD(5,"飞船识字课"),ART(6,"艺术"),FIND_DIFFERENT(7,"找不同"),PIC_KNOWENAGE(8,"图片认知")
    ,VIDEO_LESSON(9,"视频课程"),LESSON_KNOWLEDGE(10,"飞船认知课");

    int id;
    String name;
    StoryType(int id,String name){
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
}
