package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.StoryPackageDao;
import com.ifenghui.storybookapi.app.story.entity.StoryPackage;
import com.ifenghui.storybookapi.app.story.service.StoryPackageService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
@Component
public class StoryPackageServiceImpl implements StoryPackageService {

    @Autowired
    StoryPackageDao storyPackageDao;

    @Autowired
    StoryService storyService;

    @Override
    public void setStatusActive(int id) {
        StoryPackage storyPackage=storyPackageDao.findOne(id);
        List<StoryPackage> storyPackages=storyPackageDao.findByStoryId(storyPackage.getStory().getId());
        for(StoryPackage storyPackage1:storyPackages){
            if(storyPackage1.getId()!=id){
                storyPackage1.setStatus(0);
                storyPackageDao.save(storyPackage1);
            }

        }
        storyPackage.setStatus(1);
        storyPackage.getStory().setAppFile(storyPackage.getAppFile());
        storyPackageDao.save(storyPackage);


//        return null;
    }
}
