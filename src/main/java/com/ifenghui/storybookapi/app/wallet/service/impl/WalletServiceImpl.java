package com.ifenghui.storybookapi.app.wallet.service.impl;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.dao.UserAccountRecordDao;
import com.ifenghui.storybookapi.app.wallet.dao.UserStarRecordDao;
import com.ifenghui.storybookapi.app.wallet.dao.WalletDao;
import com.ifenghui.storybookapi.app.wallet.entity.UserAccountRecord;
import com.ifenghui.storybookapi.app.wallet.entity.UserStarRecord;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.config.StarConfig;
import com.ifenghui.storybookapi.exception.ApiLackBalanceException;
import com.ifenghui.storybookapi.style.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wml on 2017/2/16.
 */
@Transactional
@Component
public class WalletServiceImpl implements WalletService {


    @Autowired
    WalletDao walletDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    UserDao userDao;


    @Autowired
    UserAccountRecordDao userAccountRecordDao;

    @Autowired
    UserStarRecordDao userStarRecordDao;

    @Transactional
    @Override
    public Wallet getWalletByUserId(Integer userId) {

        Wallet wallet = this.walletDao.getWalletByUserId(userId);
        if(wallet == null){
            this.initReg(userId);
            wallet = this.walletDao.getWalletByUserId(userId);
        }
        User user=userDao.findOne(wallet.getUserId().longValue());
        user.setStarCount(wallet.getStarCount());
        user.setBalance(wallet.getBalance());
        wallet.setUser(user);
        return wallet;
    }
    @Override
    public Wallet getWalletByUserId(Long userId) {
        return this.getWalletByUserId(userId.intValue());
    }

    @Override
    public Wallet getWalletByUserIdInCache(Integer userId) {
        Wallet wallet = this.walletDao.getWalletByUserIdInCache(userId);
        if(wallet ==null){
            return null;
        }
        User user=userDao.findOne(wallet.getUserId().longValue());
        user.setStarCount(wallet.getStarCount());
        user.setBalance(wallet.getBalance());
        wallet.setUser(user);
        return wallet;
    }

    @Transactional
    @Override
    public Wallet addAmountToWallet(int userId, WalletStyle walletStyle, RechargeStyle rechargeStyle, int amount,String outTradeNo, String intro) {

        /**
         * 获得钱包增加余额，增加记录,
         */
        UserAccountRecord userAccountRecordSrc= userAccountRecordDao.findOneByOutTradeNo(outTradeNo);


        Wallet wallet=walletDao.getWalletByUserId(userId);
        if(userAccountRecordSrc!=null){
//            throw new ApiLackBalanceException("已经支付完成，请不要重复支付");
            return wallet;
        }
        if(amount==0){
            return wallet;
        }

        UserAccountRecord userAccountRecord=new UserAccountRecord();
        if(amount>0){
            userAccountRecord.setAddStyle(AddStyle.UP);
        }else{
            userAccountRecord.setAddStyle(AddStyle.DOWN);
        }

        int newbalance = 0;

        if(walletStyle.equals(WalletStyle.IOS_WALLET)){

            newbalance = wallet.getBalance()+amount;
            if(newbalance < 0){
                throw new ApiLackBalanceException("您的余额不足！");
            }
            wallet.setBalance(newbalance);

        } else if (walletStyle.equals(WalletStyle.ANDROID_WALLET)) {
            newbalance = wallet.getBalanceAndroid() + amount;

            if (newbalance < 0) {
                throw new ApiLackBalanceException("您的余额不足！");
            }

            wallet.setBalanceAndroid(newbalance);

        } else if (walletStyle.equals(WalletStyle.SMALL_PROGRAM_WALLET)) {
            newbalance = wallet.getBalanceSmallProgram() + amount;
            if (newbalance < 0) {
                throw new ApiLackBalanceException("您的余额不足！");
            }
            wallet.setBalanceSmallProgram(newbalance);
        } else {
            throw new RuntimeException("not set wallet Style");
        }



        userAccountRecord.setAmount(amount);
        userAccountRecord.setBalance(newbalance);
        userAccountRecord.setCreateTime(new Date());
        userAccountRecord.setIntro(intro);
        userAccountRecord.setUserId((long)userId);
        userAccountRecord.setWalletStyle(walletStyle);
        userAccountRecord.setRechargeStyle(rechargeStyle);
        userAccountRecord.setOutTradeNo(outTradeNo);
        userAccountRecordDao.save(userAccountRecord);



        wallet=walletDao.save(wallet);

        return wallet;
    }

    @Transactional
    @Override
    public Wallet addStarToWallet(int userId, StarRechargeStyle starRechargeStyle, int amount, String intro) {
        Wallet wallet=walletDao.getWalletByUserId(userId);

        if(amount==0){
            return wallet;
        }

        UserStarRecord userStarRecord=new UserStarRecord();
        if(amount>0){
            userStarRecord.setAddStyle(AddStyle.UP);
        }else{
            userStarRecord.setAddStyle(AddStyle.DOWN);
        }

        int newbalance=0;
        newbalance=wallet.getStarCount()+amount;

        if(newbalance<0){
            throw new ApiLackBalanceException("您的星星值不足！");
        }


        userStarRecord.setAmount(amount);
        userStarRecord.setBalance(newbalance);
        userStarRecord.setCreateTime(new Date());
        userStarRecord.setIntro(intro);
        userStarRecord.setUserId(userId);
        userStarRecord.setStarRechargeStyle(starRechargeStyle);
        userStarRecordDao.save(userStarRecord);

        wallet.setStarCount(newbalance);

        wallet=walletDao.save(wallet);

        return wallet;
    }


    @Override
    public void addRegisterUserStarRecord(int userId) {
//        Integer buyType = 14;//注册获得积分
        String intro = "注册获得积分";
        this.addStarToWallet(userId,StarRechargeStyle.REG, StarConfig.STAR_REGISTER,StarContentStyle.REGISTER.getName());
    }

    @Override
    public void initReg(int userId) {
        Wallet wallet = walletDao.getWalletByUserId(userId);
        if (wallet == null){
            //添加钱包
            wallet = new Wallet();
            wallet.setBalance(0);
            wallet.setUserId(userId);
            wallet.setStatus(1);
            wallet.setStarCount(0);
            wallet.setCreateTime(new Date());
            wallet.setBalanceAndroid(0);
            wallet.setDiscount(100);
            wallet.setBalanceSmallProgram(0);
            wallet.setBalanceCash(0);
            walletDao.save(wallet);
        }
    }

    @Override
    public void changeUserDiscount(Integer userId, Integer userDiscount){
        Wallet wallet = walletDao.getWalletByUserId(userId);
        if(wallet.getDiscount() > userDiscount){
            wallet.setDiscount(userDiscount);
            walletDao.save(wallet);
        }
    }

    @Override
    public Integer getAndroidUserNewAmount(Integer userId, Integer orderAmount, OrderPayStyle payStyle, WalletStyle walletStyle){
        if(payStyle.equals(OrderPayStyle.ANDRIOD_BLANCE) && walletStyle.equals(WalletStyle.ANDROID_WALLET)){
            Wallet wallet = this.getWalletByUserId(userId);
            Float newAmount = ((float)wallet.getDiscount() / 100) * (float) orderAmount;
            return newAmount.intValue();
        } else {
            return orderAmount;
        }
    }
}
