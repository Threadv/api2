package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.app.transaction.dao.BuyMagazineRecordDao;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.story.dao.MagazineDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.story.entity.Magazine;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.transaction.service.BuyMagazineRecordService;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryRecordService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by wml on 2017/2/15.
 */
@Transactional
@Component
public class BuyMagazineRecordServiceImpl implements BuyMagazineRecordService {


    @Autowired
    MagazineDao magazineDao;

    @Autowired
    BuyMagazineRecordDao buyMagazineRecordDao;

    @Autowired
    UserService userService;

    @Autowired
    StoryDao storyDao;

    @Autowired
    BuyStoryRecordService buyStoryRecordService;

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

    @Transactional
    @Override
    public Page<BuyMagazineRecord> getBuyMagazineRecordByUserIdAndMagazineId(Long userId, Long magazineId) {
        Integer pageNo =0;
        Integer pageSize =10;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Page<BuyMagazineRecord> buyMagazineRecords=this.buyMagazineRecordDao.getRecordByUserIdAndMagazineId(userId,magazineId,pageable);
        return buyMagazineRecords;

    }

    @Override
    public void giveNewUserNowMagazineAndStory(Long userId) {
        this.addIsNowBuyMagazineRecordAndBuyStoryRecord(userId, StoryConfig.BUY_STORY_RECORD_SUBSCRIPTION);
    }
    @Override
    public void addIsNowBuyMagazineRecordAndBuyStoryRecord(Long userId, Integer type){

        /**
         * ????????????????????????????????????
         * ??????????????????????????????????????????????????????????????????
         */
        Pageable pageable = new PageRequest(0, 100, new Sort(Sort.Direction.ASC,"orderBy","id"));//?????????????????????????????????????????????
        Page<Story> storyPage = storyDao.getStorysByIsNow(1,pageable);
        List<Story> storyList = storyPage.getContent();
        List<BuyStoryRecord> buyStoryRecordLists;
        for (Story story : storyList) {
            /**
             * ????????????????????????
             */
            buyStoryRecordLists = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndStoryId(userId,story.getId());
            if (buyStoryRecordLists.size() == 0) {
                buyStoryRecordService.addBuyStoryRecord(userId,story.getId(), type);
            }
        }
        /**
         * ???????????????????????????????????????????????????????????????????????????????????????
         */
        Pageable magazinePageable = new PageRequest(0, 6, new Sort(Sort.Direction.ASC,"publishTime","id"));
        Page<Magazine> magazinePage = magazineDao.getMagazinePageByIsNow(1 ,magazinePageable);
        List<Magazine> magazineList = magazinePage.getContent();
        for(Magazine item : magazineList){
            this.addBuyMagazineRecord(userId,item.getId());
        }
    }
    @Override
    public BuyMagazineRecord addBuyMagazineRecord(Long userId, Long magazineId){
        User user = userService.getUser(userId);
        Pageable pageable = new PageRequest(0, 3, new Sort(Sort.Direction.DESC,"id"));
        Page<BuyMagazineRecord> buyMagazineRecordPage = buyMagazineRecordDao.getRecordByUserIdAndMagazineId(userId, magazineId,pageable);
        BuyMagazineRecord buyMagazineRecord = new BuyMagazineRecord();
        if(buyMagazineRecordPage.getContent().size() == 0){
            buyMagazineRecord.setMagazineId(magazineId);
            buyMagazineRecord.setCreateTime(new Date());
            buyMagazineRecord.setUser(user);
            buyMagazineRecord = buyMagazineRecordDao.save(buyMagazineRecord);
        } else {
            buyMagazineRecord =  buyMagazineRecordPage.getContent().get(0);
        }
        return buyMagazineRecord;
    }

}
