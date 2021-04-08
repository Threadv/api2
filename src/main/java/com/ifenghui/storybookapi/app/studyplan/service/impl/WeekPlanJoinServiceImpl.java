package com.ifenghui.storybookapi.app.studyplan.service.impl;

import com.ifenghui.storybookapi.app.social.controller.ViewRecordController;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanJoinDao;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanUserRecordDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanIntro;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanUserRecord;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanIntroService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanJoinService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanUserRecordService;
import com.ifenghui.storybookapi.app.transaction.dao.AbilityPlanOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.user.dao.UserExtendDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.AbilityPlanStyle;
import com.ifenghui.storybookapi.style.VipGoodsStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class WeekPlanJoinServiceImpl implements WeekPlanJoinService {

    org.apache.log4j.Logger logger= org.apache.log4j.Logger.getLogger(WeekPlanJoinServiceImpl.class);

    @Autowired
    UserExtendDao userExtendDao;
    @Autowired
    WeekPlanJoinDao weekPlanJoinDao;

    @Autowired
    UserService userService;

    @Autowired
    WeekPlanUserRecordDao weekPlanUserRecordDao;
    @Autowired
    WeekPlanUserRecordService weekPlanUserRecordService;

    @Autowired
    WeekPlanIntroService weekPlanIntroService;

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;
    @Autowired
    AbilityPlanOrderDao abilityPlanOrderDao;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    WeekPlanJoinService weekPlanJoinService;


    @Override
    public void addOneWeekPlanByUserId(Integer userId, WeekPlanStyle weekPlanStyle) {

        WeekPlanJoin planJoin = this.getWeekPlanJoinByUserIdAndType(userId, weekPlanStyle);

        if (planJoin == null) {
            WeekPlanJoin weekPlanJoin = weekPlanJoinService.createWeekPlanJoin(userId, weekPlanStyle);
            weekPlanJoin.setBuyNum(1);
            weekPlanJoinDao.save(weekPlanJoin);
        } else if ( planJoin.getBuyNum() == 0) {
            planJoin.setBuyNum(1);
            weekPlanJoinDao.save(planJoin);
        }
        WeekPlanIntro weekPlanIntro = weekPlanIntroService.getWeekPlanIntroByWeekNum(1, weekPlanStyle);
        if (weekPlanIntro == null) {
            throw new ApiNotFoundException("没有这个内容");
        }
        weekPlanUserRecordService.createWeekPlanUserRecord(weekPlanIntro.getId(), userId);
    }

    @Override
    public void updateWeekPlanJoinBuyNum(Integer orderId,Integer userId, WeekPlanStyle weekPlanStyle, Integer priceId) {

        WeekPlanJoin weekPlanJoin = this.getWeekPlanJoinByUserIdAndType(userId, weekPlanStyle);
        if(weekPlanJoin.getBuyNum() >= 52){
            return ;
        }
        int buyNum = 0;
        VipGoodsStyle vipGoodsStyle=VipGoodsStyle.getById(priceId);
        buyNum=vipGoodsStyle.getAbilityPlanCodeStyle().getBuyNum();
//        if (priceId == VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR.getId() ||
//                priceId == VipGoodsStyle.ABILITY_PLAN_YEAR_TWO_FOUR_CODE.getId() ||
//                priceId == VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX.getId() ||
//                priceId == VipGoodsStyle.ABILITY_PLAN_YEAR_FOUR_SIX_CODE.getId()
//                ) {
//            buyNum = 52;
//        }
//        if (priceId == VipGoodsStyle.ABILITY_PLAN_OTHER_TWO_FOUR_CODE.getId() || priceId == VipGoodsStyle.ABILITY_PLAN_OTHER_FOUR_SIX_CODE.getId()) {
//            buyNum = 48;
//        }
//        if (priceId == VipGoodsStyle.ABILITY_PLAN_OTHER_TWO_FOUR_CODE47.getId() || priceId == VipGoodsStyle.ABILITY_PLAN_OTHER_FOUR_SIX_CODE47.getId()) {
//            buyNum = 47;
//        }
//        if (priceId == VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_TWO_FOUR_CODE.getId() || priceId == VipGoodsStyle.ABILITY_PLAN_HALF_YEAR_FOUR_SIX_CODE.getId()) {
//            buyNum = 26;
//        }
//        if (priceId == VipGoodsStyle.ABILITY_PLAN_SEASON_TWO_FOUR_CODE.getId() || priceId == VipGoodsStyle.ABILITY_PLAN_SEASON_FOUR_SIX_CODE.getId()) {
//            buyNum = 12;
//        }
//        if (priceId == VipGoodsStyle.ABILITY_PLAN_MONTH_TWO_FOUR_CODE.getId() || priceId == VipGoodsStyle.ABILITY_PLAN_MONTH_FOUR_SIX_CODE.getId()) {
//            buyNum = 4;
//        }
        weekPlanJoin.setBuyNum(weekPlanJoin.getBuyNum() + buyNum);
        //订单添加备注更改buyNum信息
        AbilityPlanOrder order = abilityPlanOrderDao.findOne(orderId);
        if(order!=null){
            String intro = order.getRemark()+"用户"+userId+"planJoin"+weekPlanStyle+"修改buyNum为"+buyNum;
            order.setRemark(intro);
            abilityPlanOrderDao.save(order);
        }
        weekPlanJoinDao.save(weekPlanJoin);
    }

    @Override
    public WeekPlanJoin addWeekPlanJoin(Integer userId, WeekPlanStyle weekPlanStyle) {
        WeekPlanJoin weekPlanJoin = new WeekPlanJoin();
        weekPlanJoin.setUserId(userId);
        weekPlanJoin.setWeekPlanStyle(weekPlanStyle);
        weekPlanJoin.setBeginTime(new Date());
        weekPlanJoin.setCreateTime(new Date());
        weekPlanJoin.setBuyNum(0);
        return weekPlanJoinDao.save(weekPlanJoin);
    }

    @Override
    public WeekPlanJoin createWeekPlanJoin(Integer userId, WeekPlanStyle weekPlanStyle) {
        WeekPlanJoin weekPlanJoin = this.getWeekPlanJoinByUserIdAndType(userId, weekPlanStyle);
        if (weekPlanJoin != null) {
            return weekPlanJoin;
        }
        return this.addWeekPlanJoin(userId, weekPlanStyle);
    }

    @Override
    public WeekPlanJoin getWeekPlanJoinByUserIdAndType(Integer userId, WeekPlanStyle weekPlanStyle) {
        if (this.countWeekPlanJoinByUserIdAndType(userId, weekPlanStyle) == 0) {
            return null;
        }
        return weekPlanJoinDao.getDistinctByPlanTypeAndUserId(userId, weekPlanStyle.getId());
    }

    @Override
    public Long countWeekPlanJoinByUserIdAndType(Integer userId, WeekPlanStyle weekPlanStyle) {
        Long count = weekPlanJoinDao.countDistinctByPlanTypeAndUserId(userId, weekPlanStyle.getId());
        if (count == null) {
            count = 0L;
        }
        return count;
    }

    @Override
    public Page<WeekPlanJoin> getAllPage(int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        return weekPlanJoinDao.getAllByPage(pageable);
    }

    @Override
    public Page<WeekPlanJoin> findAllBuyNumBiggerThanOne(int pageNo, int pageSize) {
        return weekPlanJoinDao.findAllByBuyNumGreaterThan(1,new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.ASC, "id")));
    }

    @Override
    public Page<WeekPlanJoin> getPageByDate(int pageNo, int pageSize, Date date) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        return weekPlanJoinDao.getAllByDate(date, pageable);
    }

    @Override
    public Page<WeekPlanJoin> getWeekPlanJoinByBuyNum(Integer pageNo, Integer pageSize) {

        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.ASC, "id"));
        return weekPlanJoinDao.getAllByBuyNum(pageable);

    }

    @Override
    public void addBuyNum(List<WeekPlanJoin> weekPlanJoinList) {

        for (WeekPlanJoin w : weekPlanJoinList) {
            logger.info("updateWeekPlanJoin--for (WeekP----weekplanjon userId==" + w.getUserId());
            AbilityPlanOrder order = abilityPlanOrderService.getAbilityPlanOrderByPlanTypeAndUserIdAndStatus(w.getWeekPlanStyle(), w.getUserId(), 1);
            if (order != null) {
                int priceId = order.getPriceId();
                if (priceId >= 40 && priceId <= 43) {
                    logger.info("updateWeekPlanJoin--for priceId >= 40 && priceId <= 43---weekplanjon userId==" + w.getUserId());
                    //52
                    w.setBuyNum(52);
                } else if (priceId == 44 || priceId == 45) {
                    logger.info("updateWeekPlanJoin--for priceId == 44 || priceId == 45n userId==" + w.getUserId());
                    //26
                    w.setBuyNum(26);
                } else if (priceId == 46 || priceId == 47) {
                    logger.info("updateWeekPlanJoin--for priceId == 46 || priceId == 47 userId==" + w.getUserId());
                    //13
                    w.setBuyNum(13);
                } else if (priceId == 48 || priceId == 49) {
                    logger.info("updateWeekPlanJoin--for priceId == 48 || priceId == 49 userId==" + w.getUserId());
                    //5
                    w.setBuyNum(5);
                }
                weekPlanJoinDao.save(w);
            } else {
                logger.info("updateWeekPlanJoin--else--buynum=1---weekplanjon userId==" + w.getUserId());
                w.setBuyNum(1);
                weekPlanJoinDao.save(w);
            }
        }
    }

    @Override
    public void addBuyNumVip(List<User> userList) {
        for (User user:userList) {
            UserExtend userExtend = userExtendService.findUserExtendByUserId(user.getId());
            if(userExtend.getWeekPlanType() ==1){
                WeekPlanJoin planJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(user.getId().intValue(), WeekPlanStyle.TWO_FOUR);
                logger.info("VIPupdateWeekPlanJoin--for (WeekP-----planJoin="+planJoin+"weekplanjon11111 userId==" + userExtend.getUserId());
                planJoin.setBuyNum(52);
                weekPlanJoinDao.save(planJoin);
                //2设置为1周
                WeekPlanJoin planJoin2 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(user.getId().intValue(), WeekPlanStyle.FOUR_SIX);
                if(planJoin2 !=null){
                    planJoin2.setBuyNum(1);
                    weekPlanJoinDao.save(planJoin2);
                }

                Pageable pageable = new PageRequest(0, 60, new Sort(Sort.Direction.ASC, "id"));
                List<WeekPlanUserRecord> recordList = weekPlanUserRecordDao.getListFourSixByUserId(user.getId().intValue(), pageable);
                for (WeekPlanUserRecord r:recordList) {
                    if(r.getWeekPlanId() != 53){
                        logger.info("deleteVIPupdateWeekPlanJoin--for (WeekP----planJoin="+planJoin+"weekplanjon11111 userId==" + r.getUserId());
                        weekPlanUserRecordService.deleteRecordsByUserIdAndWeekPlanId(user.getId().intValue(),r.getWeekPlanId());
                    }
                }
            }
            if(userExtend.getWeekPlanType() ==2){
                WeekPlanJoin planJoin = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(user.getId().intValue(), WeekPlanStyle.FOUR_SIX);
                logger.info("VIPupdateWeekPlanJoin--for (WeekP----planJoin="+planJoin+"weekplanjon22222 userId==" + userExtend.getUserId());
                planJoin.setBuyNum(52);
                weekPlanJoinDao.save(planJoin);
                //1设置为1周
                WeekPlanJoin planJoin2 = weekPlanJoinService.getWeekPlanJoinByUserIdAndType(user.getId().intValue(), WeekPlanStyle.TWO_FOUR);
                if(planJoin2 !=null){
                    planJoin2.setBuyNum(1);
                    weekPlanJoinDao.save(planJoin2);
                }

                Pageable pageable = new PageRequest(0, 60, new Sort(Sort.Direction.ASC, "id"));
                List<WeekPlanUserRecord> recordList = weekPlanUserRecordDao.getListTwoFourByUserId(user.getId().intValue(), pageable);
                for (WeekPlanUserRecord r:recordList) {
                    if(r.getWeekPlanId() != 1){
                        logger.info("deleteVIPupdateWeekPlanJoin--for (WeekP----planJoin="+planJoin+"weekplanjon22222 userId==" + r.getUserId());
                        weekPlanUserRecordService.deleteRecordsByUserIdAndWeekPlanId(user.getId().intValue(),r.getWeekPlanId());
                    }
                }
            }


        }

    }

    @Override
    public void updateOrderPlanStyle(List<WeekPlanJoin> weekPlanJoinList) {

        for (WeekPlanJoin w : weekPlanJoinList) {

            logger.info("updateWeekPlanJoin--addNum--update--order");
            UserExtend userExtend = userExtendService.findUserExtendByUserId(w.getUserId().longValue());
            List<AbilityPlanOrder> orderList = abilityPlanOrderDao.getAbilityPlanOrdersByUserIdAndStatus(userExtend.getUserId(), 1);
            for (AbilityPlanOrder o : orderList) {
                //修改订单的计划类型根据userextend修改
                if (userExtend.getWeekPlanType() == o.getPlaneType().intValue()) {
                    logger.info("updateWeekPlanJoin--continue--addNum--update--order orderId"+o.getId());
                    continue;
                } else {
                    logger.info("updateWeekPlanJoin--设置priceId--update--order orderId"+o.getId());
                    if (o.getPlaneType() == 1) {
                        o.setPriceId(o.getPriceId() + 1);
                    } else if (o.getPlaneType() == 2) {
                        o.setPriceId(o.getPriceId() - 1);
                    }
                    o.setAbilityPlanStyle(AbilityPlanStyle.getById(userExtend.getWeekPlanType()));
                    abilityPlanOrderDao.save(o);
                }
            }
            logger.info("updateWeekPlanJoin--设置userExtend--update--order userId"+userExtend.getUserId());
            userExtend.setPlanChangeCount(1);
            userExtendDao.save(userExtend);
        }
    }


    @Override
    public void updateUserExtendPlanChangeCount(List<User> userList) {

        for (User user:userList) {
            logger.info("updateUserExtendPlanChangeCount--修改planchangecou userId ="+user.getId());
            UserExtend userExtend = userExtendService.findUserExtendByUserId(user.getId());
            userExtend.setPlanChangeCount(0);
            userExtendDao.save(userExtend);
            //补第一周数据
            logger.info("updateUserExtendPlanChangeCount--补第一周数据 userId ="+user.getId());
            weekPlanJoinService.addOneWeekPlanByUserId(user.getId().intValue(),WeekPlanStyle.TWO_FOUR);
            weekPlanJoinService.addOneWeekPlanByUserId(user.getId().intValue(),WeekPlanStyle.FOUR_SIX);

        }
    }

    @Override
    public Page<WeekPlanJoin> findAll(WeekPlanJoin weekPlanJoin, PageRequest pageRequest) {
        return weekPlanJoinDao.findAll(Example.of(weekPlanJoin),pageRequest);
    }

    @Override
    public WeekPlanJoin update(WeekPlanJoin weekPlanJoin) {
        return weekPlanJoinDao.save(weekPlanJoin);
    }
}
