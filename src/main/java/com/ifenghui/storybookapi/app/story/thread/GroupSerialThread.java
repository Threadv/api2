package com.ifenghui.storybookapi.app.story.thread;

import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.SerialStoryStyle;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class GroupSerialThread implements Runnable {
    Logger logger=Logger.getLogger(GroupSerialThread.class);

    SerialStoryStyle serialStoryStyle;

    SerialStoryService serialStoryService;
    List<SerialStory> serialStoryList;
    User user;
//    int groupId;
    Integer pageNo;
    Integer pageSize;
    List<Integer> searalIds;

    public GroupSerialThread(SerialStoryService serialStoryService,SerialStoryStyle serialStoryStyle, User user, int pageNo, int pageSize){
        this.serialStoryService=serialStoryService;
        this.user=user;
        this.pageNo=pageNo;
        this.pageSize=pageSize;
        this.serialStoryStyle=serialStoryStyle;
    }

    public GroupSerialThread(SerialStoryService serialStoryService,SerialStoryStyle serialStoryStyle, User user,List<Integer> searalIds, int pageNo, int pageSize){
        this.serialStoryService=serialStoryService;
        this.user=user;
        this.pageNo=pageNo;
        this.pageSize=pageSize;
        this.serialStoryStyle=serialStoryStyle;
        this.searalIds=searalIds;
    }
    @Override
    public void run() {
//        logger.info("-----begin serial");
        int userid=0;
        if(user!=null){
            userid=user.getId().intValue();
        }
        Page<SerialStory> serialStoryPage;

        List<SerialStory> result=new ArrayList<>();
        //如果包含searalIds，只输出带id的
        if(searalIds!=null){
            for(Integer serialId:searalIds){
                SerialStory serialStory=serialStoryService.findOne(serialId);
                result.add(serialStory);
            }
//            for(SerialStory serialStory:serialStoryPage.getContent()){
//                if(hasInIds(serialStory.getId().intValue())){
//
//                }
//            }
            serialStoryList=result;
        }else{
            serialStoryPage = serialStoryService.getCommonSerialStoryPage((long)userid, serialStoryStyle, pageNo,pageSize);
            serialStoryList = serialStoryPage.getContent();
        }

        for(SerialStory item : serialStoryList){
            serialStoryService.setSerialStoryIsBuy(user,item);
        }
//        logger.info("-----end serial");
    }

    //允许
    private boolean hasInIds(Integer id){
        if(id==null||searalIds==null){
            return true;
        }
        for(Integer hasId:this.searalIds){
            if(hasId.intValue()==id){
                return true;
            }
        }
        return false;
    }

    public List<SerialStory> getSerialStoryList() {
        return serialStoryList;
    }

    public SerialStoryStyle getSerialStoryStyle() {
        return serialStoryStyle;
    }
}
