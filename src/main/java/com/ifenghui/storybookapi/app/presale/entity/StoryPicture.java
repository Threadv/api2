package com.ifenghui.storybookapi.app.presale.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StoryPicture {

    Integer id;

    Integer gameStoryId;

    String gameImgUrl;

    String gameTitle;

    String littleTitle;

    Integer storyId;

    String imgUrl;

    String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameStoryId() {
        return gameStoryId;
    }

    public void setGameStoryId(Integer gameStoryId) {
        this.gameStoryId = gameStoryId;
    }

    public String getGameImgUrl() {
        return gameImgUrl;
    }

    public void setGameImgUrl(String gameImgUrl) {
        this.gameImgUrl = gameImgUrl;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getLittleTitle() {
        return littleTitle;
    }

    public void setLittleTitle(String littleTitle) {
        this.littleTitle = littleTitle;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
