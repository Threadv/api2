package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.style.AbilityPlanCodeStyle;
import com.ifenghui.storybookapi.style.AbilityPlanStyle;
import com.ifenghui.storybookapi.style.VipPriceStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

/**
 * @Date: 2018/11/8 19:47
 * @Description:
 */
public interface UserAbilityPlanRelateService {


    /***
     * 获得连续最早开始时间
     * @param userId
     * @param startTime
     * @return
     */
    Date getUserAbilityPlanRelateStartTime(Long userId,Date startTime);

    UserAbilityPlanRelate getUserAbilityPlanRelateByUserIdAndType(Integer userId,Integer type);

    /**
     * 更改计划类型
     * @param userId
     * @param weekPlanStyle
     */
    void updateAbilityRelateByUserId(Integer userId,WeekPlanStyle weekPlanStyle);

    /**
     * 创建用户宝宝会读（优能计划）关联
     * @param userId
     * @param abilityPlanStyle
     * @return
     */
    UserAbilityPlanRelate createUserAbilityPlanRelate(Integer userId, AbilityPlanCodeStyle abilityPlanStyle);

    /**
     * 获得最后一调记录
     * @param userId
     * @return
     */
    UserAbilityPlanRelate getLastestUserAbilityPlanRelateRecord(Long userId);

    /**
     * 添加用户宝宝会读（优能计划）关联
     * @param userId
     * @param abilityPlanStyle
     * @param startTime
     * @param endTime
     * @return
     */
    UserAbilityPlanRelate addUserAbilityPlanRelate(Integer userId, AbilityPlanStyle abilityPlanStyle, Date startTime, Date endTime);

    /**
     * 重置优宝宝会读（优能计划）
     * 定时清理的时候使用
     */
    void resetAbilityPlanUser();

    /**
     * 查找用户记录
     * @param userId
     * @return
     */
    Integer countUserAbilityPlan(Integer userId);

    /**
     * 权益记录
     * @param userAbilityPlanRelate
     * @param pageRequest
     * @return
     */
    Page<UserAbilityPlanRelate> findAll(UserAbilityPlanRelate userAbilityPlanRelate, PageRequest pageRequest);

    /**
     * 删除计划
     * @param id
     * @return
     */
    void delete(Integer id);
}
