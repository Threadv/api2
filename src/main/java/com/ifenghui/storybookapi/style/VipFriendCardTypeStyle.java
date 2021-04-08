package com.ifenghui.storybookapi.style;

import java.util.List;

public enum VipFriendCardTypeStyle {

    CARD_18_08(1, "第一批", new int[]{100, 35, 36, 230, 132}, "", 3);

    int id;
    String name;
    int[] storyIdList;
    String imgPath;
    int cardNum;

    VipFriendCardTypeStyle(int id, String name, int[] storyIdList, String imgPath, int cardNum){
        this.id = id;
        this.name = name;
        this.storyIdList = storyIdList;
        this.imgPath = imgPath;
        this.cardNum = cardNum;
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

    public int[] getStoryIdList() {
        return storyIdList;
    }

    public void setStoryIdList(int[] storyIdList) {
        this.storyIdList = storyIdList;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public static VipFriendCardTypeStyle getById(int id) {
        for (VipFriendCardTypeStyle style : VipFriendCardTypeStyle.values()) {
            if (style.getId() == id) {
                return style;
            }
        }
        return null;
    }
}
