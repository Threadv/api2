package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.dao.DisplayGroupDao;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.app.service.DisplayGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
@Component
public class DisplayGroupServiceImpl implements DisplayGroupService {


    @Autowired
    DisplayGroupDao displayGroupDao;

    @Transactional
    @Override
    public List<DisplayGroup> getGroups() {

        //获取广告数据
        Integer status = 1;
        Integer pageNo = 0;
        Integer pageSize = 20;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        List<DisplayGroup> groups=displayGroupDao.getDisplayGroupsByStatus(status,pageable);

        return groups;
    }

    @Override
    public List<DisplayGroup> getGroupsExceptTargetType(Integer targetType) {

        //获取广告数据
        Integer status = 1;
        Integer pageNo = 0;
        Integer pageSize = 20;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        Page<DisplayGroup> groupPage=displayGroupDao.getGroupsNoTargetType(targetType,status,pageable);

        return groupPage.getContent();
    }
    @Override
    public List<DisplayGroup> getGroupsByTargetType(Integer targetType) {

        //获取广告数据
        Integer status = 1;

        List<DisplayGroup> groups=displayGroupDao.getDisplayGroupsByTargetTypeAndStatus(targetType,status);

        return groups;
    }

    @Override
    public List<DisplayGroup> getAllGroupsByTargetType(Integer targetType) {
        List<DisplayGroup> groups=displayGroupDao.getDisplayGroupsByTargetType(targetType);

        return groups;
    }

    @Override
    public DisplayGroup getGroupById(Long id) {
        DisplayGroup group=displayGroupDao.findOne(id);

        return group;
    }

    @Override
    public Page<DisplayGroup> getAllGroups(DisplayGroup displayGroup, PageRequest pageRequest) {
        return displayGroupDao.findAll(Example.of(displayGroup),pageRequest);
    }

    @Override
    public DisplayGroup findOne(Integer id) {
        return displayGroupDao.findOne(id.longValue());
    }

    @Override
    public DisplayGroup update(DisplayGroup displayGroup) {
        return displayGroupDao.save(displayGroup);
    }

    @Override
    public DisplayGroup add(DisplayGroup displayGroup) {
        return displayGroupDao.save(displayGroup);
    }

    @Override
    public void delete(DisplayGroup displayGroup) {
        displayGroupDao.delete(displayGroup);
    }
}
