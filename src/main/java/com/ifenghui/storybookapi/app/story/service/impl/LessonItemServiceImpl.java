package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.presale.service.PreSalePayService;
import com.ifenghui.storybookapi.app.story.dao.LessonItemDao;
import com.ifenghui.storybookapi.app.story.entity.Lesson;
import com.ifenghui.storybookapi.app.story.entity.LessonItem;
import com.ifenghui.storybookapi.app.story.entity.LessonItemRelate;
import com.ifenghui.storybookapi.app.story.response.lesson.GetLessonItemListResponse;
import com.ifenghui.storybookapi.app.story.service.LessonItemRelateService;
import com.ifenghui.storybookapi.app.story.service.LessonItemService;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.BuyLessonItemRecord;
import com.ifenghui.storybookapi.app.transaction.response.lesson.GetPayLessonOrderResponse;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.LessonItemReadRecordService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LessonItemServiceImpl implements LessonItemService {

    @Autowired
    LessonItemDao lessonItemDao;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Autowired
    LessonItemRelateService lessonItemRelateService;

    @Autowired
    LessonService lessonService;

    @Autowired
    PreSalePayService preSalePayService;

    @Autowired
    UserService userService;

    @Autowired
    LessonItemReadRecordService lessonItemReadRecordService;

    @Override
    public GetLessonItemListResponse getLessonItemListByLessonId(Integer lessonId, Integer userId) {
        User user = userService.getUser(userId);
        boolean userStatus = false;
        if (user != null && (user.getIsAbilityPlan()==1 || user.getSvip().equals(SvipStyle.LEVEL_THREE.getId()) || user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId()))) {
            userStatus = true;
        }
        /**
         * 没有阅读过的课程章节记录
         */
        BuyLessonItemRecord hasNotReadLessonItem = buyLessonItemRecordService.getHasLessonRecord(userId, lessonId, 0, Sort.Direction.ASC);
        /**
         * 已经阅读过的课程章节记录
         */
        BuyLessonItemRecord hasReadLessonItem = buyLessonItemRecordService.getHasLessonRecord(userId, lessonId, 1, Sort.Direction.DESC);
        GetLessonItemListResponse response = new GetLessonItemListResponse();

        Lesson lesson = lessonService.getLessonById(lessonId);
        response.setName(lesson.getShortName());
        response.setHasNeedRead(0);
        response.setAllLessonCount(lesson.getLessonItemCount());

        if (hasReadLessonItem != null) {
            response.setHasReadNum(hasReadLessonItem.getLessonNum());
        } else {
            response.setHasReadNum(0);
        }
//        LessonItem lessonItem = new LessonItem();
//        lessonItem.setLessonId(lessonId);
//        lessonItem.setStatus(1);
//        List<LessonItem> lessonItemList = lessonItemDao.findAll(Example.of(lessonItem), new Sort(Sort.Direction.ASC, "lessonNum"));


        //查询小于38的
        Sort sort = new Sort(Sort.Direction.ASC, "lessonNum");
        List<LessonItem> lessonItemList = lessonItemDao.findAllByLessonIdAndLessonNum(lessonId,51,sort);

        Boolean checkNeedRead = true;
        for (LessonItem item : lessonItemList) {

            if (item.getIsFree().equals(1)) {
                item.setIsBuyLessonItem(1);
                BuyLessonItemRecord buyLessonItemRecord = buyLessonItemRecordService.getBuyLessonItemRecordByUserIdAndLessonItemId(userId, item.getId());
                Integer isRead = 0;
                if (buyLessonItemRecord != null) {
                    isRead = buyLessonItemRecord.getIsRead();
                } else {
                    List<LessonItemRelate> lessonItemRelateList = lessonItemRelateService.getLessonItemRelateList(item.getId(), userId.longValue());
                    isRead = buyLessonItemRecordService.getIsReadAllStoryByLessonItemRelateList(userId.longValue(), lessonItemRelateList);
                }

                item.setIsRead(isRead);
                item.setNeedRead(0);
                if (isRead.equals(0) && checkNeedRead.equals(true)) {
                    item.setNeedRead(1);
                    response.setHasNeedRead(1);
                    checkNeedRead = false;
                }
            } else {

                if (checkNeedRead.equals(true) && hasNotReadLessonItem != null && item.getId().equals(hasNotReadLessonItem.getLessonItemId())) {
                    item.setNeedRead(1);
                    response.setHasNeedRead(1);
                    checkNeedRead = false;
                } else {
                    item.setNeedRead(0);
                }

                if (!userId.equals(0)) {
                    BuyLessonItemRecord buyLessonItemRecord = buyLessonItemRecordService.getBuyLessonItemRecordByUserIdAndLessonItemId(userId, item.getId());
                    if (buyLessonItemRecord != null) {
                        item.setIsBuyLessonItem(1);
                        item.setIsRead(buyLessonItemRecord.getIsRead());
                    } else if (userStatus) {
                        Integer isRead = lessonItemReadRecordService.getLessonItemReadRecordByUserIdAndLessonItemId(userId, item.getId());
                        item.setIsBuyLessonItem(1);
                        item.setIsRead(isRead);
                    } else {
                        item.setIsBuyLessonItem(0);
                        item.setIsRead(0);
                    }

                } else {
                    item.setIsBuyLessonItem(0);
                    item.setIsRead(0);
                }
            }
        }

        Integer maxHasLessonNumber = buyLessonItemRecordService.getMaxLessonNumByLessonIdAndUserId(lessonId, userId);
        Integer maxLessonNumber = this.getMaxLessonNumInLessonItems(lessonId);
        if (maxHasLessonNumber.equals(maxLessonNumber) || userStatus ) {
            response.setIsAllBuy(1);
        } else {
            response.setIsAllBuy(0);
        }

        Integer finishLessonNum = buyLessonItemRecordService.getHasReadLessonCount(lessonId, userId);
        response.setFinishLessonNum(finishLessonNum);
        response.setLessonItemList(lessonItemList);
        /**
         * 判断是否需要给出送杂志活动提示（后期可以删除掉）
         */
//        response.setAds(preSalePayService.isGetMagazinePreSalePay(userId));

        return response;
    }


    @Override
    public List<LessonItem> getNeedBuyLessonItemList(Integer maxLessonNum, Integer orderNum, Integer lessonId) {
        Pageable pageable = new PageRequest(0, orderNum, new Sort(Sort.Direction.ASC, "lessonNum"));
        Page<LessonItem> lessonItemPage = lessonItemDao.getLessonItemsByMaxLessonNum(lessonId, maxLessonNum, 0, pageable);
        return lessonItemPage.getContent();
    }
    @Override
    public Integer getMaxLessonNumInLessonItems(Integer lessonId) {
        Integer maxLessonNum = lessonItemDao.getMaxLessonNumInLessonItems(lessonId);
        if (maxLessonNum == null) {
            maxLessonNum = 0;
        }
        return maxLessonNum;
    }

    @Override
    public LessonItem getLessonItemById(Integer itemId) {
        return lessonItemDao.findOne(itemId);
    }

    @Override
    public Integer getLessonHasUpdateNumByLessonId(Integer lessonId) {
        Integer hasUpdateNum = lessonItemDao.getHasContentLessonItemCount(lessonId, 1);
        if (hasUpdateNum == null) {
            hasUpdateNum = 0;
        } else {
            hasUpdateNum = hasUpdateNum - 1;
        }
        return hasUpdateNum;
    }
}
