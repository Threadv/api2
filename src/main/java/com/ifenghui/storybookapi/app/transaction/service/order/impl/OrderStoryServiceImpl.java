package com.ifenghui.storybookapi.app.transaction.service.order.impl;

import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.transaction.dao.OrderStoryDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderStory;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderStoryService;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OrderStoryServiceImpl implements OrderStoryService {

    @Autowired
    StoryDao storyDao;

    @Autowired
    OrderStoryDao orderStoryDao;

    @Override
    public void addOrderStoryList(List<Long> storyIds, User user, Long payStoryOrderId) {
        for (Long storyId:storyIds){
            Story story = storyDao.findOne(storyId);
            OrderStory orderStory = new OrderStory();
            orderStory.setUserId(user.getId());
            orderStory.setUserId(user.getId());
            orderStory.setStoryId(storyId);
            orderStory.setStoryPrice(story.getPrice());
            orderStory.setOrderId(payStoryOrderId);
            orderStory.setCreateTime(new Date());
            orderStoryDao.save(orderStory);
        }
    }
}
