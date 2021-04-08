package com.ifenghui.storybookapi.style;

public enum VipFriendCardStatusStyle {

    HAS_NOT_GET(0, "未领取"),
    HAS_GET(1, "已领取"),
    HAS_USE(2, "已使用");

    int id;
    String name;

    VipFriendCardStatusStyle(int id, String name){
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

    public static VipFriendCardStatusStyle getById(int id) {
        for (VipFriendCardStatusStyle style : VipFriendCardStatusStyle.values()) {
            if (style.getId() == id) {
                return style;
            }
        }
        return null;
    }

}
