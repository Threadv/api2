package com.ifenghui.storybookapi.app.express.service.impl;

import com.ifenghui.storybookapi.app.express.dao.ExpressCenterOrderDao;
import com.ifenghui.storybookapi.app.express.dao.ExpressCenterTrackDao;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterPhoneBind;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterOrderService;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterPhoneBindService;
import com.ifenghui.storybookapi.app.shop.entity.ShopExpress;
import com.ifenghui.storybookapi.app.shop.service.ShopExpressService;
import com.ifenghui.storybookapi.app.transaction.entity.abilityplan.AbilityPlanOrder;
import com.ifenghui.storybookapi.exception.ApiDuplicateException;
import com.ifenghui.storybookapi.style.ExpressSrcStyle;
import com.ifenghui.storybookapi.style.OrderStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpressCenterOrderServiceImpl implements ExpressCenterOrderService {

    @Autowired
    ExpressCenterOrderDao expressCenterOrderDao;

    @Autowired
    ExpressCenterTrackDao expressCenterTrackDao;

    @Autowired
    ExpressCenterPhoneBindService expressCenterPhoneBindService;

    @Autowired
    ShopExpressService shopExpressService;
    @Override
    public ExpressCenterOrder addExpressCenterOrder(ExpressCenterOrder expressCenterOrder) {

        //通过外部订单号判断是否重复,重复抛异常
        Integer count=expressCenterOrderDao.countBySrcTypeAndOutOrderId(expressCenterOrder.getSrcType(),expressCenterOrder.getOutOrderId());
        if(count>0){
            throw new ApiDuplicateException("订单外部id重复,"+expressCenterOrder.getSrcType()+"_"+expressCenterOrder.getOutOrderId());
        }
        return expressCenterOrderDao.save(expressCenterOrder);
    }

    @Override
    public ExpressCenterOrder addExpressCenterOrderByAbilityPlanOrder(AbilityPlanOrder abilityPlanOrder) {
        //1查订单状态
        if(abilityPlanOrder.getStatus()!=1){
            return null;
        }

        //2 查物流信息
        ShopExpress shopExpress= shopExpressService.findShopExpressByOrderId(abilityPlanOrder.getId(), OrderStyle.ABILITY_PLAN_ORDER);

        //3 判断是否已经增加
        ExpressCenterOrder expressCenterOrder=this.findOneBySrcStyleAndOutId(ExpressSrcStyle.APP,abilityPlanOrder.getId()+"");
        if(expressCenterOrder!=null){
            return null;

        }

        //4 增加物流信息
        expressCenterOrder=new ExpressCenterOrder(abilityPlanOrder,shopExpress);
        return null;
    }

    private ExpressCenterOrder findOneBySrcStyleAndOutId(ExpressSrcStyle srcStyle,String outOrderId){
        ExpressCenterOrder expressCenterOrder=new ExpressCenterOrder();
        expressCenterOrder.setSrcStryle(srcStyle);
        expressCenterOrder.setOutOrderId(outOrderId);
        expressCenterOrder=expressCenterOrderDao.findOne(Example.of(expressCenterOrder));
        return expressCenterOrder;
    }

    @Override
    public ExpressCenterOrder updateExpressCenterOrder(ExpressCenterOrder expressCenterOrder) {
         expressCenterOrderDao.save(expressCenterOrder);
        //修改该订单下物流信息
        List<ExpressCenterTrack> tracks = expressCenterTrackDao.findTracksByOrderId(expressCenterOrder.getId());
        for (ExpressCenterTrack track:tracks){
            track.setFullname(expressCenterOrder.getFullname());
            track.setPhone(expressCenterOrder.getPhone());
            track.setSrcType(expressCenterOrder.getSrcType());
            track.setSrcMark(expressCenterOrder.getSrcMark());
            track.setOrderTime(expressCenterOrder.getOrderTime());
            expressCenterTrackDao.save(track);
        }
        return expressCenterOrder;
    }

    @Override
    public ExpressCenterOrder findOne(Integer id) {
        return expressCenterOrderDao.findOne(id);
    }

    @Override
    public void deleteExpressCenterOrder(Integer id) {
        expressCenterOrderDao.delete(id);
    }

//    @Override
//    public Page<ExpressCenterOrder> findByPhoneBindId(Integer phoneBindId,PageRequest pageRequest) {
//        ExpressCenterPhoneBind phoneBind= expressCenterPhoneBindService.findOne(phoneBindId);
//        return expressCenterOrderDao.findAllByPhone(phoneBind.getPhone(),pageRequest);
//    }

    @Override
    public Page<ExpressCenterOrder> findOByPhoneBindId(Integer phoneBindId, Pageable pageable) {
        ExpressCenterPhoneBind phoneBind= expressCenterPhoneBindService.findOne(phoneBindId);
        return expressCenterOrderDao.findOrdersByPhone(phoneBind.getPhone(),pageable);
    }

    @Override
    public Page<ExpressCenterOrder> findByPhone(String phone, Pageable pageable) {
        return expressCenterOrderDao.findOrdersByPhone(phone,pageable);
    }

    @Override
    public List<ExpressCenterOrder> findOrdersByPhone(String phone) {
        return expressCenterOrderDao.findByPhone(phone);
    }

    @Override
    public Page<ExpressCenterOrder> findAll(ExpressCenterOrder expressCenterOrder,PageRequest pageRequest) {
        return expressCenterOrderDao.findAll(Example.of(expressCenterOrder),pageRequest);
    }

    @Override
    public List<ExpressCenterOrder> findAllOrders(ExpressCenterOrder expressCenterOrder) {
        return expressCenterOrderDao.findAll(Example.of(expressCenterOrder));
    }
}
