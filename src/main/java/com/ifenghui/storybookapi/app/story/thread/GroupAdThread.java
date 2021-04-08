package com.ifenghui.storybookapi.app.story.thread;

import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class GroupAdThread implements Runnable {
    Logger logger=Logger.getLogger(GroupAdThread.class);
    AdService adService;
    List<Ads> adsList;
//    User user;
//    int groupId;
    String channel;
    String platform;
    User user;
    int isCheckVer;
    String ver;
    public GroupAdThread(AdService adService, String channel,String platform,int isCheckVer,String ver,User user){
        this.channel=channel;
        this.platform=platform;
        this.adService=adService;
        this.isCheckVer=isCheckVer;
        this.user=user;
        this.ver=ver;


    }
    @Override
    public void run() {
//        logger.info("------begin ad");
        adsList=adService.getAds(0,8,isCheckVer,channel,platform,ver,user);

        if(user != null && user.getIsTest().equals(1)){
            List<Ads> adsTestList = adService.getTestAds();
            List<Ads> newAdsList = new ArrayList<>(adsList);
            newAdsList.addAll(adsTestList);
            adsList=newAdsList;
        }
//        logger.info("------end ad");
    }

    public List<Ads> getAdsList() {
        return adsList;
    }
}
