package com.ifenghui.storybookapi.app.presale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.OrderPayStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "story_pre_sale_pay")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PreSalePay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    //用户id
    Integer userId;

    //活动id
    Integer activityId;

    //商品ID
    Integer goodsId;

    //价格
    Integer price;

    //用户类型 1app，2微信
    Integer userType;

    //状态 0等待支付 1已成交
    Integer status;

    /**
     * 支付方式
     * @see OrderPayStyle
     */
    Integer payStyle;

    /**
     * 渠道
     */
    String channel;

    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    //购买成功时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date successTime;

    //外部订单号唯一号
    String outTradeNo;

    @Transient
    PreSaleGoods preSaleGoods;

    @Transient
    PreSaleGift address;

    @Transient
    Activity activity;

    @Transient
    PreSaleUser user;

    /**
     * 订单如果包含兑换码，在这里冗余兑换码
     */
    String code;

    /**
     * 激活用户标记，如果是微信激活记录昵称，
     * 手机号激活记录手机号
     */
    String sign;

    /**
     * 权益截至时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date vipTime;

    /**
     * 激活用户的登录方式，不同的type记录不同的sign，用于显示在ui上 [手机号激活|微信激活]
     */
    //激活类型 1 手机号 2 微信号
    Integer type;

    /**
     * 是否已激活兑换码
     */
    Integer isActive;

    /**
     * 激活成功时间
     */
    Date activeTime;
    /**
     * 是否已经增加物流地址
     */
    Integer isExpress;

    /**
     * 是否已经导入到物流中心
     */
    Integer isExpressCenter;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(OrderPayStyle payStyle) {
        this.payStyle = payStyle.getId();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public PreSaleGoods getPreSaleGoods() {
        return preSaleGoods;
    }

    public void setPreSaleGoods(PreSaleGoods preSaleGoods) {
        this.preSaleGoods = preSaleGoods;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Date getVipTime() {
        return vipTime;
    }

    public void setVipTime(Date vipTime) {
        this.vipTime = vipTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public PreSaleGift getAddress() {
        return address;
    }

    public void setAddress(PreSaleGift address) {
        this.address = address;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Integer getIsExpress() {
        return isExpress;
    }

    public void setIsExpress(Integer isExpress) {
        this.isExpress = isExpress;
    }

    public Integer getIsExpressCenter() {
        return isExpressCenter;
    }

    public void setIsExpressCenter(Integer isExpressCenter) {
        this.isExpressCenter = isExpressCenter;
    }

    public PreSaleUser getUser() {
        return user;
    }

    public void setUser(PreSaleUser user) {
        this.user = user;
    }
}
