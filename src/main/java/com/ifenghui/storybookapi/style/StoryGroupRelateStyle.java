package com.ifenghui.storybookapi.style;

public enum StoryGroupRelateStyle {

    DEFAULT(0, 0,"默认选择"),
    LANGUAGE_CULTURE(1, 9, "语言文化"),
    SELF_GROWTH(2, 10, "自我成长"),
    SOCIAL_COGNITION(3, 11, "社会认知"),
    ENLIGHTEN_MIND(4, 12, "创意思维");

    int categoryNewId;

    int groupId;

    String name;

    StoryGroupRelateStyle(int categoryNewId, int groupId,String name){
        this.categoryNewId = categoryNewId;
        this.groupId = groupId;
        this.name = name;
    }

    public int getCategoryNewId() {
        return categoryNewId;
    }

    public void setCategoryNewId(int categoryNewId) {
        this.categoryNewId = categoryNewId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static StoryGroupRelateStyle getByCategoryNewId(int id){
        switch (id){
            case 1:
                return LANGUAGE_CULTURE;
            case 2:
                return SELF_GROWTH;
            case 3:
                return SOCIAL_COGNITION;
            case 4:
                return ENLIGHTEN_MIND;
            default:
                return DEFAULT;
        }
    }

    public static StoryGroupRelateStyle getByGroupId(int id){
        switch (id){
            case 9:
                return LANGUAGE_CULTURE;
            case 10:
                return SELF_GROWTH;
            case 11:
                return SOCIAL_COGNITION;
            case 12:
                return ENLIGHTEN_MIND;
            default:
                return DEFAULT;
        }
    }

}
