package com.ifenghui.storybookapi.app.app.service.impl;

/**
 * Created by wang
 */

import com.ifenghui.storybookapi.app.app.dao.HelpDao;
import com.ifenghui.storybookapi.app.app.entity.Help;
import com.ifenghui.storybookapi.app.app.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class HelpServiceImpl implements HelpService {
    @Autowired
    HelpDao questionDao;


    @Override
    public Help findOne(long id) {
        return questionDao.findOne(id);
    }

    @Override
    public Help save(Help question) {
        return questionDao.save(question);
    }

    @Override
    public void del(Long id) {
        questionDao.delete(id);
    }

    @Override
    public List<Help> findAll() {
        return questionDao.findAll(new Sort(Sort.Direction.ASC,"position"));
    }

    @Override
    public Page<Help> findAllByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"position"));
        return questionDao.findAll(pageable);
    }
}
