package com.ifenghui.storybookapi.app.express.service;


import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface ExpressCenterPhoneBindService {

    /**
     * 发送短信验证
     * @param phone
     */
    public void sendSmsVerification(String phone);

    /**
     * 检测验证码是否正确
     * @param phone
     * @param verifyCode
     * @return
     */
    public boolean checkSmsVerification(String phone,String verifyCode);

    /**
     * 增加手机号绑定
     * @param phoneBind
     */
    public void addExpressCenterPhoneBind(ExpressCenterPhoneBind phoneBind);

    /**
     * 通过userid和活跃状态查询
     * @param
     * @return
     */
    ExpressCenterPhoneBind findByUserIdAnd(Integer userId, Integer isActive);


    public Page<ExpressCenterPhoneBind> findAll(ExpressCenterPhoneBind phoneBind, PageRequest request);

    public ExpressCenterPhoneBind findOne(Integer id);


    public void delete(Integer id);

}
