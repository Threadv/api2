package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.UserAbilityPlanRelate;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @Date: 2018/11/8 19:53
 * @Description:
 */
public interface UserAbilityPlanRelateDao extends JpaRepository<UserAbilityPlanRelate, Integer> {


    List<UserAbilityPlanRelate>  findUserAbilityPlanRelatesByUserId(Integer userId,Sort sort);

    List<UserAbilityPlanRelate> findUserAbilityPlanRelatesByUserIdAndType(Integer userId,Integer type,Sort sort);
    /**
     * 返回没有过期的优能计划状态
     * @param endTime
     * @return
     */
    @Query("select s from UserAbilityPlanRelate s where s.userId=:userId and s.endTime >:endTime and (s.type = 1 or s.type = 2 or s.type = 0)")
    List<UserAbilityPlanRelate> findByUserIdAndEndTime(
            @Param("userId") Integer userId,
            @Param("endTime") Date endTime,
            Sort sort
    );

    @Query("from UserAbilityPlanRelate s where s.userId=:userId and s.type=:stype and s.endTime >:endTime")
    List<UserAbilityPlanRelate> findByUserIdAndTypeAndEndTime(@Param("userId") Integer userId,@Param("stype") Integer stype, @Param("endTime") Date endTime);


    @Cacheable(cacheNames ="countUserAbilityPlan",key = "'countUserAbilityPlan'+#p0")
    @Query("select  count(u) from UserAbilityPlanRelate u where u.userId =:userId")
    Integer countUserAbilityPlan(@Param("userId") Integer userId);

}
