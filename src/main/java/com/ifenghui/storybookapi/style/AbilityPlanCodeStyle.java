package com.ifenghui.storybookapi.style;

import com.ifenghui.storybookapi.config.MyEnv;

import static com.ifenghui.storybookapi.style.AbilityPlanStyle.FOUR_SIX;
import static com.ifenghui.storybookapi.style.AbilityPlanStyle.TWO_FOUR;
import static com.ifenghui.storybookapi.style.AbilityPlanStyle.DEFAULT;

/**
 * 宝宝会读（优能计划）兑换码
 * Created by wslhk on 2016/12/22.
 */
public enum AbilityPlanCodeStyle {

    /**
     * 宝宝会读（优能计划） 购买类型
     */
    TWO_FOUR_YEAR(1,"2-4岁(52周)","ability.plan.price.year",366,TWO_FOUR,"ability.plan.real.price.year",52,40,"全年"),
    FOUR_SIX_YEAR(2,"4-6岁(52周)","ability.plan.price.year",366,FOUR_SIX,"ability.plan.real.price.year",52,41,"全年"),

    TWO_FOUR_HALF_YEAR(3,"2-4岁(26周)","ability.plan.price.halfyear",183,TWO_FOUR,"ability.plan.real.price.halfyear",26,44,"半年"),
    FOUR_SIX_HALF_YEAR(4,"4-6岁(26周)","ability.plan.price.halfyear",183,FOUR_SIX,"ability.plan.real.price.halfyear",26,45,"半年"),

    TWO_FOUR_SEASON(5,"2-4岁(13周)","ability.plan.price.season",92,TWO_FOUR,"ability.plan.real.price.season",13,46,"季度"),
    FOUR_SIX_SEASON(6,"4-6岁(13周)","ability.plan.price.season",92,FOUR_SIX,"ability.plan.real.price.season",13,47,"季度"),

    TWO_FOUR_MONTH(7,"2-4岁(4周)","ability.plan.price.month",31,TWO_FOUR,"ability.plan.real.price.month",4,48,"单月"),
    FOUR_SIX_MONTH(8,"4-6岁(4周)","ability.plan.price.month",31,FOUR_SIX,"ability.plan.real.price.month",4,49,"单月"),

    TWO_FOUR_OTHER(9,"2-4岁(48周)","ability.plan.price.other",335,TWO_FOUR,"ability.plan.real.price.other",48,50,"剩余"),
    FOUR_SIX_OTHER(10,"4-6岁(48周)","ability.plan.price.other",335,FOUR_SIX,"ability.plan.real.price.other",48,51,"剩余"),

    TWO_FOUR_OTHER47(11,"2-4岁(47周)","ability.plan.price.other",335,TWO_FOUR,"ability.plan.real.price.other",47,54,"剩余"),
    FOUR_SIX_OTHER47(12,"4-6岁(47周)","ability.plan.price.other",335,FOUR_SIX,"ability.plan.real.price.other",47,55,"剩余"),

    /**2.11.0 默认购买无类型订单*/
    DEFAULT_STYLE(13,"宝宝会读","ability.plan.price.year",366,DEFAULT,"ability.plan.real.price.year",52,0,"全年"),
    DEFAULT_MONTH_STYLE(14,"宝宝会读","ability.plan.price.month",31,DEFAULT,"ability.plan.real.price.month",4,0,"单月"),

    //15个月的计划
    DEFAULT_15_MOMTH_STYLE(15,"宝宝会读","ability.plan.price.15month",457,DEFAULT,"ability.plan.real.price.15month",52,0,"全年+季度"),

    //12个月的计划,线上
    DEFAULT_YEAR_ONLINE(16,"线上专属权益","ability.plan.price.year.online",366,DEFAULT,"ability.plan.real.price.year.online",52,0,"全年",1),
    //ability.plan.price.year.online
    /**
     * 内不调用 添加一周时间使用
     */
    TWO_FOUR_WEEK(21,"内部使用","",7,TWO_FOUR,"",1,0,""),
    FOUR_SIX_WEEK(22,"内部使用","",7,FOUR_SIX,"",1,0,""),
    ;

    int id;
    String name;
    String priceCode;
    int days;
    AbilityPlanStyle abilityPlanStyle;
    String realPriceCode;
    int buyNum;
    int priceId;
    String title;

    int onlineOnly=0;
    AbilityPlanCodeStyle(int id, String name, String priceCode, int days,AbilityPlanStyle abilityPlanStyle,String realPriceCode,int buyNum,int priceId,String title){
        this.id=id;
        this.name=name;
        this.priceCode=priceCode;
        this.days=days;
        this.abilityPlanStyle=abilityPlanStyle;
        this.realPriceCode=realPriceCode;
        this.buyNum=buyNum;
        this.priceId=priceId;
        this.title=title;
    }

    AbilityPlanCodeStyle(int id, String name, String priceCode, int days,AbilityPlanStyle abilityPlanStyle,String realPriceCode,int buyNum,int priceId,String title,int onlineOnly){
        this.id=id;
        this.name=name;
        this.priceCode=priceCode;
        this.days=days;
        this.abilityPlanStyle=abilityPlanStyle;
        this.realPriceCode=realPriceCode;
        this.buyNum=buyNum;
        this.priceId=priceId;
        this.title=title;
        this.onlineOnly=onlineOnly;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public Integer getPrice() {
        return Integer.parseInt(MyEnv.env.getProperty(priceCode));
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public String getRealPriceCode() {
        return realPriceCode;
    }

    public void setRealPriceCode(String realPriceCode) {
        this.realPriceCode = realPriceCode;
    }

    public Integer getRealPrice() {
        return Integer.parseInt(MyEnv.env.getProperty(realPriceCode));
    }


    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public static AbilityPlanCodeStyle getById(int id) {
        for (AbilityPlanCodeStyle abilityPlanStyle : AbilityPlanCodeStyle.values()) {
            if (abilityPlanStyle.getId() == id) {
                return abilityPlanStyle;
            }
        }
        return null;
    }

    public static AbilityPlanCodeStyle getByPriceId(int priceId) {
        for (AbilityPlanCodeStyle abilityPlanStyle : AbilityPlanCodeStyle.values()) {
            if (abilityPlanStyle.getPriceId() == priceId) {
                return abilityPlanStyle;
            }
        }
        return null;
    }


    public AbilityPlanStyle getAbilityPlanStyle() {
        return abilityPlanStyle;
    }

    public void setAbilityPlanStyle(AbilityPlanStyle abilityPlanStyle) {
        this.abilityPlanStyle = abilityPlanStyle;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOnlineOnly() {
        return onlineOnly;
    }

    public void setOnlineOnly(int onlineOnly) {
        this.onlineOnly = onlineOnly;
    }
}
