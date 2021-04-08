package com.ifenghui.storybookapi.app.express.service.impl;

import com.ifenghui.storybookapi.app.app.service.PhoneCheckCodeService;
import com.ifenghui.storybookapi.app.express.dao.ExpressCenterPhoneBindDao;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.style.CheckCodeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ExpressCenterPhoneBindServiceImpl implements ExpressCenterPhoneBindService {

    @Autowired
    ExpressCenterPhoneBindDao expressCenterPhoneBindDao;

    @Autowired
    PhoneCheckCodeService phoneCheckCodeService;


    @Override
    public void sendSmsVerification(String phone) {
        phoneCheckCodeService.createPhoneCheckCodeSimple(phone, CheckCodeStyle.EXPRESS_CENTER_PHONE);
    }

    @Override
    public boolean checkSmsVerification(String phone, String verifyCode) {
        return phoneCheckCodeService.checkCode(verifyCode,phone)==1?true:false;
    }

    @Override
    public void addExpressCenterPhoneBind(ExpressCenterPhoneBind phoneBind) {

        List<ExpressCenterPhoneBind> list=expressCenterPhoneBindDao.findAll(Example.of(phoneBind));
        if(list.size()>0){
            //已经存在
            phoneBind=list.get(0);
            phoneBind.setIsActive(1);
        }else{
            phoneBind.setCreateTime(new Date());
            phoneBind.setIsActive(1);
        }
        expressCenterPhoneBindDao.save(phoneBind);

        //修改这个用户的其他绑定
        ExpressCenterPhoneBind phoneBindOver=new ExpressCenterPhoneBind();
        phoneBindOver.setUserType(phoneBind.getUserType());
        phoneBindOver.setUserOutId(phoneBind.getUserOutId());
        List<ExpressCenterPhoneBind> listOver=expressCenterPhoneBindDao.findAll(Example.of(phoneBindOver));
        for(ExpressCenterPhoneBind bind:listOver){
            if(!bind.getPhone().equals(phoneBind.getPhone())){
                bind.setIsActive(0);
                expressCenterPhoneBindDao.save(bind);
            }
        }
    }

    @Override
    public ExpressCenterPhoneBind findByUserIdAnd(Integer userId, Integer isActive) {
        ExpressCenterPhoneBind phoneBindOver=new ExpressCenterPhoneBind();
        phoneBindOver.setIsActive(isActive);
        phoneBindOver.setUserOutId(userId);
        List<ExpressCenterPhoneBind> listOver=expressCenterPhoneBindDao.findAll(Example.of(phoneBindOver));
        if (listOver.size() != 0) {
            return listOver.get(0);
        }
        return null;
    }

    @Override
    public Page<ExpressCenterPhoneBind> findAll(ExpressCenterPhoneBind phoneBind, PageRequest pageRequest) {
        return expressCenterPhoneBindDao.findAll(Example.of(phoneBind),pageRequest);
    }

    @Override
    public ExpressCenterPhoneBind findOne(Integer id) {
        return expressCenterPhoneBindDao.findOne(id);
    }

    @Override
    public void delete(Integer id) {
        expressCenterPhoneBindDao.delete(id);
    }
}
