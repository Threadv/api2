package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.dao.PhoneCheckCodeDao;
import com.ifenghui.storybookapi.app.app.entity.PhoneCheckCode;
import com.ifenghui.storybookapi.app.app.service.PhoneCheckCodeService;
import com.ifenghui.storybookapi.app.system.service.MessageService;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import com.ifenghui.storybookapi.exception.ApiNoPermissionDelException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.CheckCodeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PhoneCheckCodeServiceImpl implements PhoneCheckCodeService {

    @Autowired
    PhoneCheckCodeDao phoneCheckCodeDao;

    @Autowired
    MessageService messageService;

//    @Override
    public PhoneCheckCode addPhoneCheckCode(String content, String phone, Date endTime, CheckCodeStyle checkCodeStyle) {
        PhoneCheckCode phoneCheckCode = new PhoneCheckCode();
        phoneCheckCode.setContent(content);
        phoneCheckCode.setPhone(phone);
        phoneCheckCode.setEndTime(endTime);
        phoneCheckCode.setCreateTime(new Date());
        phoneCheckCode.setType(checkCodeStyle);
        return phoneCheckCodeDao.save(phoneCheckCode);
    }

    @Override
    public PhoneCheckCode createPhoneCheckCode(String phone, CheckCodeStyle checkCodeStyle) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,
        calendar.get(Calendar.HOUR_OF_DAY) - 1);
        Date startTime = calendar.getTime();
        Integer countCodeNumber = phoneCheckCodeDao.getCountPhoneCheckCodeByPhone(phone, startTime, now);
        if(countCodeNumber != null && countCodeNumber > 6) {
            throw new ApiDuplicateException("超过了短信发送最大限制！请稍候重试！");
        }
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"id"));
        List<PhoneCheckCode> phoneCheckCodeList = phoneCheckCodeDao.getPhoneCheckCodeListByPhone(phone, pageable);
        if(phoneCheckCodeList != null && phoneCheckCodeList.size() > 0) {
            PhoneCheckCode phoneCheckCode = phoneCheckCodeList.get(0);
            long timeGap = now.getTime() - phoneCheckCode.getCreateTime().getTime();
            if(timeGap < 60000) {
                throw new ApiDuplicateException("上次创建验证码还未超过30秒！请稍候重试！");
            }
        }

        calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        Date codeEndTime = calendar.getTime();
        String code = this.getRandString();
        PhoneCheckCode phoneCheckCode = this.addPhoneCheckCode(code, phone, codeEndTime, checkCodeStyle);
        Map<String,String> smsMap = new HashMap<>();
        smsMap.put("product", "故事飞船");
        smsMap.put("code", code);
        messageService.sendSms(phone, checkCodeStyle.getMode(), smsMap);
        return phoneCheckCode;
    }

    @Override
    public PhoneCheckCode createPhoneCheckCodeSimple(String phone, CheckCodeStyle checkCodeStyle) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,
                calendar.get(Calendar.HOUR_OF_DAY) - 1);
        Date startTime = calendar.getTime();
        Integer countCodeNumber = phoneCheckCodeDao.getCountPhoneCheckCodeByPhone(phone, startTime, now);
        if(countCodeNumber != null && countCodeNumber > 6) {
            throw new ApiDuplicateException("超过了短信发送最大限制！请稍候重试！");
        }
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"id"));
        List<PhoneCheckCode> phoneCheckCodeList = phoneCheckCodeDao.getPhoneCheckCodeListByPhone(phone, pageable);
        if(phoneCheckCodeList != null && phoneCheckCodeList.size() > 0) {
            PhoneCheckCode phoneCheckCode = phoneCheckCodeList.get(0);
            long timeGap = now.getTime() - phoneCheckCode.getCreateTime().getTime();
            if(timeGap < 60000) {
                throw new ApiDuplicateException("上次创建验证码还未超过30秒！请稍候重试！");
            }
        }

        calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        Date codeEndTime = calendar.getTime();
        String code = this.getRandString();
        PhoneCheckCode phoneCheckCode = this.addPhoneCheckCode(code, phone, codeEndTime, checkCodeStyle);
        Map<String,String> smsMap = new HashMap<>();
//        smsMap.put("product", "故事飞船");
        smsMap.put("code", code);
        messageService.sendSms(phone, checkCodeStyle.getMode(), smsMap);
        return phoneCheckCode;
    }

    @Override
    public String getRandString() {
        Integer num = (int)(Math.random()*9000)+1000;
        return num.toString();
    }

    @Override
    public Integer checkCode(String code, String phone) {
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"id"));
        List<PhoneCheckCode> phoneCheckCodeList = phoneCheckCodeDao.getPhoneCheckCodeListByPhone(phone, pageable);
        PhoneCheckCode phoneCheckCode = null;
        if(phoneCheckCodeList != null && phoneCheckCodeList.size() > 0) {
            phoneCheckCode = phoneCheckCodeList.get(0);
        }
        if(phoneCheckCode == null) {
            throw new ApiNotFoundException("验证码有误！");
        }
        if(phoneCheckCode.getContent().equals(code)) {
            if(phoneCheckCode.getEndTime().getTime() < System.currentTimeMillis()) {
                throw new ApiNoPermissionDelException("验证码已过期！");
            } else {
                return 1;
            }
        } else {
            throw new ApiNotFoundException("验证码有误！");
        }
    }

}
