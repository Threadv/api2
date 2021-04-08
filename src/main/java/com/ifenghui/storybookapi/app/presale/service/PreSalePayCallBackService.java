package com.ifenghui.storybookapi.app.presale.service;


import com.ifenghui.storybookapi.app.presale.entity.PreSalePayCallBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PreSalePayCallBackService {

    PreSalePayCallBack getPayCallbackRecordByPayId(Integer payId);

    PreSalePayCallBack addPayCallback(PreSalePayCallBack preSalePayCallBack);

    Page<PreSalePayCallBack> findAll(PreSalePayCallBack payCallBack, PageRequest pageRequest);
}
