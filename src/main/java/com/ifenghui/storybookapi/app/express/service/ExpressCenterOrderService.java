package com.ifenghui.storybookapi.app.express.service;


import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 物流中心订单
 */
public interface ExpressCenterOrderService {

    /**
     * 增加订单
     * @param expressCenterOrder
     * @return
     */
    public ExpressCenterOrder addExpressCenterOrder(ExpressCenterOrder expressCenterOrder);


    /**
     * 通过宝宝会读订单导入物流信息
     * @param abilityPlanOrder
     * @return
     */
    public ExpressCenterOrder addExpressCenterOrderByAbilityPlanOrder(AbilityPlanOrder abilityPlanOrder);

    /**
     * 修改物流订单
     * @param expressCenterOrder
     * @return
     */
    public ExpressCenterOrder updateExpressCenterOrder(ExpressCenterOrder expressCenterOrder);

    /**
     * 修改物流订单
     * @param id
     * @return
     */
    public ExpressCenterOrder findOne(Integer id);

    /**
     * 删除物流订单
     * @param id
     */
    public void deleteExpressCenterOrder(Integer id);

    /**
     * 通过手机号绑定id查询
     * @param phoneBindId
     * @return
     */
//    public Page<ExpressCenterOrder> findByPhoneBindId(Integer phoneBindId,PageRequest pageRequest);
    public Page<ExpressCenterOrder> findOByPhoneBindId(Integer phoneBindId,Pageable pageable);

    /**
     * 通过手机号查询分页
     * @param phone
     * @return
     */
    public Page<ExpressCenterOrder> findByPhone(String phone,Pageable pageable);

    /**
     * 通过手机号查询
     * @param phone
     * @return
     */
    public List<ExpressCenterOrder> findOrdersByPhone(String phone);
    /**
     * 查询所有订单，后台使用
     * @param expressCenterOrder
     * @return Page<ExpressCenterOrder>
     */
    public Page<ExpressCenterOrder> findAll(ExpressCenterOrder expressCenterOrder,PageRequest pageRequest);

    /**
     * 查询所有订单，后台使用
     * @param expressCenterOrder
     * @return List<ExpressCenterOrder>
     */
    public List<ExpressCenterOrder> findAllOrders(ExpressCenterOrder expressCenterOrder);

}
