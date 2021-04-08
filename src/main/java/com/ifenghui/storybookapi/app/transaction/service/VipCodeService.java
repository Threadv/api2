package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.wallet.response.GetVipCodeDetailResponse;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;

public interface VipCodeService {

    /**
     * 激活兑换码
     * @param userId
     * @param code
     * @return
     */
    public SubscribeByCodeResponse subscribeByCode(Long userId, String code);

    /**
     * 获取分销兑换码详情
     * 早期订阅的功能已经不再使用
     * @param code
     * @return
     */
//    public GetVipCodeDetailResponse getVipcodeDetail(String code);

    /**
     * 获得课程兑换码详情
     * @param code
     * @return
     */
    public GetVipCodeDetailResponse getSaleLessonVipCodeDetail(Long userId,String code);

    /**
     * 检测预售兑换码
     * @param code
     */
    void checkSaleLessonVipCode(PreSaleCode code);
}
