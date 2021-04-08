package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.GetUserStoryListResponse;
import com.ifenghui.storybookapi.app.story.response.GetUserSubscriptionStoryResponse;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStorySubscriptionRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStorySubscriptionRecord;
import com.ifenghui.storybookapi.app.transaction.service.BuyStorySubscriptionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BuyStorySubscriptionRecordServiceImpl implements BuyStorySubscriptionRecordService {

    @Autowired
    StoryService storyService;

    @Autowired
    BuyStorySubscriptionRecordDao buyStorySubscriptionRecordDao;

    @Override
    public GetUserStoryListResponse getUserStoryList(Long userId, Integer groupId, Integer pageNo, Integer pageSize){
        GetUserSubscriptionStoryResponse response = new GetUserSubscriptionStoryResponse();
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<BuyStorySubscriptionRecord> buyStoryRecords;
        if (groupId==0) {
            buyStoryRecords = buyStorySubscriptionRecordDao.getBuyStorySubscriptionRecordsByUserId(userId, pageable);
        } else if(groupId.equals(8)){
            buyStoryRecords = buyStorySubscriptionRecordDao.getMusicBuyStoryRecordsByUserId(userId, pageable);
        } else {
            buyStoryRecords = buyStorySubscriptionRecordDao.getUserBuyRecordByCatId(userId, groupId, pageable);
        }

        List<BuyStorySubscriptionRecord> buyStoryRecordList = buyStoryRecords.getContent();

        if(groupId.equals(8)){
            for(BuyStorySubscriptionRecord item : buyStoryRecordList){
                Story story = storyService.getStory(item.getStoryId());
                item.setStory(this.setStoryDetail(story.getStory()));
            }
        } else {
            for (BuyStorySubscriptionRecord item : buyStoryRecordList) {
                Story story = storyService.getStory(item.getStoryId());
                item.setStory(this.setStoryDetail(story));
            }
        }
        response.setBuyStoryRecords(buyStoryRecordList);
        response.setJpaPage(buyStoryRecords);
        return response;
    }
    @Override
    public Story setStoryDetail(Story story){
        story.setIsBuy(1);
        story.setIsPurchased(1);
        storyService.setStoryAppFile(story);
        return story;
    }

}
