package com.ifenghui.storybookapi.app.studyplan.service;

import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanJoin;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

public interface WeekPlanJoinService {


    /**
     * 添加一周试用
     * @param userId
     * @param weekPlanStyle
     */
    void addOneWeekPlanByUserId(Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 修改购买宝宝会读（优能计划）的周数
     *
     * @param priceId
     */
    void updateWeekPlanJoinBuyNum(Integer orderId,Integer userId, WeekPlanStyle weekPlanStyle, Integer priceId);

    /**
     * 增加用户参加周计划记录
     *
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    WeekPlanJoin addWeekPlanJoin(Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 创建用户参加周计划记录
     *
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    WeekPlanJoin createWeekPlanJoin(Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 根据计划类型 获取用户的参加记录
     *
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    WeekPlanJoin getWeekPlanJoinByUserIdAndType(Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 通过count判断是否有参加记录
     *
     * @param userId
     * @param weekPlanStyle
     * @return
     */
    Long countWeekPlanJoinByUserIdAndType(Integer userId, WeekPlanStyle weekPlanStyle);

    /**
     * 获取所有参加周计划记录
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<WeekPlanJoin> getAllPage(int pageNo, int pageSize);

    /**
     * 有购买记录并且大于1 的
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<WeekPlanJoin> findAllBuyNumBiggerThanOne(int pageNo,int pageSize);

    /**
     * 根据截止时间获取周计划参加记录
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<WeekPlanJoin> getPageByDate(int pageNo, int pageSize, Date date);

    /**
     * 查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<WeekPlanJoin> getWeekPlanJoinByBuyNum(Integer pageNo,Integer pageSize);

    /**
     * 补充数据使用
     */
    void addBuyNum(List<WeekPlanJoin> weekPlanJoinList);

    /**
     * Vip 补数据
     * @param userList
     */
    void addBuyNumVip(List<User> userList);
    /**
     * 补充数据使用
     * @param weekPlanJoinList
     */
    void updateOrderPlanStyle(List<WeekPlanJoin> weekPlanJoinList);


    /**
     * 更改userExtendPlanchangecount
     */
    void  updateUserExtendPlanChangeCount(List<User> userList);


    /**
     * 后台用
     * @param weekPlanJoin
     * @param pageRequest
     * @return
     */
    public Page<WeekPlanJoin> findAll(WeekPlanJoin weekPlanJoin, PageRequest pageRequest);

    public WeekPlanJoin update(WeekPlanJoin weekPlanJoin);
}
