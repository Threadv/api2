package com.ifenghui.storybookapi.app.transaction.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifenghui.storybookapi.style.VipPriceStyle;

import java.util.List;

public class ReturnSvip {

    SvipPrice svipPrice;

    List<SvipGift> svipGiftList;

    @JsonProperty("content")
    public String getContent() {
        return "1.《锋绘》 杂志：全年23本纸质期刊，2-6岁亲子共读，每月免费寄送1次。\n\n2.全年学习计划：会员期免费体验，365天科学成长计划，提升孩子自主学习能力！\n\n3.飞船阅读课：会员期间，免费学习飞船阅读课（启蒙版、成长版）全部内容，全年共100节。\n\n4.APP故事阅读权：互动故事会员免费读。\n\n5.飞船商城8折优惠：除纯星值兑换的商品外，会员购物享8折。\n\n6.亲友卡：每次购买VIP赠送3张，每张可分享赠送好友（新用户）5个免费故事。\n\n";
    }

    public SvipPrice getSvipPrice() {
        return svipPrice;
    }

    public void setSvipPrice(SvipPrice svipPrice) {
        this.svipPrice = svipPrice;
    }

    public List<SvipGift> getSvipGiftList() {
        return svipGiftList;
    }

    public void setSvipGiftList(List<SvipGift> svipGiftList) {
        this.svipGiftList = svipGiftList;
    }
}
