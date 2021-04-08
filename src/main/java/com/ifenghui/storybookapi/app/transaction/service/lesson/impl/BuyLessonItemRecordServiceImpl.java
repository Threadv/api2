package com.ifenghui.storybookapi.app.transaction.service.lesson.impl;

import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.entity.UserReadStudyVideo;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.social.service.UserReadStudyVideoService;
import com.ifenghui.storybookapi.app.story.dao.LessonItemDao;
import com.ifenghui.storybookapi.app.story.dao.LessonItemRelateDao;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;
import com.ifenghui.storybookapi.app.story.service.LessonItemRelateService;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.BuyLessonItemRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.LessonItemReadRecord;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.LessonItemReadRecordService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class BuyLessonItemRecordServiceImpl implements BuyLessonItemRecordService {

    @Autowired
    LessonItemReadRecordService lessonItemReadRecordService;

    @Autowired
    BuyLessonItemRecordDao buyLessonItemRecordDao;

    @Autowired
    LessonItemDao lessonItemDao;

    @Autowired
    UserService userService;

    @Autowired
    LessonItemRelateService lessonItemRelateService;

    @Autowired
    UserReadRecordService userReadRecordService;

    @Autowired
    LessonItemRelateDao lessonItemRelateDao;

    @Autowired
    UserReadStudyVideoService userReadStudyVideoService;

    @Override
    public Integer isBuyLessonItem(Integer userId, Integer lessonItemId) {
        Integer isBuyLessonItem;
        BuyLessonItemRecord buyLessonItemRecord = this.getBuyLessonItemRecordByUserIdAndLessonItemId(userId, lessonItemId);
        if (buyLessonItemRecord != null) {
            isBuyLessonItem = 1;
        } else {
            isBuyLessonItem = 0;
        }
        return isBuyLessonItem;
    }

    @Override
    public BuyLessonItemRecord getBuyLessonItemRecordByUserIdAndLessonItemId(Integer userId, Integer lessonItemId) {
        BuyLessonItemRecord buyLessonItemRecord = null;
        Long countItem=buyLessonItemRecordDao.countBuyLessonItemRecordsByUserIdAndLessonItemId(userId,lessonItemId);
        if(countItem==null||countItem==0){
            return null;
        }
        buyLessonItemRecord = buyLessonItemRecordDao.getBuyLessonItemRecordsByUserIdAndLessonItemId(userId, lessonItemId);
        return buyLessonItemRecord;
    }

    @Override
    public BuyLessonItemRecord getHasLessonRecord(Integer userId, Integer lessonId, Integer isRead, Sort.Direction sort) {
        Pageable pageable = new PageRequest(0, 1, new Sort(sort, "lessonNum"));
        Page<BuyLessonItemRecord> buyLessonItemRecordPage = buyLessonItemRecordDao.getBuyLessonItemRecordsByLessonIdAndUserId(lessonId, userId, isRead, pageable);
        BuyLessonItemRecord buyLessonItemRecord = null;
        if (buyLessonItemRecordPage.getContent().size() > 0) {
            buyLessonItemRecord = buyLessonItemRecordPage.getContent().get(0);
        }
        return buyLessonItemRecord;
    }

    @Transactional
    @Override
    public Integer getBuyLessonItemCount(Integer userId, Integer lessonId) {
        Integer buyLessonItemCount = buyLessonItemRecordDao.getBuyLessonItemRecordCountByLessonIdAndUserId(lessonId, userId);
        if (buyLessonItemCount == null) {
            buyLessonItemCount = 0;
        }
        return buyLessonItemCount;
    }

    @Transactional
    @Override
    public BuyLessonItemRecord addBuyLessonItemRecord(Integer userId, Integer lessonId, Integer lessonItemId,Integer isBaobao) {
        LessonItem lessonItem = lessonItemDao.findOne(lessonItemId);
        User user = userService.getUser(userId);
        if (lessonItem != null) {
            BuyLessonItemRecord buyLessonItemRecordOld = this.getBuyLessonItemRecordByUserIdAndLessonItemId(userId, lessonItemId);
            if (buyLessonItemRecordOld != null) {
                if(buyLessonItemRecordOld.getIsBaobao()==1&&isBaobao==0){
                    //如果从宝宝会读变到直接购买，需要改下状态
                    buyLessonItemRecordOld.setIsBaobao(isBaobao);
                    buyLessonItemRecordOld=this.update(buyLessonItemRecordOld);
                }
                return buyLessonItemRecordOld;
            }
            BuyLessonItemRecord buyLessonItemRecord = new BuyLessonItemRecord();
            buyLessonItemRecord.setCreateTime(new Date());
            buyLessonItemRecord.setUserId(userId);
            buyLessonItemRecord.setLessonId(lessonId);
            buyLessonItemRecord.setLessonItemId(lessonItemId);
            buyLessonItemRecord.setLessonNum(lessonItem.getLessonNum());

            if (user != null) {
                buyLessonItemRecord.setIsTest(user.getIsTest());
            } else {
                buyLessonItemRecord.setIsTest(0);
            }
            buyLessonItemRecord.setIsRead(0);
            buyLessonItemRecord.setIsFree(lessonItem.getIsFree());
            buyLessonItemRecord.setIsBaobao(isBaobao);
            buyLessonItemRecord = buyLessonItemRecordDao.save(buyLessonItemRecord);
            return buyLessonItemRecord;
        } else {
            return null;
        }
    }

    @Override
    public Integer getMaxLessonNumByLessonIdAndUserId(Integer lessonId, Integer userId) {
        Integer maxLessonNum = buyLessonItemRecordDao.getMaxLessonNumByLessonIdAndUserId(lessonId, userId);
        if (maxLessonNum == null) {
            maxLessonNum = 0;
        }
        return maxLessonNum;
    }

    @Override
    public void setStoryOfLessonItemIsRead(Long storyId, Long userId, boolean isVideo) {
        LessonItemRelate lessonItemRelate = lessonItemRelateService.getLessonItemRelateByStoryIdAndIsVideo(storyId.intValue(), isVideo);

        if (lessonItemRelate == null) {
            return;
        }

        List<LessonItemRelate> lessonItemRelateList = lessonItemRelateService.getLessonItemListByStoryId(lessonItemRelate);
        Integer isRead = this.getIsReadAllStoryByLessonItemRelateList(userId, lessonItemRelateList);

        BuyLessonItemRecord buyLessonItemRecord = this.getBuyLessonItemRecordByUserIdAndLessonItemId(userId.intValue(), lessonItemRelate.getItemId());
        if (buyLessonItemRecord != null) {
            if (isRead.equals(1)) {
                buyLessonItemRecord.setIsRead(1);
                buyLessonItemRecordDao.save(buyLessonItemRecord);
            }
        }

        if (userService.getUser(userId).getSvip().equals(SvipStyle.LEVEL_THREE.getId()) && isRead.equals(1)) {
            //添加阅读记录 无购买记录
            lessonItemReadRecordService.createLessonItemRecord(userId.intValue(), lessonItemRelate.getItemId());
        }

    }

    @Override
    public Integer getHasReadLessonCount(Integer lessonId, Integer userId) {
        Integer finishLessonNum = buyLessonItemRecordDao.getHasReadLessonCount(lessonId, userId, 1);
        if (finishLessonNum == null) {
            finishLessonNum = 0;
        }
        return finishLessonNum;
    }

    @Override
    public Integer getIsReadAllStoryByLessonItemRelateList(Long userId, List<LessonItemRelate> lessonItemRelateList) {
        Integer isRead = 1;
        if (userId.equals(0L)) {
            isRead = 0;
        } else {
            if (lessonItemRelateList != null) {
                for (LessonItemRelate item : lessonItemRelateList) {
                    if (item.getType().equals(3)) {
                        Page<UserReadStudyVideo> userReadStudyVideoPage = userReadStudyVideoService.getUserReadStudyVideoByUserIdAndVideoId(userId.intValue(), item.getStoryId());
                        if (userReadStudyVideoPage.getContent().size() == 0) {
                            isRead = 0;
                        }
                    } else {
//                        Page<UserReadRecord> userReadRecordPage = userReadRecordService.getReadRecordsByUserIdAndStoryIdWithOutStory(userId, item.getStoryId().longValue(), 0, 1);
                        Long count=userReadRecordService.countReadRecordsByUserIdAndStoryId(userId,item.getStoryId().longValue());
                        if (count==null||count == 0) {
                            isRead = 0;
                        }
                    }
                }
            }
        }
        return isRead;
    }

    @Override
    public boolean isReadFreeLesson(Integer userId) {
        /**
         * 成长版 lessonItemId = 53
         * 启蒙版 lessonItemId = 1
         */
        BuyLessonItemRecord growthRecord = this.getBuyLessonItemRecordByUserIdAndLessonItemId(userId, 53);
        BuyLessonItemRecord enlightenRecord = this.getBuyLessonItemRecordByUserIdAndLessonItemId(userId, 1);

        if (growthRecord != null && growthRecord.getIsRead().equals(1)) {
            return true;
        }

        if (enlightenRecord != null && enlightenRecord.getIsRead().equals(1)) {
            return true;
        }

        return false;
    }

    @Override
    public BuyLessonItemRecord update(BuyLessonItemRecord buyLessonItemRecord) {
        return buyLessonItemRecordDao.save(buyLessonItemRecord);
    }
}
