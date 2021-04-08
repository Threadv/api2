package com.ifenghui.storybookapi.app.wallet.entity;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface UserAccountRecordInterface {

    Long getId();

    Long getUserId();

    Integer getAmount();

    Integer getType();

    Date getCreateTime();

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date getCreateTimeFormat();

    String getIntro();

    Integer getBalance();

    String getOutTradeNo();

}
