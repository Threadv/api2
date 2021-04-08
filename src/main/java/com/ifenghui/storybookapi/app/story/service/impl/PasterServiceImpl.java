package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.PasterDao;
import com.ifenghui.storybookapi.app.story.entity.Paster;
import com.ifenghui.storybookapi.app.story.service.PasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasterServiceImpl implements PasterService {

    @Autowired
    PasterDao pasterDao;

    @Override
    public List<Paster> getAllPasterList() {
        Paster paster = new Paster();
        paster.setStatus(1);
        List<Paster> pasterList = pasterDao.findAll(Example.of(paster),new Sort(Sort.Direction.DESC,"orderBy","id"));
        return pasterList;
    }

    @Override
    public Page<Paster> getAllPasterPage(Integer pageSize, Integer pageNo) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy"));
        Paster paster = new Paster();
        paster.setStatus(1);
        Page<Paster> pasterPage = pasterDao.findAll(Example.of(paster),pageable);
        return pasterPage;
    }
}
