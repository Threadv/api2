package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.app.service.TemplateNoticeService;
import com.ifenghui.storybookapi.app.social.dao.SignRecordDao;
import com.ifenghui.storybookapi.app.social.entity.SignRecord;
import com.ifenghui.storybookapi.app.social.service.SignRecordService;
import com.ifenghui.storybookapi.app.transaction.service.StarShopService;
import com.ifenghui.storybookapi.style.NoticeStyle;
import com.ifenghui.storybookapi.style.SignRecordStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Transactional
public class SignRecordServiceImpl implements SignRecordService {

    @Autowired
    SignRecordDao signRecordDao;

    @Autowired
    StarShopService starShopService;

    @Autowired
    TemplateNoticeService templateNoticeService;

    /**
     * 添加分享类型记录
     *
     * @param userId
     * @param signRecordStyle
     * @return
     */
    @Override
    public SignRecord createSignRecord(Integer userId, SignRecordStyle signRecordStyle) {

        SignRecord signRecord = this.findByUserIdAndType(userId, signRecordStyle);
        if (signRecord != null) {
            return signRecord;
        }
        //添加一条记录
        SignRecord record = this.addSignRecord(userId, signRecordStyle);
        //分享记录添加代金券
        if (signRecordStyle.getId() == SignRecordStyle.SHARE_TYPE.getId()) {
            starShopService.addExchangeRecordCoupon(userId.longValue(), "30元代金券", 8L, 1);
            /**
             * 发送有充值推送消息和赠送代金券
             */
            Map<String, String> contentMap = new HashMap<>();
            templateNoticeService.addNoticeToUserByUserIdAndMessage(NoticeStyle.SHARE, contentMap, userId);
        }

        return record;
    }

    /**
     * 查找记录
     *
     * @param userId
     * @param signRecordStyle
     * @return
     */
    @Override
    public SignRecord findByUserIdAndType(Integer userId, SignRecordStyle signRecordStyle) {

        return signRecordDao.getSignRecordByUserIdAndType(userId, signRecordStyle.getId());
    }

    /**
     * 添加一条新纪录
     *
     * @param userId
     * @param signRecordStyle
     * @return
     */
    public SignRecord addSignRecord(Integer userId, SignRecordStyle signRecordStyle) {

        SignRecord signRecord = new SignRecord();
        signRecord.setUserId(userId);
        signRecord.setType(signRecordStyle);
        signRecord.setCreateTime(new Date());
        return signRecordDao.save(signRecord);
    }

    /**
     * 判断是否有添加记录
     *
     * @param userId
     * @param signRecordStyle
     * @return
     */
    @Override
    public Long countSignRecordByUserIdAndType(Integer userId, SignRecordStyle signRecordStyle) {

        return signRecordDao.countSignRecordByUserIdAndType(userId, signRecordStyle.getId());
    }
}
