package com.ifenghui.storybookapi.adminapi.controlleradmin.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;

import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.style.IpBrandStyle;

import java.util.List;

//后台用
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown = true)
public class IpBrand {
    Integer id;
    String title;

    List<SerialStory> serialStories;

    //所有laben
    List<IpLabel> ipLabels;

    String banner;

    String bannerPad;

//
//    List<Story> labelStory;
    public IpBrand(){

    }
    public IpBrand(IpBrandStyle ipBrandStyle){
        this.id=ipBrandStyle.getId();
        this.title=ipBrandStyle.getName();
        this.banner="ipbrand/banner_"+ipBrandStyle.getId()+".png";
        this.bannerPad="ipbrand/banner_pad_"+ipBrandStyle.getId()+".png";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SerialStory> getSerialStories() {
        return serialStories;
    }

    public void setSerialStories(List<SerialStory> serialStories) {
        this.serialStories = serialStories;
    }

    public List<IpLabel> getIpLabels() {
        return ipLabels;
    }

    public void setIpLabels(List<IpLabel> ipLabels) {
        this.ipLabels = ipLabels;
    }

    public String getBanner() {
        return banner;
    }

    public String getBannerUrl() {
        if(banner==null){
            return "";
        }
        return MyEnv.env.getProperty("oss.url")+ banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBannerPad() {
        return bannerPad;
    }

    public void setBannerPad(String bannerPad) {
        this.bannerPad = bannerPad;
    }

    public String getBannerPadUrl() {
        if(bannerPad==null){
            return "";
        }
        return MyEnv.env.getProperty("oss.url")+ bannerPad;
    }
}
