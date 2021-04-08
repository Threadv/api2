package com.ifenghui.storybookapi.app.transaction.service.impl;

import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.*;
import com.ifenghui.storybookapi.app.transaction.entity.single.ShoppingTrolley;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.transaction.service.CouponService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.config.MyEnv;
import com.ifenghui.storybookapi.exception.ApiCouponPastDueException;
import com.ifenghui.storybookapi.exception.ApiCouponUsedException;
import com.ifenghui.storybookapi.exception.ApiNoPermissionDelException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeService;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CouponStoryExchangeUserServiceImpl implements CouponStoryExchangeUserService {


    @Autowired
    CouponStoryExchangeDao couponStoryExchangeDao;

    @Autowired
    CouponStoryExchangeUserDao couponStoryExchangeUserDao;

    @Autowired
    CouponStoryExchangeService couponStoryExchangeService;

    @Autowired
    StoryService storyService;

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Autowired
    UserService userService;

    @Autowired
    SerialStoryService serialStoryService;

    @Autowired
    CouponGetRecordDao couponGetRecordDao;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponDao couponDao;

    @Override
    public CouponStoryExchangeUser addCouponStoryExchangeUser(Integer couponId, Integer userId) {

        CouponStoryExchange couponStoryExchange = couponStoryExchangeService.getCouponStoryExchangeById(couponId);
        CouponStoryExchangeUser couponStoryExchangeUser = new CouponStoryExchangeUser();
        couponStoryExchangeUser.setCouponId(couponId);
        couponStoryExchangeUser.setStatus(0);
        couponStoryExchangeUser.setUserId(userId);
        couponStoryExchangeUser.setCreateTime(new Date());
        couponStoryExchangeUser.setIsExpire(0);
        couponStoryExchangeUser.setEndTime(this.getEndTimeByCouponId(couponId));
        couponStoryExchangeUserDao.save(couponStoryExchangeUser);
        return couponStoryExchangeUser;

    }

    @Override
    public Date getEndTimeByCouponId(Integer couponId) {
        Date endTime;
        Calendar calendar = Calendar.getInstance();
        CouponStoryExchange couponStoryExchange = couponStoryExchangeService.getCouponStoryExchangeById(couponId);
        calendar.add(Calendar.MONTH, couponStoryExchange.getExceedTime());//几个月
        endTime = calendar.getTime();
        return endTime;
    }

    /**
     * 获取用户故事兑换券
     * @param userId
     * @param isExpire 0 未过期 1 已过期
     * @param pageNo
     * @param pageSize
     * @return
     */

    @Override
    public Page<CouponStoryExchangeUser> getCouponStoryExchangeUserPageByUserIdAndIsExpire(Integer userId, Integer isExpire, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));

        CouponStoryExchangeUser couponStoryExchangeUser = new CouponStoryExchangeUser();
        couponStoryExchangeUser.setUserId(userId);
        couponStoryExchangeUser.setIsExpire(isExpire);
        Page<CouponStoryExchangeUser> couponStoryExchangeUserPage = couponStoryExchangeUserDao.findAll(Example.of(couponStoryExchangeUser),pageable);

        return couponStoryExchangeUserPage;
    }

    /**
     * 获取用户未过期故事兑换券
     * @param userId
     * @param status 0 未使用 1 已使用
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<CouponStoryExchangeUser> getCouponStoryExchangeUserPageByUserIdAndStatus(Integer userId, Integer status, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));

        CouponStoryExchangeUser couponStoryExchangeUser = new CouponStoryExchangeUser();
        couponStoryExchangeUser.setUserId(userId);
        couponStoryExchangeUser.setStatus(status);
        couponStoryExchangeUser.setIsExpire(0);
        Page<CouponStoryExchangeUser> couponStoryExchangeUserPage = couponStoryExchangeUserDao.findAll(Example.of(couponStoryExchangeUser),pageable);

        return couponStoryExchangeUserPage;
    }

    @Override
    public void dayTaskCheckExpireStoryCoupon() {
        Date nowTime = new Date();
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserDao.getUserUnValidStoryCouponList(nowTime);
        for(CouponStoryExchangeUser item : couponStoryExchangeUserList){
            item.setIsExpire(1);
            couponStoryExchangeUserDao.save(item);
        }
    }

    @Transactional
    @Override
    public void useCouponStoryExchangeUser(Integer couponStoryExchangeUserId, Integer storyId, Long userId) {

        CouponStoryExchangeUser couponStoryExchangeUser = couponStoryExchangeUserDao.findOne(couponStoryExchangeUserId);
        if(couponStoryExchangeUser == null || couponStoryExchangeUser.getCouponStoryExchange() == null){
            throw new ApiNotFoundException("未找到故事兑换券数据！");
        }

        if(couponStoryExchangeUser.getStatus().equals(1)){
            throw new ApiNoPermissionDelException("此兑换券已使用！");
        }

        if(couponStoryExchangeUser.getIsExpire().equals(1)){
            throw new ApiNoPermissionDelException("此兑换券已过期！");
        }

        Story story = storyService.getStory((long)storyId);
        if(story ==  null){
            throw new ApiNotFoundException("没有这个故事！");
        }

        if(story.getIsFree().equals(1)){
            throw new ApiNotFoundException("此故事为免费故事！");
        }

        Long buyStoryRecordListCount = buyStoryRecordDao.countByUserIdAndStoryId(userId,story.getId());
        if (buyStoryRecordListCount>0){
            throw new ApiNotFoundException("您已购买此故事！");
        }

        if(couponStoryExchangeUser.getUserId().equals(userId.intValue())){

            BuyStoryRecord buyStoryRecord = new BuyStoryRecord();
            buyStoryRecord.setUserId(userId);
            buyStoryRecord.setStoryId((long)storyId);
            User user = userService.getUser(userId);
            buyStoryRecord.setIsTest(user.getIsTest());
            buyStoryRecord.setCreateTime(new Date());
            buyStoryRecord.setType(3);
            buyStoryRecordDao.save(buyStoryRecord);
            couponStoryExchangeUser.setStatus(1);
            couponStoryExchangeUserDao.save(couponStoryExchangeUser);

            //TO-DO：从购物车中清除数据
            ShoppingTrolley shoppingTrolley=new ShoppingTrolley();
            shoppingTrolley.setUserId(userId);
            shoppingTrolley.setStoryId((long)storyId);
            List<ShoppingTrolley> shoppingTrolleys= shoppingCartDao.findAll(Example.of(shoppingTrolley));
            for(ShoppingTrolley shoppingTrolley1:shoppingTrolleys){
                shoppingCartDao.delete(shoppingTrolley1);
            }
        } else {
            throw new ApiNoPermissionDelException("您不是此故事兑换券拥有者！");
        }
    }

    @Transactional
    @Override
    public List<CouponStoryExchangeUser> checkStoryCouponAndGetCouponNum(Integer userId, Integer couponNum) {
        Pageable pageable = new PageRequest(0, couponNum, new Sort(Sort.Direction.ASC,"endTime"));
        CouponStoryExchangeUser couponStoryExchangeUser = new CouponStoryExchangeUser();
        couponStoryExchangeUser.setUserId(userId);
        couponStoryExchangeUser.setIsExpire(0);
        couponStoryExchangeUser.setStatus(0);
        Page<CouponStoryExchangeUser> couponStoryExchangeUserPage = couponStoryExchangeUserDao.findAll(Example.of(couponStoryExchangeUser),pageable);
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserPage.getContent();
        if(couponStoryExchangeUserList.size() != couponNum){
            throw new ApiCouponUsedException("可用故事兑换券不足！");
        }
        return couponStoryExchangeUserList;
    }

    @Override
    public Integer getUserHasStoryCouponNumber(Integer userId) {
        Integer userHasStoryCouponNumber = couponStoryExchangeUserDao.getUserHasStoryCouponNumber(userId);
        if(userHasStoryCouponNumber == null){
            userHasStoryCouponNumber = 0;
        }
        return userHasStoryCouponNumber;
    }

    @Override
    public Integer getUserNeedUseStoryCouponNumber(Integer userId, OrderStyle orderStyle, Integer targetValue){
        Integer needCouponNumber = 0;
        if(orderStyle.equals(OrderStyle.LESSON_ORDER)){
            needCouponNumber = targetValue * 3;
        }
        if(orderStyle.equals(OrderStyle.STORY_ORDER)){
            needCouponNumber = targetValue;
        }
        if(orderStyle.equals(OrderStyle.SERIAL_ORDER)){
            SerialStory serialStory = serialStoryService.getSerialStory(targetValue.longValue());
            needCouponNumber = serialStory.getCouponCount();
        }
        return needCouponNumber;
    }

    @Override
    public void sendRegisterUserStoryCoupon(Integer userId, String phone){
        CouponGetRecord couponGetRecord = couponGetRecordDao.getByPhone(phone);
        if(couponGetRecord != null && couponGetRecord.getStatus().equals(0)) {
            Coupon coupon = couponDao.findOne(3L);
            Date nowTime = new Date();
            Date endTime = couponService.getCouponEndTime(nowTime,coupon);
            couponService.addCouponUser(3, userId.longValue(),0,endTime,nowTime,phone,0,"注册赠送");
            this.addCouponStoryExchangeUser(1,couponGetRecord.getUserId().intValue());
            this.addCouponStoryExchangeUser(1,couponGetRecord.getUserId().intValue());
            this.addCouponStoryExchangeUser(1,userId);
            this.addCouponStoryExchangeUser(1,userId);
            couponGetRecord.setStatus(1);
            couponGetRecordDao.save(couponGetRecord);
        }
    }

    @Override
    public void sendActivityStoryCoupon(Integer userId, Integer sendCouponNum) {
        for(int i = 1; i<= sendCouponNum; i++){
            this.addCouponStoryExchangeUser(3,userId);
        }
    }
}
