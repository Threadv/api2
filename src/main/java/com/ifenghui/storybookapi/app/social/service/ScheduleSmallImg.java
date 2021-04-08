package com.ifenghui.storybookapi.app.social.service;
@Deprecated
public class ScheduleSmallImg {

    Integer width;

    Integer height;

    String imgPath;

    Integer isVideo;

    String videoPath;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getIsVideo() {
        if(this.isVideo == null){
            this.isVideo = 0;
        }
        return isVideo;
    }

    public void setIsVideo(Integer isVideo) {
        this.isVideo = isVideo;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
}
