package com.ifenghui.storybookapi.app.express.service;


import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface ExpressCenterMagService {


    /**
     * 创建物流杂志
     * @param mag
     * @return
     */
    ExpressCenterMag addMag(ExpressCenterMag mag);


    /**
     * 修改物流杂志
     * @param mag
     * @return
     */
    ExpressCenterMag updateMag(ExpressCenterMag mag);


    /**
     * 删除杂志
     * @param id
     * @return
     */
    void deleteMag(Integer id);


    ExpressCenterMag findOne(Integer id);

    /**
     * 通过时间返回杂志列表
     * @param year
     * @param month
     * @return
     */
    List<ExpressCenterMag> findByYearMonth(int year,int month);


    Page<ExpressCenterMag> findAll(ExpressCenterMag mag, PageRequest pageRequest);
}
