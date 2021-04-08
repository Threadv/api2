package com.ifenghui.storybookapi.app.presale.service;


import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;


public interface PreSaleCodeService {


    /**
     * 生成兑换码 根据本身id
     * @return
     */
    PreSaleCode addCode(Integer userId,Integer userType,Integer activityId,Integer codeType);


    /**
     * 生成兑换码
     * @param payId
     * @return
     */
    PreSaleCode addCode(Integer payId);


    /**
     * 获得code列表
     * @param userId
     * @return
     */
    Page<PreSaleCode> getCodeListByUserId(Integer userId, Integer pageNo, Integer pageSize);

    /**
     * 通过payId获得code
     * @param payId
     * @return
     */
    PreSaleCode getCodeByPayId(Integer payId);

    public PreSaleCode getCodeByCode(String code);

    public PreSaleCode findCode(Integer id);

    void usePreSaleCode(PreSaleCode preSaleCode, Integer userId);

    public Page<PreSaleCode> findAllCodes(PreSaleCode code, PageRequest pageRequest);

    public PreSaleCode update(PreSaleCode code);

    public PreSaleCode add(PreSaleCode code);

    //    /**
//     * 设置兑换码过期
//     */
    void setExpire();

    /**
     * 查询即将过期兑换码
     */
    List<PreSaleCode> getCodesByEndTime(Integer codeType,Date endTime);


}
