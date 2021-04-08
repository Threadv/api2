package com.ifenghui.storybookapi.app.app.service.impl;
import com.ifenghui.storybookapi.app.app.entity.Hotword;
import com.ifenghui.storybookapi.app.app.dao.HotwordDao;
import com.ifenghui.storybookapi.app.app.service.HotwordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
@Component
public class HotwordServiceImpl implements HotwordService{

    @Autowired
    HotwordDao hotwordDao;

    @Transactional
    @Override
    public List<Hotword> getHotwordsByStatus(Integer status,Integer pageNo,Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        List<Hotword> hotwords=this.hotwordDao.getHotwordsByStatus(status,pageable);
        return hotwords;
    }


}
