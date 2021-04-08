package com.ifenghui.storybookapi.style;

/**
 * @Date: 2018/11/12 10:52
 * @Description: 故事分组类型
 */
public enum  StoryGroupStyle {

    /**
     * 故事分组类型
     */
    LESSON(2, "课程",2),
    SERIAL(3, "系列", 3),
    HOT_STORY(4, "热销故事", 4),
    STORY(5, "故事", 5),
    AUDIO(6, "音频", 6),
    RECORD(7, "故事足迹", 7),
    RECOMMEND(0, "精品推荐", 0);

    int id;
    String name;
    int targetType;

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

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    StoryGroupStyle(int id, String name, int targetType){
        this.id = id;
        this.name = name;
        this.targetType = targetType;
    }

    public static StoryGroupStyle getById(int id){
        for(StoryGroupStyle style:StoryGroupStyle.values()){
            if(style.getId()==id){
                return style;
            }
        }
        return null;
    }
}
