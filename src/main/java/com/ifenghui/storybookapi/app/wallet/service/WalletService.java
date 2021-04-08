package com.ifenghui.storybookapi.app.wallet.service;

import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.StarRechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wml on 2017/2/16.
 */
public interface WalletService {


    Wallet getWalletByUserId(Integer userId);//

    Wallet getWalletByUserId(Long userId);//

    /**
     * 带缓存的钱包，用户数据展示，不能用于交易
     * @param userId
     * @return
     */
    Wallet getWalletByUserIdInCache(Integer userId);//

//    @Deprecated
//    Wallet editWalletBalance(Long userId,Integer amount,Integer type);//1添加2减少

    /**
     * 获得钱包
     * @param userId
     * @param walletStyle
     * @return
     */
//    Wallet getWallet(int userId, WalletStyle walletStyle);

    /**
     * 增加或减少余额
     * @param userId
     * @param walletStyle
     * @param amount
     * @return
     */
    @Transactional
    Wallet addAmountToWallet(int userId, WalletStyle walletStyle,RechargeStyle rechargeStyle,int amount,String outTradeNo, String intro);

    /**
     * 增加或减少星星
     * @param userId
     * @param amount
     * @return
     */
    @Transactional
    Wallet addStarToWallet(int userId, StarRechargeStyle starRechargeStyle, int amount, String intro);

    /**
     * 注册时增加星星值
     * @param userId
     */
    void addRegisterUserStarRecord(int userId);

    /**
     * 创建用户时创建
     * @param userId
     */
    void initReg(int userId);

    /**
     * 修改用户的折扣
     * @param userId
     * @param userDiscount
     */
    void changeUserDiscount(Integer userId, Integer userDiscount);

    public Integer getAndroidUserNewAmount(Integer userId, Integer orderAmount, OrderPayStyle payStyle, WalletStyle walletStyle);
}
