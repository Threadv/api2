package com.ifenghui.storybookapi.app.user.service;

import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserExtendService {

    /**
     * 寻找用户拓展属性
     * @param userId
     * @return
     */
    UserExtend findUserExtendByUserId(Long userId);

    /**
     * 增加或者编辑用户识字量
     * @param userId
     * @param wordCount
     * @param vocabularyCount
     * @return
     */
    UserExtend changeUserExtend(Long userId, Integer wordCount, Integer vocabularyCount);

    /**
     * 用于初始化userextend
     * @param userId
     * @param userParentId
     * @return
     */
    UserExtend createUserExtend(Integer userId, Integer userParentId);


    /**
     * 修改用户的分享大使关系，用户第一次绑定手机
     * @param userId
     * @param userParentId
     * @return
     */
    UserExtend editParentId(Integer userId, Integer userParentId);

    /**
     * 获取某个用户分享的用户列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<UserExtend> getUsersByUserExtendParentId(Integer userId, Integer pageNo, Integer pageSize);

    /**
     * 通过userExtend列表 获得用户列表 （格式化使用）
     * @param userExtendList
     * @return
     */
    List<User> getUserListFromUserExtendList(List<UserExtend> userExtendList);

    /**
     * 改变用户关联数据
     * @param userId
     * @param cashBalance
     * @param friendTradeNum
     * @param friendTradeAmount
     * @return
     */
    UserExtend changeUserShareTradeData(Integer userId, Integer cashBalance, Integer friendTradeNum, Integer friendTradeAmount);

    /**
     * 修改用户分享朋友数量
     * @param userId
     * @return
     */
    UserExtend changeUserShareFriendNum(Integer userId);

    /**
     * 改变用户的参与周计划情况
     * @param userId
     * @param weekPlanStyle
     */
    void changeUserWeekPlanType(Integer userId, WeekPlanStyle weekPlanStyle);


    UserExtend update(UserExtend userExtend);
}
