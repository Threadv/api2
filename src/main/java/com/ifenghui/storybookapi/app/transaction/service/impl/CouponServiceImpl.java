package com.ifenghui.storybookapi.app.transaction.service.impl;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.*;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.*;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionPrice;
import com.ifenghui.storybookapi.app.transaction.service.CouponStoryExchangeUserService;
import com.ifenghui.storybookapi.app.user.dao.UserAccountDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.service.UserAccountService;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.entity.UserStarRecord;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.app.transaction.service.CouponService;
import com.ifenghui.storybookapi.app.wallet.service.UserStarRecordService;
import com.ifenghui.storybookapi.style.StarContentStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by wml on 2017/05/18
 */
@Transactional
@Component
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDao couponDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    SerialStoryDao serialStoryDao;
    @Autowired
    CouponDeferredDao couponDeferredDao;
    @Autowired
    CouponUserDao couponUserDao;
    @Autowired
    VCouponMixDao vCouponMixDao;
    @Autowired
    CouponGetRecordDao couponGetRecordDao;
    @Autowired
    UserDao userDao;
    @Autowired
    PaySubscriptionPriceDao paySubscriptionPriceDao;

    @Autowired
    CouponStoryExchangeDao couponStoryExchangeDao;

    @Autowired
    UserStarRecordService userStarRecordService;

    @Autowired
    WalletService walletService;

    @Autowired
    CouponStoryExchangeUserDao couponStoryExchangeUserDao;

    @Autowired
    CouponStoryExchangeUserService couponStoryExchangeUserService;

    @Autowired
    UserAccountDao userAccountDao;

    @Override
    public Page<CouponUser> getUserCoupons(Long userId, Integer type, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Date nowTime = new  Date();
        Page<CouponUser> couponUserPage;
        if(type==1){
            //未过期
            couponUserPage = couponUserDao.getUserCoupons(userId,nowTime,pageable);
        }else{
            //过期
            couponUserPage = couponUserDao.getExpiredUserCoupons(userId,nowTime,pageable);
        }
        Coupon coupon;
        for (CouponUser item:couponUserPage.getContent()){
            coupon = couponDao.findOne(item.getCouponId());
            item.setName(coupon.getName());
            item.setContent(coupon.getContent());
            item.setAmount(coupon.getAmount());
            item.setIsUsable(1);
            //把未读的标记成已读
            item.setIsView(1);
        }
        return couponUserPage;
    }

    @Override
    public Page<VCouponMix> getExpiredMixCoupons(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"createTime"));
        Date nowTime = new  Date();
        Page<VCouponMix> vCouponMixPage;

        //过期优惠券+赠阅券
        vCouponMixPage = vCouponMixDao.getExpiredMixCoupons(userId,nowTime,pageable);


        Coupon coupon;
        CouponDeferred couponDeferred;
        CouponUser couponUser;
        for (VCouponMix item:vCouponMixPage.getContent()){
            if(item.getType() == 1){
                //coupon_user表
                coupon = couponDao.findOne(item.getCouponId());
                item.setName(coupon.getName());
                item.setContent(coupon.getContent());
                item.setAmount(coupon.getAmount());
                item.setValidTime(0);
                //把未读的标记成已读
                couponUser = couponUserDao.findOne(item.getId()-800000000);
                couponUser.setIsView(1);
            } else if(item.getType().equals(2)) {
                //coupon_deferred_user表
                couponDeferred = couponDeferredDao.findOne(item.getCouponId());
                item.setName(couponDeferred.getName());
                item.setContent(couponDeferred.getContent());
                item.setValidTime(couponDeferred.getValidTime());
                item.setAmount(0);
            } else if(item.getType().equals(3)){
                CouponStoryExchange storyCoupon = couponStoryExchangeDao.findOne(item.getCouponId().intValue());
                item.setName(storyCoupon.getName());
                item.setContent(storyCoupon.getContent());
            }


        }
        return vCouponMixPage;
    }

    @Override
    public Page<VCouponMix> getExpiredMixCoupons(VCouponMix vCouponMix, Integer pageNo, Integer pageSize) {
        return vCouponMixDao.findAll(Example.of(vCouponMix)
                ,new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id")));
    }

    @Override
    public Page<CouponUser> getUserUnReacCoupons(Long userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Date nowTime = new  Date();
        Page<CouponUser> couponUserPage;

        //未读
        couponUserPage = couponUserDao.getUserUnReadCoupons(userId,pageable);

        Coupon coupon;
        for (CouponUser item:couponUserPage.getContent()){
            item.setIsView(1);
            couponUserDao.save(item);//改为已读
            //获取优惠券数据
            coupon = couponDao.findOne(item.getCouponId());
            item.setName(coupon.getName());
            item.setContent(coupon.getContent());
            item.setAmount(coupon.getAmount());
        }
        return couponUserPage;
    }

    @Override
    public Page<CouponUser> getUserValidityCoupons(Long userId, Integer pageNo, Integer pageSize,Long priceId,Integer type) {

        Integer price = 0;
        //获取价格
        if(type == 1){
            //获取订阅价格数据
            PaySubscriptionPrice paySubscriptionPrice = paySubscriptionPriceDao.findOne(priceId);
            price = paySubscriptionPrice.getPrice();
        } else {
            //获取故事集价格
            SerialStory serialStory = serialStoryDao.findOne(priceId.longValue());
            price = serialStory.getPrice();
        }

        return this.getUserValidCouponsByPrice(userId, pageNo, pageSize, price);
    }

    @Override
    public Page<CouponUser> getUserValidCouponsByPrice(Long userId, Integer pageNo, Integer pageSize, Integer price) {

        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        Date nowTime = new  Date();
        //未过期,且未使用
        Page<CouponUser> couponUserPage = couponUserDao.getUserValidityCoupons(userId,nowTime,pageable);
        Coupon coupon;
        for (CouponUser item:couponUserPage.getContent()){
            coupon = couponDao.findOne(item.getCouponId());
            item.setName(coupon.getName());
            item.setContent(coupon.getContent());
            item.setAmount(coupon.getAmount());
            item.setMaxAmount(coupon.getMaxCount());
            if(price!=null && (coupon.getMaxCount() <= price)){
                item.setIsUsable(1);
            }else{
                item.setIsUsable(0);
            }
        }
        return couponUserPage;
    }

    @Override
    public Integer getCashCouponNumber(Long userId, Integer price){
        List<CouponUser> couponUserList = couponUserDao.getUserValidityCoupons(userId, new Date());
        Iterator<CouponUser> iterator = couponUserList.iterator();
        while(iterator.hasNext()){
            CouponUser item = iterator.next();
            Coupon coupon = couponDao.findOne(item.getCouponId());
            if(price != null && (coupon.getMaxCount() > price)) {
                iterator.remove();
            }
        }
        return couponUserList.size();
    }

    @Override
    public Integer getUserValidityCouponsCount(Long userId) {
        Date nowTime = new Date();
        Integer count = couponUserDao.getUserValidityCouponsCount(userId,nowTime);
        return count;
    }

    @Override
    public Integer getUserCouponCount(Long userId) {
        Date nowTime = new Date();
        Integer count = couponUserDao.getUserCouponsCount(userId,nowTime);
        return count;
    }

    @Override
    public CouponUser getByUserIdAndCouponId(Long userId, Long couponId) {
        CouponUser  couponUserFind = new CouponUser();
        couponUserFind.setUserId(userId);
        couponUserFind.setCouponId(couponId);
        List<CouponUser> couponUsers = couponUserDao.findAll(Example.of(couponUserFind),new Sort(Sort.Direction.DESC,"id"));
        CouponUser couponUser = new CouponUser();
        if(couponUsers.size()==0){
            return couponUser;
        }
        couponUser = couponUsers.get(0);
//        CouponUser couponUser = couponUserDao.getByUserIdAndCouponId(userId,couponId);//2,代表分享要领取的优惠券
        return couponUser;
    }

    @Override
    public CouponUser getUserCouponById(Long id) {
        CouponUser couponUser = couponUserDao.findOne(id);
        return couponUser;
    }
    @Override
    public Coupon getCouponById(Long id) {
        Coupon coupon = couponDao.findOne(id);
        return coupon;
    }
    @Override
    public Date getCouponEndTime(Date nowTime,Coupon coupon) {
        Date endTime;
//        Date nowTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        if(coupon.getTimeType()==1){//天
            // 时间表示格式可以改变，yyyyMMdd需要写例如20160523这种形式的时间
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String str = "2016/05/23";
            // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
//            Date date = sdf.parse(str, new ParsePosition(0));
//            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowTime);
            // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
            calendar.add(Calendar.DATE, coupon.getValidTime());
            endTime = calendar.getTime();
//            String out = sdf.format(date1);
        }else{//月
            calendar.add(Calendar.MONTH, coupon.getValidTime());//几个月
            Date time = calendar.getTime();
            Long endTimeStr = time.getTime();
            endTime = new Date(endTimeStr);
        }
        return endTime;
    }
    @Override
    public Void acceptCoupon(Long userId,String phone) throws ApiException{

        //判断手机号是否注册
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"id"));
        Page<User> userPage =  userDao.getUserByPhoneAndUnsubscribe(phone, 0, pageable);
        // 查询userAccount
        List<UserAccount> userAccounts = userAccountDao.findUserAccountsBySrcId(phone);

        if(userPage.getContent().size()>0 || userAccounts.size()>0){
            //提示手机号/微信号已注册
            throw new ApiNotFoundException("该手机号已注册",2);
        }
        //判断是否存在这个用户（邀请链接无效，请重新获取）
        User user = userDao.findOne(userId);
        if(user == null){
            throw new ApiNotFoundException("邀请链接无效，请重新获取",3);
        }

        Pageable pageableTwo = new PageRequest(100, 1, new Sort(Sort.Direction.DESC,"id"));
        Page<CouponGetRecord> couponGetRecordPage = couponGetRecordDao.getCouponGetRecordsByUserId(userId, pageableTwo);

        if(couponGetRecordPage != null && couponGetRecordPage.getContent().size() > 0){
            CouponGetRecord lastRecord = couponGetRecordPage.getContent().get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = format.format(new Date());
            String lastRecordDateString = format.format(lastRecord.getCreateTime());
            if(nowDateString.equals(lastRecordDateString)){
                throw new ApiNotFoundException("邀请链接已打每日邀请上限！");
            }
        }

        CouponGetRecord couponGetRecord = couponGetRecordDao.getByPhone(phone);

        if(couponGetRecord ==null){
            //添加领取记录
            CouponGetRecord cgr = new CouponGetRecord();
            cgr.setUserId(userId);
            cgr.setPhone(phone);
            cgr.setCreateTime(new Date());
            cgr.setStatus(0);
            couponGetRecordDao.save(cgr);
        }else{
            //提示已领取
            throw new ApiNotFoundException("已经领取",4);
        }
        return null;
    }

    @Override
    public Void getCouponByRegsiter(Long userId,String phone) {
        //给注册者20元优惠券
        Coupon coupon = couponDao.findOne(3L);//20元
        //判断优惠券类型（1日/2月），并获取过期时间
        Date nowTime = new Date();
        Date endTime = this.getCouponEndTime(nowTime,coupon);
        this.addCouponUser(3,userId,0,endTime,nowTime,phone,0,"注册赠送");

        //判断此用户是否是被邀请，是则给分享者2元券
        CouponGetRecord couponGetRecord = couponGetRecordDao.getByPhone(phone);
        if(couponGetRecord != null && couponGetRecord.getStatus().equals(0)){
            User user = userDao.findOne(couponGetRecord.getUserId());
            Coupon couponFirst = couponDao.findOne(1L);//2元
            Date endTime2 = this.getCouponEndTime(nowTime,couponFirst);
            this.addCouponUser(1, couponGetRecord.getUserId(), 0, endTime2, nowTime, user.getPhone(),0, "");
        }
        return null;
    }

    @Override
    public void registerSendCoupon(Long userId, String phone){
        Date nowTime = new Date();
        Coupon couponThree = couponDao.findOne(2L);//20元
        Coupon couponFive = couponDao.findOne(5L);//5元
        Date threeEndTime = this.getCouponEndTime(nowTime,couponThree);
        Date fiveEndTime = this.getCouponEndTime(nowTime, couponFive);
        this.addCouponUser(2, userId, 0, threeEndTime, nowTime, phone, 0, "");
        this.addCouponUser(5, userId, 0, fiveEndTime, nowTime, phone, 0, "");
    }

    @Override
    public CouponUser addCouponUser(Integer couponId, Long userId, Integer status, Date endTime, Date createTime, String phone, Integer isView, String channel) {
        CouponUser couponUser = new CouponUser();
        couponUser.setUserId(userId);
        couponUser.setStatus(status);//未使用
        couponUser.setCouponId(couponId.longValue());
        couponUser.setPhone(phone);
        couponUser.setEndTime(endTime);
        couponUser.setIsView(isView);
        couponUser.setCreateTime(createTime);
        return couponUserDao.save(couponUser);
    }

    @Override
    public List<CouponStoryExchangeUser> getStoryCouponByShare(Long userId) {
        List<CouponStoryExchangeUser> couponStoryExchangeUserList = couponStoryExchangeUserDao.getCouponStoryExchangeUsersByCouponIdAndUserId(2, userId.intValue());
        if(couponStoryExchangeUserList.size() == 0){
            couponStoryExchangeUserList = new ArrayList<>();
            CouponStoryExchangeUser oneStoryCoupon = couponStoryExchangeUserService.addCouponStoryExchangeUser(2,userId.intValue());
            CouponStoryExchange couponStoryExchange = couponStoryExchangeDao.findOne(2);
            oneStoryCoupon.setCouponStoryExchange(couponStoryExchange);
            CouponStoryExchangeUser twoStoryCoupon = couponStoryExchangeUserService.addCouponStoryExchangeUser(2,userId.intValue());
            twoStoryCoupon.setCouponStoryExchange(couponStoryExchange);
            couponStoryExchangeUserList.add(oneStoryCoupon);
            couponStoryExchangeUserList.add(twoStoryCoupon);
            UserStarRecord userStarRecord = userStarRecordService.getSharePaster(userId);
            if(userStarRecord == null){
                // intro   分享推广海报赠送
                walletService.addStarToWallet(userId.intValue(),StarRechargeStyle.SHARE_PASTER,StarConfig.STAR_PASTER_SHARE, StarContentStyle.SHARE.getName());
            }
            return couponStoryExchangeUserList;
        } else {
            return null;
        }
    }

    @Override
    public CouponUser getCouponByShare(Long userId) throws ApiException {

        User user = userDao.findOne(userId);
        Coupon coupon = couponDao.findOne(2L);//10元
        //判断优惠券类型（1日/2月），并获取过期时间
        Date nowTime = new Date();
        Date endTime = this.getCouponEndTime(nowTime,coupon);
        //用户获取10元优惠券（仅第一次可领）
//        CouponUser cu = couponUserDao.getByUserIdAndCouponId(userId,coupon.getId());//2,代表分享要领取的优惠券

        List<CouponUser> couponUsers = couponUserDao.getCouponUsersByUserIdAndCouponId(userId, coupon.getId());
        CouponUser couponUser = new CouponUser();
        if(couponUsers.size()==0){
            couponUser.setUserId(userId);
            couponUser.setStatus(0);//未使用
            couponUser.setCouponId(coupon.getId());
            couponUser.setPhone(user.getPhone());
            couponUser.setEndTime(endTime);
            couponUser.setIsView(0);
            couponUser.setCreateTime(new Date());
            couponUserDao.save(couponUser);
            couponUser.setAmount(coupon.getAmount());
            couponUser.setContent(coupon.getContent());
            couponUser.setName(coupon.getName());
            //返回个装过获取多少优惠券
//            throw new ApiCouponUsedException("获取一张10元优惠券");
        }else{
            couponUser = null;
        }

        UserStarRecord userStarRecord = userStarRecordService.getSharePaster(userId);
        if(userStarRecord == null){
//            userStarRecordService.addUserStarRecord(userId, StarConfig.STAR_PASTER_SHARE, AddStyle.UP, StarRechargeStyle.SHARE_PASTER,"分享推广海报赠送");
            walletService.addStarToWallet(userId.intValue(),StarRechargeStyle.SHARE_PASTER,StarConfig.STAR_PASTER_SHARE,StarContentStyle.SHARE.getName());

        }


        return couponUser;
    }


    @Override
    public Void collectCoupon(Long userId,Long couponId,String channel) throws ApiException{
        //判断这个用户是否领取过此代金券
        CouponUser couponUserFind=new CouponUser();
        couponUserFind.setUserId(userId);
        couponUserFind.setCouponId(couponId);

//        CouponDeferredUser couponDeferredUser = couponDeferredUserDao.getByUserIdAndCouponId(userId,couponId);
        List<CouponUser> couponUserList = couponUserDao.findAll(Example.of(couponUserFind));

        //获取优惠券数据
        Coupon coupon = couponDao.findOne(couponId);

        if(couponUserList.size() == 0 || coupon.getGetCount() == 0) {
            Date nowTime = new Date();
            //获取结束时间
            Date endTime = this.getCouponEndTime(nowTime,coupon);
            //添加领取记录
            CouponUser couponUser = new CouponUser();
            couponUser.setCouponId(couponId);
            couponUser.setUserId(userId);
            couponUser.setStatus(0);
            couponUser.setEndTime(endTime);
            couponUser.setCreateTime(new Date());
            couponUser.setChannel(channel);
            couponUser.setIsView(0);
            couponUser.setPhone("");
            couponUserDao.save(couponUser);
        }else{
            //提示已领取
            throw new ApiNotFoundException("已经领取",2);
        }
        return null;
    }

    @Override
    public CouponUser getCouponUserById(Long couponUserId) {
        return couponUserDao.findOne(couponUserId);
    }

    @Override
    public CouponGetRecord getCouponGetRecord(String phone) {
        return couponGetRecordDao.getByPhone(phone);
    }
}

