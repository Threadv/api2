package com.ifenghui.storybookapi.style;

import com.ifenghui.storybookapi.app.presale.entity.SaleGoodsStyle;

/**
 * 激活码枚举
 */
public enum
VipGoodsStyle {

    /**
     * 兑换码类型
     */
    ENLIGHTEN_LESSON(1, SaleGoodsStyle.ENLIGHTEN_LESSON, 1),
    GROWTH_LESSON(2, SaleGoodsStyle.GROWTH_LESSON, 1),
    ENLIGHTEN_LESSON_FIVE(8, SaleGoodsStyle.ENLIGHTEN_LESSON_FIVE, 1),
    ENLIGHTEN_LESSON_TWENTY(9, SaleGoodsStyle.ENLIGHTEN_LESSON_TWENTY, 1),
    ENLIGHTEN_LESSON_ALL(10, SaleGoodsStyle.ENLIGHTEN_LESSON_ALL, 1),
    GROWTH_LESSON_FIVE(11, SaleGoodsStyle.GROWTH_LESSON_FIVE, 1),
    GROWTH_LESSON_TWENTY(12, SaleGoodsStyle.ENLIGHTEN_LESSON_TWENTY, 1),
    GROWTH_LESSON_ALL(13, SaleGoodsStyle.GROWTH_LESSON_ALL, 1),
    GROWTH_LESSON_14(14, SaleGoodsStyle.GROWTH_LESSON_14, 1),
    GROWTH_LESSON_15(15, SaleGoodsStyle.GROWTH_LESSON_15, 1),
    GROWTH_LESSON_16(16, SaleGoodsStyle.GROWTH_LESSON_16, 1),
    GROWTH_LESSON_17(17, SaleGoodsStyle.GROWTH_LESSON_17, 1),
    GROWTH_LESSON_19(19, SaleGoodsStyle.GROWTH_LESSON_19, 1),
    ENLIGHTEN_LESSON_18(18, SaleGoodsStyle.ENLIGHTEN_LESSON_18, 1),
    ENLIGHTEN_LESSON_20(20, SaleGoodsStyle.ENLIGHTEN_LESSON_20, 1),
    GROWTH_LESSON_21(21, SaleGoodsStyle.GROWTH_LESSON_21, 1),
    ENLIGHTEN_LESSON_22(22, SaleGoodsStyle.ENLIGHTEN_LESSON_22, 1),
    GROWTH_LESSON_23(23, SaleGoodsStyle.GROWTH_LESSON_23, 1),

    YEAR_VIP(28, VipPriceStyle.YEAR_VIP, 2),
    HALF_YEAR_VIP(29, VipPriceStyle.HALF_YEAR_VIP, 2),
    SEASON_VIP(30, VipPriceStyle.SEASON_VIP, 2),
    GIFT_YEAR_VIP(31, VipPriceStyle.GIFT_YEAR_VIP, 2),
    GIFT_HALF_YEAR_VIP(32, VipPriceStyle.GIFT_HALF_YEAR_VIP, 2),
    GIFT_SEASON_VIP(33, VipPriceStyle.GIFT_SEASON_VIP, 2),
    EXPRESS_YEAR_VIP(34, VipPriceStyle.YEAR_VIP, 1),
    EXPRESS_HALF_YEAR_VIP(35, VipPriceStyle.HALF_YEAR_VIP, 1),
    EXPRESS_SEASON_VIP(36, VipPriceStyle.SEASON_VIP, 1),
    GIFT_EXPRESS_YEAR_VIP(37, VipPriceStyle.GIFT_YEAR_VIP, 1),
    GIFT_EXPRESS_HALF_YEAR_VIP(38, VipPriceStyle.GIFT_HALF_YEAR_VIP, 1),
    GIFT_EXPRESS_SEASON_VIP(39, VipPriceStyle.GIFT_SEASON_VIP, 1),


    //售出的
    ABILITY_PLAN_YEAR_TWO_FOUR(40, AbilityPlanCodeStyle.TWO_FOUR_YEAR, 2),
    ABILITY_PLAN_YEAR_FOUR_SIX(41, AbilityPlanCodeStyle.FOUR_SIX_YEAR, 2),

    //送的
    ABILITY_PLAN_YEAR_TWO_FOUR_CODE(42, AbilityPlanCodeStyle.TWO_FOUR_YEAR, 1),
    ABILITY_PLAN_YEAR_FOUR_SIX_CODE(43, AbilityPlanCodeStyle.FOUR_SIX_YEAR, 1),

    //默认无类型 42 43已经废弃
    ABILITY_PLAN_YEAR_DEFAULT(-43, AbilityPlanCodeStyle.DEFAULT_STYLE, 1),

    //不区分类型的兑换码
    ABILITY_PLAN_HALF_YEAR_TWO_FOUR_CODE(44, AbilityPlanCodeStyle.TWO_FOUR_HALF_YEAR, 1),
    ABILITY_PLAN_HALF_YEAR_FOUR_SIX_CODE(45, AbilityPlanCodeStyle.FOUR_SIX_HALF_YEAR, 1),
    ABILITY_PLAN_SEASON_TWO_FOUR_CODE(46, AbilityPlanCodeStyle.TWO_FOUR_SEASON, 1),
    ABILITY_PLAN_SEASON_FOUR_SIX_CODE(47, AbilityPlanCodeStyle.FOUR_SIX_SEASON, 1),

    ABILITY_PLAN_MONTH_TWO_FOUR_CODE(48, AbilityPlanCodeStyle.TWO_FOUR_MONTH, 1),
    ABILITY_PLAN_MONTH_FOUR_SIX_CODE(49, AbilityPlanCodeStyle.FOUR_SIX_MONTH, 1),
    //默认无类型 49 49已经废弃
    ABILITY_PLAN_MONTH_DEFAULT(-49, AbilityPlanCodeStyle.DEFAULT_MONTH_STYLE, 1),

    ABILITY_PLAN_OTHER_TWO_FOUR_CODE(50, AbilityPlanCodeStyle.TWO_FOUR_OTHER, 1),
    ABILITY_PLAN_OTHER_FOUR_SIX_CODE(51, AbilityPlanCodeStyle.FOUR_SIX_OTHER, 1),

    ABILITY_PLAN_OTHER_TWO_FOUR_CODE47(54, AbilityPlanCodeStyle.TWO_FOUR_OTHER47, 1),
    ABILITY_PLAN_OTHER_FOUR_SIX_CODE47(55, AbilityPlanCodeStyle.FOUR_SIX_OTHER47, 1),

    //系列故事
    SERIAL_WULALA(52,SerialStoryCodeStyle.WULALA_SERIAL, 1),
    SERIAL_AUDIO(53, SerialStoryCodeStyle.AUDIO_SERIAL, 1),
    SERIAL_XIYOUJI(56, SerialStoryCodeStyle.XIYOUJI_SERIAL, 1),
    SERIAL_XIYOUJI_2(59, SerialStoryCodeStyle.XIYOUJI_SERIAL_2, 1),//西游记第二季

    //十五个月宝宝会读
    ABILITY_PLAN_15_MONTH_DEFAULT(57, AbilityPlanCodeStyle.DEFAULT_15_MOMTH_STYLE, 1),
    //全年只包含线上
    ABILITY_PLAN_YEAR_ONLINE(58, AbilityPlanCodeStyle.DEFAULT_YEAR_ONLINE, 1);

    int id;
    SaleGoodsStyle saleGoodsStyle; //课程
    VipPriceStyle vipPriceStyle;// vip会员订阅
    AbilityPlanCodeStyle abilityPlanCodeStyle;//宝宝会读（优能计划）
    SerialStoryCodeStyle serialStoryCodeStyle;//宝宝会读（优能计划）
    /**
     * 1 不需物流信息 2 需要物流信息
     */
    int codeType;
    String templateCode;
    String title;//标题描述

    VipGoodsStyle(int id, SaleGoodsStyle saleGoodsStyle, int codeType) {
        this.id = id;
        this.vipPriceStyle = null;
        this.saleGoodsStyle = saleGoodsStyle;
        this.codeType = codeType;
        this.abilityPlanCodeStyle = null;
        this.serialStoryCodeStyle = null;
        this.title=saleGoodsStyle.getName();
    }

    VipGoodsStyle(int id, VipPriceStyle vipPriceStyle, int codeType) {
        this.id = id;
        this.vipPriceStyle = vipPriceStyle;
        this.saleGoodsStyle = null;
        this.codeType = codeType;
        this.abilityPlanCodeStyle = null;
        this.serialStoryCodeStyle = null;
        this.title=vipPriceStyle.getName();

    }

    VipGoodsStyle(int id, AbilityPlanCodeStyle abilityPlanCodeStyle, int codeType) {
        this.id = id;
        this.vipPriceStyle = null;
        this.saleGoodsStyle = null;
        this.codeType = codeType;
        this.abilityPlanCodeStyle = abilityPlanCodeStyle;
        this.serialStoryCodeStyle = null;
        this.templateCode="SMS_163439235";
        this.title=abilityPlanCodeStyle.getTitle()+abilityPlanCodeStyle.getName();
    }

    VipGoodsStyle(int id, SerialStoryCodeStyle serialStoryCodeStyle, int codeType) {
        this.id = id;
        this.vipPriceStyle = null;
        this.saleGoodsStyle = null;
        this.codeType = codeType;
        this.abilityPlanCodeStyle = null;
        this.serialStoryCodeStyle = serialStoryCodeStyle;
        this.templateCode="SMS_163439235";

        this.title=serialStoryCodeStyle.getName();

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VipPriceStyle getVipPriceStyle() {
        return vipPriceStyle;
    }

    public void setVipPriceStyle(VipPriceStyle vipPriceStyle) {
        this.vipPriceStyle = vipPriceStyle;
    }

    public SaleGoodsStyle getSaleGoodsStyle() {
        return saleGoodsStyle;
    }

    public void setSaleGoodsStyle(SaleGoodsStyle saleGoodsStyle) {
        this.saleGoodsStyle = saleGoodsStyle;
    }

    public static VipGoodsStyle getById(int id) {
        for (VipGoodsStyle goodsStyle : VipGoodsStyle.values()) {
            if (goodsStyle.getId() == id) {
                return goodsStyle;
            }
        }
        return null;
    }

    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
    }

    public AbilityPlanCodeStyle getAbilityPlanCodeStyle() {
        return abilityPlanCodeStyle;
    }

    public void setAbilityPlanCodeStyle(AbilityPlanCodeStyle abilityPlanCodeStyle) {
        this.abilityPlanCodeStyle = abilityPlanCodeStyle;
    }

    public SerialStoryCodeStyle getSerialStoryCodeStyle() {
        return serialStoryCodeStyle;
    }

    public void setSerialStoryCodeStyle(SerialStoryCodeStyle serialStoryCodeStyle) {
        this.serialStoryCodeStyle = serialStoryCodeStyle;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
