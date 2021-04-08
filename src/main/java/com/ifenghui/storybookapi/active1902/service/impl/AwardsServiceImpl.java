package com.ifenghui.storybookapi.active1902.service.impl;


import com.ifenghui.storybookapi.active1902.dao.AwardsDao;
import com.ifenghui.storybookapi.active1902.entity.Awards;
import com.ifenghui.storybookapi.active1902.service.AwardsService;
import com.ifenghui.storybookapi.active1902.style.AwardsStyle;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date: 2019/2/19 16:02
 * @Description:
 */
@Component
public class AwardsServiceImpl implements AwardsService {


    @Autowired
    AwardsDao awardsDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    SerialStoryDao serialStoryDao;

    @Override
    public List<Awards> getAwardsByType(AwardsStyle awardsStyle, Integer scheduleId) {

        Pageable pageable = new PageRequest(0, 6, new Sort(Sort.Direction.DESC, "id"));
        List<Awards> awardsList;
        if (awardsStyle == AwardsStyle.STORY) {
            awardsList = awardsDao.findAwardsByTypeAndScheduleId(awardsStyle.getId(), scheduleId, pageable);
        } else {
            awardsList = awardsDao.findAwardsByType(awardsStyle.getId(), pageable);
        }
        for (Awards a : awardsList) {
            if (a.getStoryId() > 0) {
                Story story = storyDao.findOne(a.getStoryId().longValue());
                a.setStory(story);
            } else if (a.getSerialStoryId() > 0) {
                SerialStory serialStory = serialStoryDao.findOne(a.getSerialStoryId().longValue());
                a.setSerialStory(serialStory);
            }
        }
        return awardsList;
    }
}
