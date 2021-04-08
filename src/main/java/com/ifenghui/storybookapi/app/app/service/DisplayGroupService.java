package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */
public interface DisplayGroupService {

    /**
     * 首页分组
     * @return
     */
    List<DisplayGroup> getGroups();

    /**
     * 根据首页分组类型获取分组去掉某个分组
     * @param targetType
     * @return
     */
    List<DisplayGroup> getGroupsExceptTargetType(Integer targetType);

    /**
     * 根据首页分组类型获取分组
     * @param targetType
     * @return
     */
    List<DisplayGroup> getGroupsByTargetType(Integer targetType);

    /**
     * 首页分组
     * @param groupId
     * @return
     */
    DisplayGroup getGroupById(Long groupId);

    List<DisplayGroup> getAllGroupsByTargetType(Integer targetType);



//    后台使用

    /**
     * 返回所有分组列表
     * 首页分组相当于是虚拟系列，可以一对多关系，不能直接购买分组
     * @param displayGroup
     * @param pageRequest
     * @return
     */
    Page<DisplayGroup> getAllGroups(DisplayGroup displayGroup, PageRequest pageRequest);

    DisplayGroup findOne(Integer id);

    DisplayGroup update(DisplayGroup displayGroup);

    /**
     * 后台创建分组，主id是直接后台填写的，不需要自动生成
     * @param displayGroup
     * @return
     */
    DisplayGroup add(DisplayGroup displayGroup);

    void delete(DisplayGroup displayGroup);


}
