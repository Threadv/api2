package com.ifenghui.storybookapi.app.wallet.service.impl;

import com.ifenghui.storybookapi.app.social.response.StarRuleIntro;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.wallet.dao.UserStarRecordDao;
import com.ifenghui.storybookapi.app.wallet.dao.WalletDao;
import com.ifenghui.storybookapi.app.wallet.entity.UserStarRecord;
import com.ifenghui.storybookapi.app.transaction.service.order.PayStoryOrderService;
import com.ifenghui.storybookapi.app.transaction.service.order.PaySubscriptionOrderService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.style.AddStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class UserStarRecordServiceImpl implements UserStarRecordService {

    @Autowired
    WalletDao walletDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserStarRecordDao userStarRecordDao;

    @Autowired
    PaySubscriptionOrderService paySubscriptionOrderService;

    @Autowired
    PayStoryOrderService payStoryOrderService;

    @Autowired
    UserService userService;

    @Transactional
//    @Override
//    public void addUserStarRecord(Long userId, Integer amount, AddStyle type, StarRechargeStyle buyType, String intro) {
//        Wallet wallet = walletDao.getWalletByUserId(userId);
//        if(wallet == null){
//           return;
//        }
//        Integer starCount = wallet.getStarCount();
//
//        if (type.equals(StarShopConfig.STAR_INCREASE)) {
//            starCount = wallet.getStarCount() + amount;
//        }
//
//        if (type.equals(StarShopConfig.STAR_DECREASE)) {
//            if(starCount < amount){
//                throw new ApiLackBalanceException("您的星星值不足！");
//            }
//            starCount = wallet.getStarCount() - amount;
//        }
//        wallet.setStarCount(starCount);
//        walletDao.save(wallet);
//
//        UserStarRecord userStarRecord =  new UserStarRecord();
//        userStarRecord.setUserId(userId.intValue());
//        userStarRecord.setAmount(amount);
//        userStarRecord.setAddStyle(type);
//        userStarRecord.setStarRechargeStyle(buyType);
//        userStarRecord.setIntro(intro);
//        userStarRecord.setBalance(starCount);
//        userStarRecord.setCreateTime(new Date());
//        userStarRecordDao.save(userStarRecord);
//        return;
//    }

    @Override
    public Page<UserStarRecord> getUserStarRecordByUserId(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize,new Sort(Sort.Direction.DESC,"createTime","id"));
        return userStarRecordDao.getUserStarRecordsByUserId(userId.intValue(), pageable);
    }



    @Override
    public List<StarRuleIntro> getMoneyTaskList(Long userId) {
        List<StarRuleIntro> moneyTaskList = new ArrayList<StarRuleIntro>();
        Integer yearSubscriptionCount = 0;
        Integer halfYearSubscriptionCount = 0;
        Integer quarterSubscriptionCount = 0;
        Integer oneMonthSubscriptionCount = 0;
        Integer oneWeekSubscriptionCount = 0;
        Integer sixPriceStoryCount = 0;
        Integer threePriceStoryCount = 0;
        Integer onePriceStoryCount = 0;
        if(!userId.equals(0L)){
            yearSubscriptionCount = paySubscriptionOrderService.getPaySubscriptionOrderTimes(12,userId);
            halfYearSubscriptionCount = paySubscriptionOrderService.getPaySubscriptionOrderTimes(6,userId);
            quarterSubscriptionCount = paySubscriptionOrderService.getPaySubscriptionOrderTimes(3,userId);
            oneMonthSubscriptionCount = paySubscriptionOrderService.getPaySubscriptionOrderTimes(1,userId);
            oneWeekSubscriptionCount = paySubscriptionOrderService.getPaySubscriptionOrderTimes(0,userId);
            sixPriceStoryCount = payStoryOrderService.getStoryOrderTimes(600, userId);
            threePriceStoryCount = payStoryOrderService.getStoryOrderTimes(300, userId);
            onePriceStoryCount = payStoryOrderService.getStoryOrderTimes(100, userId);
        }

        moneyTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_YEAR_SUBSCRIPTION_NAME,
                StarConfig.STAR_YEAR_SUBSCRIPTION,
                yearSubscriptionCount
            )
        );

        moneyTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_QUARTER_SUBSCRIPTION_NAME,
                StarConfig.STAR_QUARTER_SUBSCRIPTION,
                quarterSubscriptionCount
            )
        );

        moneyTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_ONE_MONTH_SUBSCRIPTION_NAME,
                StarConfig.STAR_ONE_MONTH_SUBSCRIPTION,
                oneMonthSubscriptionCount
            )
        );

        moneyTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_ONE_WEEK_SUBSCRIPTION_NAME,
                StarConfig.STAR_ONE_WEEK_SUBSCRIPTION,
                oneWeekSubscriptionCount
            )
        );

        moneyTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_SIX_STORY_NAME,
                StarConfig.STAR_SIX_STORY,
                threePriceStoryCount
            )
        );

        moneyTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_THREE_STORY_NAME,
                StarConfig.STAR_THREE_STORY,
                threePriceStoryCount
            )
        );


        moneyTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_ONE_STORY_NAME,
                StarConfig.STAR_ONE_STORY,
                onePriceStoryCount
            )
        );
        return moneyTaskList;
    }

    @Override
    public List<StarRuleIntro> getRookieTaskList(Long userId) {
        List<StarRuleIntro> rookieTaskList = new ArrayList<StarRuleIntro>();

        rookieTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_FINISH_USER_INFO_NAME,
                StarConfig.STAR_FINISH_USER_INFO,
                1
            )
        );

        Integer pasterShareCondition = 0;
        UserStarRecord userStarRecord = this.getSharePaster(userId);
        if(userStarRecord != null){
            pasterShareCondition = 1;
        }
        rookieTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_PASTER_SHARE_NAME,
                StarConfig.STAR_PASTER_SHARE,
                pasterShareCondition
            )
        );
        return rookieTaskList;
    }

    @Override
    public List<StarRuleIntro> getDayTaskList(Long userId) {
        List<StarRuleIntro> dayTaskList = new ArrayList<>();
        Integer starReadStoryCount = 0;
        Integer starReadSmartGameCount = 0;
        Integer userGrowthCount = 0;
        if(!userId.equals(0L)){
            starReadStoryCount =
                this.getUserStarCountByBuyType(userId, 41) +
                this.getUserStarCountByBuyType(userId, 44);

            starReadSmartGameCount =
//                    this.getUserStarCountByBuyType(userId, 45) +
                    this.getUserStarCountByBuyType(userId, 46) +
                    this.getUserStarCountByBuyType(userId, 47) +
                    this.getUserStarCountByBuyType(userId, 48);
            userGrowthCount = this.getUserStarCountByBuyType(userId, 51);
        }

        dayTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_READ_STORY_NAME,
                StarConfig.STAR_READ_STORY,
                starReadStoryCount
            )
        );


        dayTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_READ_SMART_GAME_NAME,
                StarConfig.STAR_READ_SMART_GAME,
                starReadSmartGameCount
            )
        );

        dayTaskList.add(
            new StarRuleIntro(
                StarConfig.STAR_GROWTH_DAIRY_NAME,
                StarConfig.STAR_GROWTH_DAIRY,
                userGrowthCount
            )
        );

        return dayTaskList;
    }

    private Integer getUserStarCountByBuyType(Long userId, Integer buyType){
        Long recordCount = userStarRecordDao.getUserStarCountByBuyType(1, userId.intValue(), buyType);
        if(recordCount == null){
            recordCount = 0L;
        }
        return recordCount.intValue();
    }

    @Override
    public UserStarRecord getSharePaster(Long userId){
        List<UserStarRecord> userStarRecordList = userStarRecordDao.getUserStarRecordsByUserIdAndBuyTypeAndType(userId.intValue(), StarRechargeStyle.SHARE_PASTER.getId(), AddStyle.UP.getId());
        if(userStarRecordList.size() > 0){
            return userStarRecordList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer getTodayUserStarCount(Long userId) throws ParseException {
        Date nowTime = new Date();
        SimpleDateFormat formatOne = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat formatTwo = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String beginTimeString = formatOne.format(nowTime);
        String endTimeString = formatTwo.format(nowTime);
        SimpleDateFormat formatThree = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Date beginTime = formatThree.parse(beginTimeString);
        Date endTime = formatThree.parse(endTimeString);
        Integer starCount = userStarRecordDao.getUserStarNumberSum(1,userId.intValue(), beginTime, endTime);
        if(starCount == null){
            starCount = 0;
        }
        return starCount;
    }
}
