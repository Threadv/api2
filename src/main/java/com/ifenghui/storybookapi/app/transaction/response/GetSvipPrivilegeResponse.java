package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.app.app.response.BaseResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GetSvipPrivilegeResponse extends BaseResponse {


    Integer isSvip;

    String title;

    String img;

    String endTime;

    List<ReturnSvip> returnSvipList;

    Integer isCheck;

    public Integer getIsSvip() {
        return isSvip;
    }

    public void setIsSvip(Integer isSvip) {
        this.isSvip = isSvip;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<ReturnSvip> getReturnSvipList() {
        return returnSvipList;
    }

    public void setReturnSvipList(List<ReturnSvip> returnSvipList) {
        this.returnSvipList = returnSvipList;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    @JsonProperty("imgPathList")
    public List<VipImgPath> getImgList() {
        List<VipImgPath> imgPathList = new ArrayList<>();
        imgPathList.add(
            new VipImgPath(750,1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/1.png")
        );
        imgPathList.add(
            new VipImgPath(750, 1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/2.png")
        );
        imgPathList.add(
            new VipImgPath(750, 1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/3.png")
        );
        imgPathList.add(
            new VipImgPath(750, 1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/4.png")
        );
        imgPathList.add(
            new VipImgPath(750,1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/5.png")
        );
        imgPathList.add(
            new VipImgPath(750, 1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/6.png")
        );
        imgPathList.add(
            new VipImgPath(750, 1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/7.png")
        );
        imgPathList.add(
            new VipImgPath(750, 1000, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/8.png")
        );
        imgPathList.add(
            new VipImgPath(750, 1094, "https://storybook.oss-cn-hangzhou.aliyuncs.com/vip/content/9.png")
        );

        return imgPathList;
    }
}
