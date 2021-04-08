package com.ifenghui.storybookapi.app.story.thread;

import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;

public class GroupStoryThread implements Runnable {
    Logger logger=Logger.getLogger(GroupSerialThread.class);
    GroupRelevanceService groupRelevanceService;
    Page<Story> storyPage;
    User user;
    int groupId;
    int pageNo;
    int pageSize;
    public GroupStoryThread(GroupRelevanceService groupRelevanceService, User user, int groupId, int pageNo, int pageSize){
        this.groupRelevanceService=groupRelevanceService;
        this.user=user;
        this.groupId=groupId;
        this.pageNo=pageNo;
        this.pageSize=pageSize;
    }
    @Override
    public void run() {
//        logger.info("------storys begin");
       storyPage = groupRelevanceService.getNewGroupStoryList(user, groupId, pageNo, pageSize);
//        logger.info("------storys end");
    }

    public Page<Story> getStoryPage() {
        if(storyPage==null){
            return new PageImpl<Story>(new ArrayList<>());
        }
        return storyPage;
    }

    public int getGroupId() {
        return groupId;
    }
}
