package com.ifenghui.storybookapi.app.wallet.service.impl;

/**
 * Created by jia on 2016/12/28.
 */

import com.ifenghui.storybookapi.adminapi.controlleradmin.order.DateQuery;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.transaction.dao.PaySerialStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.PayStoryOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.PaySubscriptionOrderDao;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.PayLessonOrderDao;
import com.ifenghui.storybookapi.app.transaction.entity.PayStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonOrder;
import com.ifenghui.storybookapi.app.transaction.entity.serial.PaySerialStoryOrder;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.PaySubscriptionOrder;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.service.AbilityPlanOrderService;
import com.ifenghui.storybookapi.app.transaction.service.PayVipOrderService;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.dao.PayRechargeOrderDao;

import com.ifenghui.storybookapi.app.wallet.dao.PayRechargePriceDao;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargeOrder;
import com.ifenghui.storybookapi.app.wallet.entity.PayRechargePrice;
import com.ifenghui.storybookapi.app.wallet.service.PayRechargeOrderService;
import com.ifenghui.storybookapi.style.RechargePayStyle;
import com.ifenghui.storybookapi.style.RechargeStyle;
import com.ifenghui.storybookapi.style.WalletStyle;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Component
public class PayRechargeOrderServiceImpl implements PayRechargeOrderService {

    private static Logger logger = Logger.getLogger(PayServiceImpl.class);
    @Autowired
    UserDao userDao;
    @Autowired
    StoryDao storyDao;
    @Autowired
    PayRechargeOrderDao payRechargeOrderDao;
    @Autowired
    PaySerialStoryOrderDao paySerialStoryOrderDao;
    @Autowired
    PaySubscriptionOrderDao paySubscriptionOrderDao;
    @Autowired
    PayStoryOrderDao payStoryOrderDao;
    @Autowired
    PayRechargePriceDao payRechargePriceDao;
    @Autowired
    PayLessonOrderDao payLessonOrderDao;
    @Autowired
    UserService userService;
    @Autowired
    private Environment env;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    PayVipOrderService payVipOrderService;

    @Autowired
    AbilityPlanOrderService abilityPlanOrderService;

    @Override
    public PayRechargeOrder getPayRechargeOrder(Long id) {
        return payRechargeOrderDao.findOne(id);
    }

    @Override
    public PayRechargeOrder updateRechargePayOrder(PayRechargeOrder payRechargeOrder) {
        return payRechargeOrderDao.save(payRechargeOrder);
    }

    @Override
    public Integer getAmountByRechargeStyle(RechargeStyle rechargeStyle, Integer orderId, Long priceId) {
        Integer amount = 0;
        if (rechargeStyle.equals(RechargeStyle.BUY_STORY)) {
            /**
             * 购买，获取购买订单和价格
             */
            PayStoryOrder payStoryOrder = payStoryOrderDao.findOne(orderId.longValue());
            amount = payStoryOrder.getAmount();
        } else if (rechargeStyle.equals(RechargeStyle.SUBSCRIPTION)) {
            /**
             * 订阅，获取订阅订单和价格
             */
            PaySubscriptionOrder paySubscriptionOrder = paySubscriptionOrderDao.findOne(orderId.longValue());
            amount = paySubscriptionOrder.getAmount();
        } else if (rechargeStyle.equals(RechargeStyle.SERIAL)) {
            /**
             * 购买故事集，获取购买故事集订单和价格
             */
            PaySerialStoryOrder paySerialStoryOrder = paySerialStoryOrderDao.findOne(orderId);
            amount = paySerialStoryOrder.getAmount();

        } else if (rechargeStyle.equals(RechargeStyle.LESSON)) {
            PayLessonOrder payLessonOrder = payLessonOrderDao.findOne(orderId);
            amount = payLessonOrder.getAmount();
        } else if (rechargeStyle.equals(RechargeStyle.BUY_SVIP)) {
            PayVipOrder payVipOrder = payVipOrderService.getPayVipOrderById(orderId);
            amount = payVipOrder.getAmount();
        } else if (rechargeStyle.equals(RechargeStyle.BUY_ABILITY_PLAN)) {
            AbilityPlanOrder abilityPlanOrder = abilityPlanOrderService.getAbilityPlanOrder(orderId);
            amount = abilityPlanOrder.getAmount();
        } else {
            /**
             * 类型为0时，是直接充值，查询价格
             */
            PayRechargePrice payRechargePrice = payRechargePriceDao.findOne(priceId);
            amount = payRechargePrice.getPrice();
        }

        return amount;
    }

    @Override
    public PayRechargeOrder addPayRechargeOrder(Integer userId, Integer amount, RechargePayStyle payStyle, RechargeStyle rechargeStyle, String channel, String appName) {
        User user = userService.getUser(userId);
        PayRechargeOrder payRechargeOrder = new PayRechargeOrder();
        payRechargeOrder.setUserId(userId);
        payRechargeOrder.setAmount(amount);
        payRechargeOrder.setOrderCode("");
        payRechargeOrder.setPayStyle(payStyle);
        payRechargeOrder.setPayAccount("");
        payRechargeOrder.setStatus(0);
        payRechargeOrder.setRechargeStyle(rechargeStyle);
        payRechargeOrder.setCreateTime(new Date());
        payRechargeOrder.setChannel(channel);
        payRechargeOrder.setTradeNo("");
        payRechargeOrder.setIsTest(user.getIsTest());
        payRechargeOrder.setAppName(appName);
        WalletStyle walletStyle = VersionUtil.getWalletStyle(httpServletRequest);
        payRechargeOrder.setWalletStyle(walletStyle);
        payRechargeOrder = payRechargeOrderDao.save(payRechargeOrder);
        return payRechargeOrder;
    }

    @Override
    public void setPayRechargeOrderNotifyAddress(PayRechargeOrder payRechargeOrder, String alipay_notify_url) {
        String wxpaynotify;
        String huaweipaynotify;
        String appName = payRechargeOrder.getAppName();
        if (appName.equals("")) {
            wxpaynotify = env.getProperty("wxpay.notify");
        } else {
            wxpaynotify = env.getProperty("childwxpay.notify") + "/" + appName;
        }
        if (appName.equals("zhijianStory")) {
            wxpaynotify = env.getProperty("zhijianpay.notify") + "/" + appName;
        }

        logger.info("----------------wxnotifyurl---" + wxpaynotify);
        payRechargeOrder.setWxPayNotifyUrl(wxpaynotify);
        payRechargeOrder.setAliPayNotifyUrl(alipay_notify_url);
        payRechargeOrder.setIosAliPayNotifyUrl(env.getProperty("iosalipay.notify"));
        payRechargeOrder.setIosPayNotifyUrl(env.getProperty("iospay.notify"));
        payRechargeOrder.setHuaweiPayNotifyUrl(env.getProperty("huaweipay.notify"));
        logger.info("--------------------getPayRechargeOrder----add payrechargeorder-----");
    }

    @Override
    public Page<PayRechargeOrder> findAllBy(PayRechargeOrder payRechargeOrder, DateQuery dateQuery, PageRequest pageRequest) {

        return payRechargeOrderDao.findAll(this.rechargeOrderMixSpec(payRechargeOrder,dateQuery),pageRequest);
    }
    private Specification<PayRechargeOrder> rechargeOrderMixSpec(PayRechargeOrder payRechargeOrder,DateQuery dateQuery){
        return new Specification<PayRechargeOrder>() {
            @Override
            public Predicate toPredicate(Root<PayRechargeOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<Predicate>();

                if (payRechargeOrder.getUserId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("userId"),payRechargeOrder.getUserId()));
                }
                if (payRechargeOrder.getStatus() != null){
                    predicates.add(criteriaBuilder.equal(root.get("status"),payRechargeOrder.getStatus()));
                }
                if (payRechargeOrder.getIsTest() != null){
                    predicates.add(criteriaBuilder.equal(root.get("isTest"),payRechargeOrder.getIsTest()));
                }
                if (payRechargeOrder.getBuyType() != null){
                    predicates.add(criteriaBuilder.equal(root.get("buyType"),payRechargeOrder.getBuyType()));
                }
                if (payRechargeOrder.getType() != null){
                    predicates.add(criteriaBuilder.equal(root.get("type"),payRechargeOrder.getType()));
                }
                if (payRechargeOrder.getChannel() != null){
                    predicates.add(criteriaBuilder.equal(root.get("channel"),payRechargeOrder.getChannel()));
                }
                if (dateQuery.getSuccessStartTime() != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("successTime"),dateQuery.getSuccessStartTime()));
                }
                if (dateQuery.getSuccessEndTime() != null){
                    predicates.add(criteriaBuilder.lessThan(root.get("successTime"),dateQuery.getSuccessEndTime()));
                }
                if (dateQuery.getCreateStartTime() != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"),dateQuery.getCreateStartTime()));
                }
                if (dateQuery.getCreateEndTime() != null){
                    predicates.add(criteriaBuilder.lessThan(root.get("createTime"),dateQuery.getCreateEndTime()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }


}
