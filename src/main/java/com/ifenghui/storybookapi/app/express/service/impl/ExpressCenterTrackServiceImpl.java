package com.ifenghui.storybookapi.app.express.service.impl;

import com.ifenghui.storybookapi.app.express.dao.ExpressCenterOrderDao;
import com.ifenghui.storybookapi.app.express.dao.ExpressCenterTrackDao;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterOrder;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpressCenterTrackServiceImpl implements ExpressCenterTrackService {

    @Autowired
    ExpressCenterTrackDao expressCenterTrackDao;

    @Autowired
    ExpressCenterOrderDao expressCenterOrderDao;

    @Override
    public void addTrack(ExpressCenterTrack track) {
        ExpressCenterOrder order = expressCenterOrderDao.findOne(track.getCenterOrderId());
        track.setFullname(order.getFullname());
        track.setPhone(order.getPhone());
        track.setSrcType(order.getSrcType());
        track.setSrcMark(order.getSrcMark());
        track.setOrderTime(order.getOrderTime());
        expressCenterTrackDao.save(track);
        //修改order表中已发送期数,判断is_open状态
        int volOver = order.getVolOver();
        volOver = volOver + track.getVolCount();
        order.setVolOver(volOver);
        if (order.getVolOver() == order.getVolCount()){
            order.setIsOpen(0);
        }
        expressCenterOrderDao.save(order);
    }

    @Override
    public void updateTrack(Integer count,ExpressCenterTrack track) {

        //修改order表中已发送期数,判断is_open状态
        ExpressCenterOrder order = expressCenterOrderDao.findOne(track.getCenterOrderId());
        int volOver = order.getVolOver();
        volOver = volOver - count + track.getVolCount();
        order.setVolOver(volOver);
        if (order.getVolOver() == order.getVolCount()){
            order.setIsOpen(0);
        }else{
            order.setIsOpen(1);
        }
        expressCenterTrackDao.save(track);
        expressCenterOrderDao.save(order);
    }

    @Override
    public void deleteTrack(Integer id) {
        ExpressCenterTrack t = expressCenterTrackDao.getOne(id);
        int count = t.getVolCount();
        //修改订单已发期数
        ExpressCenterOrder order = expressCenterOrderDao.findOne(t.getCenterOrderId());
        int volOver = order.getVolOver();
        volOver = volOver - count;
        order.setVolOver(volOver);
        if (order.getVolOver() == order.getVolCount()){
            order.setIsOpen(0);
        }else{
            order.setIsOpen(1);
        }
        expressCenterTrackDao.delete(id);
        expressCenterOrderDao.save(order);
    }

    @Override
    public ExpressCenterTrack findOne(Integer id) {
        return expressCenterTrackDao.findOne(id);
    }

    @Override
    public ExpressCenterTrack findByYearMonth(int centerOrderId, int year, int month) {
        ExpressCenterTrack track=new ExpressCenterTrack();
        track.setCenterOrderId(centerOrderId);
        track.setYear(year);
        track.setMonth(month);
        return expressCenterTrackDao.findOne(Example.of(track));
    }

    @Override
    public List<ExpressCenterTrack> findAlByOrderId(int centerOrderId) {
        ExpressCenterTrack track=new ExpressCenterTrack();
        track.setCenterOrderId(centerOrderId);
        return expressCenterTrackDao.findAll(Example.of(track));
    }

    @Override
    public List<ExpressCenterTrack> findAlByOrderIdDesc(int centerOrderId) {
        ExpressCenterTrack track=new ExpressCenterTrack();
        track.setCenterOrderId(centerOrderId);
        Sort sort = new Sort(Sort.Direction.DESC, "month");
        return expressCenterTrackDao.findAll(Example.of(track), sort);
    }

    @Override
    public Page<ExpressCenterTrack> findAll(ExpressCenterTrack track, PageRequest pageRequest) {
        return expressCenterTrackDao.findAll(Example.of(track),pageRequest);
    }

    @Override
    public List<ExpressCenterTrack> findAllTracks(ExpressCenterTrack track) {
        return expressCenterTrackDao.findAll(Example.of(track));
    }
}
