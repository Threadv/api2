package com.ifenghui.storybookapi.app.express.service.impl;

import com.ifenghui.storybookapi.app.express.dao.ExpressCenterMagDao;
import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;
import com.ifenghui.storybookapi.app.express.service.ExpressCenterMagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpressCenterMagServiceImpl implements ExpressCenterMagService {

    @Autowired
    ExpressCenterMagDao expressCenterMagDao;

    @Override
    public ExpressCenterMag addMag(ExpressCenterMag mag) {
        return expressCenterMagDao.save(mag);
    }

    @Override
    public ExpressCenterMag updateMag(ExpressCenterMag mag) {
        return expressCenterMagDao.save(mag);
    }

    @Override
    public void deleteMag(Integer id) {
        expressCenterMagDao.delete(id);
    }

    @Override
    public ExpressCenterMag findOne(Integer id) {
        return expressCenterMagDao.findOne(id);
    }

    @Override
    public List<ExpressCenterMag> findByYearMonth(int year, int month) {
        return expressCenterMagDao.findAllByYearAndMonth(year,month,new Sort(Sort.Direction.ASC,"position"));
    }

    @Override
    public Page<ExpressCenterMag> findAll(ExpressCenterMag mag, PageRequest pageRequest) {
        return expressCenterMagDao.findAll(Example.of(mag),pageRequest);
    }
}
