package com.ifenghui.storybookapi.app.presale.service;

import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.presale.entity.PreSalePay;
import com.ifenghui.storybookapi.app.transaction.entity.OrderMix;
import com.ifenghui.storybookapi.app.transaction.entity.OrderPayActivity;
import com.ifenghui.storybookapi.style.OrderPayStyle;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface PreSalePayService {

    /**
     * 获得是否已经领取杂志活动礼品
     * @param userId
     * @return
     */
    Ads isGetMagazinePreSalePay(Integer userId);

    /**
     * 后台使用查所有订单
     * @param preSalePay
     * @param pageRequest
     * @return
     */
    Page<PreSalePay> findAll(PreSalePay preSalePay, PageRequest pageRequest);

    /**
     * 价格总和
     * @param preSalePay
     * @return
     */
    Integer sumPriceAll(PreSalePay preSalePay);

    /**
     * 判断是否领取过
     * @param userId
     * @param goodsId
     * @param activityId
     * @return
     */
    PreSalePay getPayByUserIdAndGoodsId(Integer userId,Integer goodsId,Integer activityId);
    /**
     * 获得orderString
     * @param amount
     * @param orderId
     * @param app_id
     * @param private_key
     * @param notify_url
     * @return
     */
    String getAlipayStr(int amount,int orderId,String app_id,String private_key,String notify_url);

    /**
     * 添加订单混合表
     * @param payId
     * @param order_type
     * @param user_id
     * @param status
     * @param is_del
     * @return
     */
    OrderMix addOrderMix(Integer payId, Integer order_type, Integer user_id, Integer status, Integer is_del);
    /**
     * 成功回调添加订单冗余表
     * @param userId
     * @param price
     * @param goodsId
     * @param status
     * @param content
     * @param icon
     * @return
     */
    @Transactional
    OrderPayActivity addOrderPayActivity(Integer userId, Integer price, Integer goodsId, Integer status, String content, String icon, OrderPayStyle orderPayStyle);

    /**
     * 添加app订单 生成out_trade_no
     * @param userId
     * @param goodsId
     * @param price
     * @return
     */
    @Transactional
    PreSalePay addOrder(Integer userId, Integer  userType, Integer  goodsId, Integer price, Integer activityId, Integer status, OrderPayStyle payStyle, String channel);
    /**
     * 添加订单 添加渠道
     * @param userId
     * @param goodsId
     * @param price
     * @return
     */
    PreSalePay addPay(Integer userId, Integer userType, Integer goodsId, Integer price, Integer activityId, Integer status, OrderPayStyle payStyle, String channel);

    /**
     * 查询订单数是否超过库存
     * @param goodsId
     * @param activityId
     * @return
     */
    List<PreSalePay> getPayList(Integer goodsId, Integer activityId);

    /**
     * 添加订单
     * @param userId
     * @param goodsId
     * @param price
     * @return
     */
    @Transactional
    PreSalePay add(Integer userId, Integer goodsId, Integer price,Integer activityId,Integer status,OrderPayStyle payStyle);

    /**
     * 修改订单
     * @param payId
     * @return
     */
    PreSalePay setPayStatusSuccess(Integer payId,OrderPayStyle payStyle);

    /**
     * 查订单
     * @param payId
     * @return
     */
    PreSalePay findPayById(Integer payId);

    /**
     * 扫码支付  下单返回信息
     */
    JSONObject doUnifiedOrder(String outTradeNo, String goodsName, Integer goodsPrice, Integer goodsId, HttpServletRequest request, String openId, String wxkey);


    /**
     * 修改状态
     * @param preSalePay
     * @return
     */
    PreSalePay update(PreSalePay preSalePay);

    /**
     * 更新激活时间
     * @param preSalePay
     * @param sign
     * @param type
     * @param vipTime
     * @return
     */
    PreSalePay updateActiveDate(PreSalePay preSalePay, String sign, Integer type, Date vipTime);
}
