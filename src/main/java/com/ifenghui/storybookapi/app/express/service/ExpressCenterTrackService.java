package com.ifenghui.storybookapi.app.express.service;


import com.ifenghui.storybookapi.app.express.entity.ExpressCenterTrack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface ExpressCenterTrackService {


    /**
     * 创建
     * @param track
     * @return
     */
    void addTrack(ExpressCenterTrack track);


    /**
     * 修改物流
     * @param track
     * @return
     */
    void updateTrack(Integer count,ExpressCenterTrack track);


    /**
     * 删除
     * @param id
     * @return
     */
    void deleteTrack(Integer id);


    ExpressCenterTrack findOne(Integer id);

    /**
     * 一个物流一个月只会发一次
     * @param year
     * @param month
     * @return
     */
    ExpressCenterTrack findByYearMonth(int centerOrderId,int year, int month);


    /**
     * 返回订单所有物流
     * @param centerOrderId
     * @return
     */
    List<ExpressCenterTrack> findAlByOrderId(int centerOrderId);

    /**
     * 返回订单所有物流月份倒叙
     * @param centerOrderId
     * @return
     */
    List<ExpressCenterTrack> findAlByOrderIdDesc(int centerOrderId);

    /**
     * 后台用组合查询
     * @param track
     * @param pageRequest
     * @return
     */
    Page<ExpressCenterTrack> findAll(ExpressCenterTrack track, PageRequest pageRequest);

    /**
     * 返回订单所有物流
     * @param track
     * @return
     */
    List<ExpressCenterTrack> findAllTracks(ExpressCenterTrack track);


}
