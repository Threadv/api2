package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.SignRecord;
import com.ifenghui.storybookapi.style.SignRecordStyle;

public interface SignRecordService {


    /**
     * 添加分享类型记录
     * @param userId
     * @param type
     * @return
     */
    SignRecord createSignRecord(Integer userId, SignRecordStyle type);

    /**
     * 查找记录
     * @param userId
     * @param signRecordStyle
     * @return
     */
    SignRecord findByUserIdAndType(Integer userId,SignRecordStyle signRecordStyle);

    /**
     * 判断是否有添加记录
     * @param userId
     * @param signRecordStyle
     * @return
     */
    Long countSignRecordByUserIdAndType(Integer userId,SignRecordStyle signRecordStyle);
}
