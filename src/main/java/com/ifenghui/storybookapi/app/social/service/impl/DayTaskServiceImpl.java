package com.ifenghui.storybookapi.app.social.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.social.dao.*;
import com.ifenghui.storybookapi.app.social.entity.*;
import com.ifenghui.storybookapi.app.social.response.*;
import com.ifenghui.storybookapi.app.social.service.DayTaskService;
import com.ifenghui.storybookapi.app.story.dao.MedalDao;
import com.ifenghui.storybookapi.app.story.dao.PartsDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Medal;
import com.ifenghui.storybookapi.app.story.entity.Parts;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.dao.PaySerialStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.PayStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.PaySubscriptionOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.PayLessonOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.PayVipOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderMixService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;

import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiIsAddException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import com.ifenghui.storybookapi.style.StarContentStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Component
public class DayTaskServiceImpl implements DayTaskService {


//    @Autowired
//    DayTaskDao dayTaskDao;
//
//    @Autowired
//    DayTaskRecordDao dayTaskRecordDao;

    @Autowired
    MedalDao medalDao;

    @Autowired
    PartsDao partsDao;

    @Autowired
    PartsRecordDao partsRecordDao;

    @Autowired
    MedalRecordDao medalRecordDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PayStoryOrderDao payStoryOrderDao;

    @Autowired
    PaySubscriptionOrderDao paySubscriptionOrderDao;

    @Autowired
    PaySerialStoryOrderDao paySerialStoryOrderDao;

    @Autowired
    StoryDao storyDao;

    @Autowired
    ShareTaskRecordDao shareTaskRecordDao;

    @Autowired
    UserStarRecordService userStarRecordService;
    @Autowired
    StoryService storyService;

    @Autowired
    WalletService walletService;

    @Autowired
    PayLessonOrderDao payLessonOrderDao;

    @Autowired
    OrderMixService orderMixService;

    @Autowired
    PayVipOrderService payVipOrderService;
    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;


//    @Override
//    public Page<DayTask> getDayTaskByType(Long userId, Integer type) {
//
//        Integer pageNo = 0;
//        Integer pageSize = 1;
//        DayTask dayTaskFind = new DayTask();
//        dayTaskFind.setType(type);
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
//        Page<DayTask> dayTaskPage = dayTaskDao.findAll(Example.of(dayTaskFind), pageable);
//        Story story;
//        Date nowDate = new Date(System.currentTimeMillis());//sql date ????????????/currentTimeMillis??????new java.util.sql
//        DayTaskRecord dayTaskRecordFind = new DayTaskRecord();
//        dayTaskRecordFind.setUserId(userId);
//        dayTaskRecordFind.setCreateTime(nowDate);
//        List<DayTaskRecord> dayTaskRecords;
//        User user = userDao.findOne(userId);
//        for (DayTask item : dayTaskPage.getContent()) {
//            //?????????????????????????????????
//            dayTaskRecordFind.setTaskId(item.getId());
//            dayTaskRecords = dayTaskRecordDao.findAll(Example.of(dayTaskRecordFind));
//            if (dayTaskRecords.size() > 0) {
//                item.setStatus(1);
//            } else {
//                item.setStatus(0);
//            }
//            //??????????????????
//            story = storyDao.findOne(item.getStoryId());
//            storyService.setStoryIsBuy(user, story);
////            story.setIsPurchased(1);
////            story.setIsBuy(1);
//            item.setStory(story);
//        }
//        return dayTaskPage;
//    }
//
//    //    @Transactional
//    @Override
//    public void addDayTaskRecord(Long userId, Long taskId) {
//        DayTaskRecord dayTaskRecord = new DayTaskRecord();
//        dayTaskRecord.setTaskId(taskId);
//        dayTaskRecord.setUserId(userId);
//        dayTaskRecord.setCreateTime(new Date(System.currentTimeMillis()));
//        dayTaskRecordDao.save(dayTaskRecord);
//        //???????????????????????????
//        DayTask dayTask = dayTaskDao.findOne(taskId);
//        Integer star = 0;
//        Integer buyType = 0;
//        String intro = "";
//        if (dayTask.getType() == 1) {
//            //??????
//            star = StarConfig.STAR_TASK_READ;//?????????????????????
//            buyType = 11;
//            intro = "???????????????????????????";
//        } else {
//            //?????????
//            star = StarConfig.STAR_TASK_LISTEN;//?????????????????????
//            buyType = 12;
//            intro = "????????????:?????????";
//        }
//        //????????????????????????
////        userStarRecordService.addUserStarRecord(userId,star, AddStyle.UP, StarRechargeStyle.getById(buyType),intro);
//        walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.SIGNIN_SOME_DAY, star, StarContentStyle.LISTEN_STORY.getName());
//
//        return;
//    }


    @Override
    public Page<Medal> getAchievementHandbook(Long userId, Integer pageNo, Integer pageSize) {
        //??????????????????
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "orderBy", "id"));
        Medal medalFind = new Medal();
        medalFind.setStatus(1);
        Page<Medal> medalPage = medalDao.findAll(Example.of(medalFind), pageable);
        List<Parts> partsList;
        for (Medal item : medalPage.getContent()) {
            //storyId????????????
            Parts partsFind = new Parts();
            partsFind.setStoryId(item.getStoryId());
            partsList = partsDao.findAll(Example.of(partsFind));
            //????????????????????????
            for (Parts pItem : partsList) {
                PartsRecord partsRecordFind = new PartsRecord();
                partsRecordFind.setUserId(userId);
                partsRecordFind.setPartId(pItem.getId());
                List<PartsRecord> records = partsRecordDao.findAll(Example.of(partsRecordFind));
                if (records.size() > 0) {
                    pItem.setStatus(1);
                } else {
                    pItem.setStatus(0);
                }
//                pItem.setImgPathGrey(MyEnv.env.getProperty("oss.url")+"medal/"+pItem.getImgPathGrey());
//                pItem.setImgPathLight(MyEnv.env.getProperty("oss.url")+"medal/"+pItem.getImgPathLight());
            }
            //????????????????????????????????????
            MedalRecord medalRecordFind = new MedalRecord();
            medalRecordFind.setUserId(userId);
            medalRecordFind.setMedalId(item.getId());
            List<MedalRecord> medalRecords = medalRecordDao.findAll(Example.of(medalRecordFind));
            if (medalRecords.size() > 0) {
                item.setIsCollect(1);
            } else {
                item.setIsCollect(0);
            }

//            item.setImgPath(MyEnv.env.getProperty("oss.url")+"medal/"+item.getImgPath());
//            item.setFilePath(MyEnv.env.getProperty("oss.url")+"medal/"+item.getFilePath());
            item.setPartsList(partsList);
        }
        return medalPage;
    }

    //???????????????????????????
    @Override
    public Integer getUserCollectMedalCount(Long userId) {
        MedalRecord medalRecordFind = new MedalRecord();
        medalRecordFind.setUserId(userId);
        List<MedalRecord> medalRecords = medalRecordDao.findAll(Example.of(medalRecordFind));
        return medalRecords.size();
    }


    @Override
    public void collectParts(Long userId, Long storyId, String keyName) {
        //storyid + keyName????????????parts
        Parts partsfind = new Parts();
        partsfind.setKeyName(keyName);
        partsfind.setStoryId(storyId);
        List<Parts> partsList = partsDao.findAll(Example.of(partsfind));
        if (partsList.size() == 0) {
            throw new ApiNotFoundException("???????????????");
        }
        //?????????????????????????????????parts
        PartsRecord partsRecordFind = new PartsRecord();
        partsRecordFind.setUserId(userId);
        partsRecordFind.setPartId(partsList.get(0).getId());
        List<PartsRecord> records = partsRecordDao.findAll(Example.of(partsRecordFind));
        if (records.size() > 0) {
            throw new ApiIsAddException("?????????????????????");
        }
        //????????????????????????
        partsRecordFind.setStoryId(storyId);
        partsRecordFind.setCreateTime(new java.util.Date());

        partsRecordDao.save(partsRecordFind);

        //????????????????????????????????????
        PartsRecord partsRecordFind2 = new PartsRecord();
        partsRecordFind2.setUserId(userId);
        partsRecordFind2.setStoryId(storyId);
        Long count = partsRecordDao.count(Example.of(partsRecordFind2));
        if (count == 5L) {
            //??????5??????????????????????????????
            Medal medalFind = new Medal();
            medalFind.setStoryId(storyId);
            List<Medal> medals = medalDao.findAll(Example.of(medalFind));
            if (medals.size() > 0) {
                //????????????????????????
                MedalRecord medalRecordFind = new MedalRecord();
                medalRecordFind.setUserId(userId);
                medalRecordFind.setMedalId(medals.get(0).getId());
                List<MedalRecord> medalRecords = medalRecordDao.findAll(Example.of(medalRecordFind));
                if (medalRecords.size() == 0) {
                    MedalRecord medalRecord = new MedalRecord();
                    medalRecord.setMedalId(medals.get(0).getId());
                    medalRecord.setUserId(userId);
                    medalRecord.setStoryId(storyId);
                    medalRecord.setCreateTime(new java.util.Date());
                    medalRecordDao.save(medalRecord);
                }
            }

        }
        return;
    }

    @Override
    public Medal getMedalByStoryId(Long storyId, Long userId) {
        Medal medal = new Medal();
        medal = medalDao.getOneByStoryId(storyId);
        if (medal == null) {
            return null;
        }

//        medal.setImgPath(MyEnv.env.getProperty("oss.url")+"medal/"+medal.getImgPath());
//        medal.setFilePath(MyEnv.env.getProperty("oss.url")+"medal/"+medal.getFilePath());
        MedalRecord medalRecord = new MedalRecord();
        medalRecord = medalRecordDao.getOneByMedalIdAndUserId(medal.getId(), userId);


        if (medalRecord == null) {
            medal.setIsCollect(0);
        } else {
            medal.setIsCollect(1);
        }

        Parts parts = new Parts();
        parts.setStoryId(storyId);
        List<Parts> partsList = partsDao.findAll(Example.of(parts));
        for (Parts item : partsList) {
            PartsRecord partsRecord = new PartsRecord();
            partsRecord = partsRecordDao.getOneByPartIdAndUserId(item.getId(), userId);
            if (partsRecord != null) {
                item.setStatus(1);
            } else {
                item.setStatus(0);
            }
//            item.setImgPathGrey(MyEnv.env.getProperty("oss.url")+"medal/"+item.getImgPathGrey());
//            item.setImgPathLight(MyEnv.env.getProperty("oss.url")+"medal/"+item.getImgPathLight());

        }

        medal.setPartsList(partsList);
        return medal;
    }

    @Override
    public void shareInviteTask(Long userId) throws ApiException {
        ShareTaskRecord shareTaskRecord = new ShareTaskRecord();
        shareTaskRecord.setUserId(userId);

        List<ShareTaskRecord> shareTaskRecords = shareTaskRecordDao.findAll(Example.of(shareTaskRecord));
        if (shareTaskRecords.size() > 0) {
            throw new ApiIsAddException("????????????");
        }
        shareTaskRecord.setCreateTime(new java.util.Date());
        shareTaskRecordDao.save(shareTaskRecord);
        //????????????????????????
//        Integer buyType = 15;//????????????
        String intro = "????????????";
//        userStarRecordService.addUserStarRecord(userId,StarConfig.STAR_SHARE_INVITE,AddStyle.UP,StarRechargeStyle.SHARE,intro);
        walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.SHARE_PASTER, StarConfig.STAR_SHARE_INVITE, StarContentStyle.SHARE_INVITE.getName());
        return;
    }

    @Autowired
    private Environment env;

    @Override
    public SynchroPartsRecordResponse synchroPartsRecord(Long userId, String jsonData) throws ApiException {


//        {
//            "scheduleImgSmallList": [
//                {
//                    "width": 100,
//                    "height": 100,
//                    "imgPath":"",
//                },
//                {
//                    "width": 100,
//                    "height": 100,
//                    "imgPath":"",
//                },
//            ]
//        }
        //??????json??????
        //jackson json????????????
        ObjectMapper objectMapper = new ObjectMapper();
        SynchroPartsRecordJsonDataResponse synchroPartsRecordJsonDataResponse = new SynchroPartsRecordJsonDataResponse();

        try {
            SynchroPartsRecordJsonDataResponse resp = objectMapper.readValue(jsonData, SynchroPartsRecordJsonDataResponse.class);
            synchroPartsRecordJsonDataResponse.setParts(resp.getParts());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long storyId;
        Integer sumStar = 0;
        Integer star = 0;
        Integer repeatCollect = 0;//??????????????????
        Medal medal;
        for (FormatPartsRecordResponse item : synchroPartsRecordJsonDataResponse.getParts()) {
            storyId = Long.parseLong(item.getStoryId());
            repeatCollect = 0;
            //????????????????????????
            medal = this.getMedalByStoryId(storyId, userId);
            if (medal != null) {
                if (medal.getIsCollect() == 1) {
                    repeatCollect = 1;
                }
            }
            //??????keyNameStr
            String[] keyNameStrArray = item.getKeyNameStr().split(",");
            if (keyNameStrArray.length > 0) {
                //??????
                for (String keyName : keyNameStrArray) {
                    try {
                        this.collectParts(userId, storyId, keyName);
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
                //?????????medal????????????????????????????????????
                PartsRecord partsRecordFind = new PartsRecord();
                partsRecordFind.setStoryId(storyId);
                partsRecordFind.setUserId(userId);
                List<PartsRecord> partsRecords = partsRecordDao.findAll(Example.of(partsRecordFind));
                if (partsRecords.size() > 0) {

                    String keyNameStr = "";
                    for (PartsRecord pItem : partsRecords) {
                        Parts parts = partsDao.findOne(pItem.getPartId());
                        if (parts != null) {
                            keyNameStr += parts.getKeyName() + ",";
                        }
                    }
                    if (!keyNameStr.equals("")) {
                        keyNameStr = keyNameStr.substring(0, keyNameStr.length() - 1);
                    }

                    item.setKeyNameStr(keyNameStr);
                }

            }
            //???????????????
            if (repeatCollect == 0) {
                //?????????????????????
                medal = this.getMedalByStoryId(storyId, userId);
                if (medal != null) {
                    if (medal.getIsCollect() == 1) {
                        star = 10;
                        sumStar = sumStar + star;
                    }
                }
            }
        }

        SynchroPartsRecordResponse response = new SynchroPartsRecordResponse();

        //??????????????????????????????????????????????????????
        response.setParts(synchroPartsRecordJsonDataResponse.getParts());

        //????????????????????????????????????
        if (sumStar > 0) {
            List<TaskFinish> taskFinishes = new ArrayList<>();

            String intro = "??????????????????\n??????" + sumStar + "????????????";

            TaskFinish taskFinish = new TaskFinish();
            taskFinish.setIntro(intro);
            taskFinishes.add(taskFinish);
            response.setTaskFinishInfo(taskFinishes);

            //??????????????????
            //????????????????????????
//            Integer buyType = 16;//??????????????????
            intro = "????????????";
//            userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_STORY_COLLECT,AddStyle.UP,StarRechargeStyle.PART,intro);
            walletService.addStarToWallet(userId.intValue(), StarRechargeStyle.PART, StarConfig.STAR_STORY_COLLECT, StarContentStyle.COLLECT_TOOL.getName());
        }


        return response;
    }

    @Override
    public GetTaskPromptResponse getTaskPrompt(Long userId, Integer type, String value) {

        GetTaskPromptResponse response = new GetTaskPromptResponse();

        Integer starCount = 0;
        String intro;
        if (type.equals(1)) {
            return this.getTastPromptFromMixOrder(OrderStyle.STORY_ORDER, Integer.parseInt(value));
        } else if (type.equals(2)) {
            return this.getTastPromptFromMixOrder(OrderStyle.SUBSCRIPTION_ORDER, Integer.parseInt(value));
        } else if (type.equals(3)) {
            //??????
            starCount = StarConfig.STAR_REGISTER;
            intro = "????????????\n??????" + starCount + "????????????";
            List<TaskFinish> taskFinishes = new ArrayList<>();
            TaskFinish taskFinish = new TaskFinish();
            taskFinish.setIntro(intro);
            taskFinishes.add(taskFinish);
            response.setTaskFinishInfo(taskFinishes);
            return response;

        } else if (type.equals(4)) {
            return this.getTastPromptFromMixOrder(OrderStyle.SERIAL_ORDER, Integer.parseInt(value));
        } else if (type.equals(5)) {
            return this.getTastPromptFromMixOrder(OrderStyle.LESSON_ORDER, Integer.parseInt(value));
        } else if (type.equals(6)) {
            OrderMix orderMix = orderMixService.getOrderMixById(Integer.parseInt(value));
            return this.getTastPromptFromMixOrder(OrderStyle.getById(orderMix.getOrderType()), orderMix.getOrderId());
        } else {
            return response;
        }
    }

    @Override
    public GetTaskPromptResponse getTastPromptFromMixOrder(OrderStyle orderStyle, Integer orderId) {
        GetTaskPromptResponse response = new GetTaskPromptResponse();
        Integer starCount = 0;
        String intro = "";
        if (orderStyle.equals(OrderStyle.STORY_ORDER)) {
            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId.longValue());
            if (payStoryOrder.getBuyType().equals(OrderPayStyle.CODE.getId()) || payStoryOrder.getBuyType().equals(OrderPayStyle.STORY_COUPON.getId())) {
                return response;
            } else {
                starCount = StarConfig.STAR_USR_BUY * payStoryOrder.getAmount() / 100;
                intro = "????????????\n??????" + starCount + "????????????";
            }
        } else if (orderStyle.equals(OrderStyle.SUBSCRIPTION_ORDER)) {
            //??????
            PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId.longValue());
            if (paySubscriptionOrder == null) {
                throw new ApiNotFoundException("????????????????????????");
            }
            if (paySubscriptionOrder.getType().equals(OrderPayStyle.CODE.getId()) || paySubscriptionOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())) {
                return response;
            } else {
                starCount = StarConfig.STAR_USR_BUY * paySubscriptionOrder.getOriginalPrice() / 100;
                intro = "????????????\n??????" + starCount + "????????????";
            }
        } else if (orderStyle.equals(OrderStyle.SERIAL_ORDER)) {
            //???????????????
            PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId);

            if (paySerialStoryOrder == null) {
                throw new ApiNotFoundException("??????????????????");
            }

            if (paySerialStoryOrder.getType().equals(OrderPayStyle.CODE.getId()) || paySerialStoryOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())) {
                return response;
            } else {
                starCount = StarConfig.STAR_USR_BUY * paySerialStoryOrder.getAmount() / 100;
                intro = "????????????\n??????" + starCount + "????????????";
            }
        } else if (orderStyle.equals(OrderStyle.LESSON_ORDER)) {
            //????????????
            PayLessonOrder payLessonOrder = payLessonOrderDao.findOne(orderId);
            if (payLessonOrder == null) {
                throw new ApiNotFoundException("??????????????????");
            }
            if (payLessonOrder.getType().equals(OrderPayStyle.CODE.getId()) || payLessonOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())) {
                return response;
            } else {
                starCount = StarConfig.STAR_USR_BUY * payLessonOrder.getAmount() / 100;
                intro = "????????????\n??????" + starCount + "????????????";
            }
        } else if (orderStyle.equals(OrderStyle.VIP_ORDER)) {
            //vip??????
            PayVipOrder payVipOrder = payVipOrderService.getPayVipOrderById(orderId);
            if (payVipOrder == null) {
                throw new ApiNotFoundException("??????????????????");
            }
            if (payVipOrder.getType().equals(OrderPayStyle.CODE.getId()) || payVipOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())) {
                return response;
            } else {
                starCount = StarConfig.STAR_USR_BUY * payVipOrder.getAmount() / 100;
                intro = "????????????\n??????" + starCount + "????????????";
            }
        } else if (orderStyle.equals(OrderStyle.ABILITY_PLAN_ORDER)) {
            //you??????
            AbilityPlanOrder abilityPlanOrder = abilityPlanOrderService.getAbilityPlanOrder(orderId);
            if (abilityPlanOrder == null) {
                throw new ApiNotFoundException("??????????????????");
            }
            if (abilityPlanOrder.getType().equals(OrderPayStyle.CODE.getId()) || abilityPlanOrder.getType().equals(OrderPayStyle.STORY_COUPON.getId())) {
                return response;
            } else {
                starCount = StarConfig.STAR_USR_BUY * abilityPlanOrder.getAmount() / 100;
                intro = "????????????\n??????" + starCount + "????????????";
            }
        }
        List<TaskFinish> taskFinishes = new ArrayList<>();
        TaskFinish taskFinish = new TaskFinish();
        taskFinish.setIntro(intro);
        taskFinishes.add(taskFinish);
        response.setTaskFinishInfo(taskFinishes);
        return response;
    }
}
