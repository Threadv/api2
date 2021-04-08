package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Date: 2018/11/8 19:12
 * @Description:
 */
public interface AbilityPlanOrderDao extends JpaRepository<AbilityPlanOrder, Integer>,JpaSpecificationExecutor {

    List<AbilityPlanOrder> findAbilityPlanOrdersByUserIdAndStatusAndPlaneType(Integer userId,Integer status,Integer planType);

    List<AbilityPlanOrder> findAbilityPlanOrdersByUserIdAndPlaneType(Integer userId,Integer planType);

    List<AbilityPlanOrder> findAbilityPlanOrdersByUserIdAndPriceIdAndStatus(@Param("userId")Integer userId,@Param("priceId")Integer priceId,@Param("status")Integer status);

    @Query("select o from AbilityPlanOrder  o where o.userId =:userId and o.isDel = 0 and o.status =:status")
    List<AbilityPlanOrder> getAbilityPlanOrdersByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);


    @Query("select o from AbilityPlanOrder  o where o.userId =:userId and o.isDel = 0 and o.status =:status and o.planeType =:planType")
    List<AbilityPlanOrder> getAbilityPlanOrdersByUserIdAndPlanType(@Param("userId") Integer userId, @Param("planType")Integer planType, @Param("status")Integer status, Sort sort);

    @Query("from AbilityPlanOrder  o where o.type =:payType and o.isDel = 0 and o.status =:status")
    Page<AbilityPlanOrder> getAbilityPlanOrdersByTypeAndStatus(@Param("payType") Integer payType,@Param("status") Integer status, Pageable pageable);

}
