package com.ifenghui.storybookapi.adminapi.manager.service.impl;


import com.ifenghui.storybookapi.adminapi.manager.dao.ManagerDao;
import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import com.ifenghui.storybookapi.adminapi.manager.service.ManagerService;
import com.ifenghui.storybookapi.style.PublishType;
import com.ifenghui.storybookapi.style.RoleStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    ManagerDao managerDao;


    @Override
    public Page<Manager> findAll(int pageNo, int pageSize) {
        Pageable pageable=new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return managerDao.findAll(pageable);
    }

    @Override
    public Manager save(Manager manager) {
        return managerDao.save(manager);
    }

    @Override
    public Manager findOne(Integer id) {
        return managerDao.findOne(id);
    }

    @Override
    public Manager findByUserAndPwd(String username, String password) {
        return managerDao.findOneByUsernameAndPassword(username,password);
    }

    @Override
    public Page<Manager> findByPublishType(PublishType publishType, int pageNo, int pageSize) {
        Pageable pageable=new PageRequest(pageNo,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return managerDao.findAllByPublishType(publishType.getId(),pageable);
    }

    @Override
    public boolean checkRole(Manager manager,String uri){
        //先验证禁用权限

        if(RoleStyle.hasOperationUser(manager.getRoles().split(","))==false){
            //如果没有操作权限
            boolean flag=checkPath(uri,RoleStyle.OPERATIONUSER.getPaths());
            if(flag){
//                无此权限
                return false;
            }
        }

        for(RoleStyle roleStyle:RoleStyle.values()){
//            System.out.println(roleStyle.toString()+" :in:"+RoleStyle.values().length);

            //新版本权限匹配2019-3 不再使用映射，改用自己维护枚举的方式，主要用于多服务权限控制
            for(String roleKey:manager.getRoles().split(",")){
                String key=roleStyle.toString().toLowerCase();
                //用户的权限
                String key2=roleKey.toLowerCase().trim();
                if(key.equals(key2)){
//                    匹配权限
                    for(String path:roleStyle.getPaths()){
                        if(uri.startsWith(path)){
                            return true;
                        }
                    }

                }
                //用户操作权限

            }

        }
        //没有匹配权限
        return false;
        //没有设置权限是否允许访问
//        return true;
    }
    private boolean checkPath(String uri,String[] paths){
        for(String path:paths){
            //链接匹配上，通过
            if(uri.startsWith(path)){
                return true;
            }
        }
        return false;
    }

}
