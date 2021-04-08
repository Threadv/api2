package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.PhoneCheckCode;
import com.ifenghui.storybookapi.style.CheckCodeStyle;

import java.util.Date;

public interface PhoneCheckCodeService {

//    public PhoneCheckCode addPhoneCheckCode(String content, String phone, Date endTime, CheckCodeStyle checkCodeStyle);

    /**
     * 短信注册专用
     * @param phone
     * @param checkCodeStyle
     * @return
     */
    public PhoneCheckCode createPhoneCheckCode(String phone, CheckCodeStyle checkCodeStyle);

    /**
     * 验证码发送通用
     * @param phone
     * @param checkCodeStyle
     * @return
     */
    public PhoneCheckCode createPhoneCheckCodeSimple(String phone, CheckCodeStyle checkCodeStyle);

    public String getRandString();

    Integer checkCode(String code, String phone);
}
