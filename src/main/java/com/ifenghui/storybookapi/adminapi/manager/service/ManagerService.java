package com.ifenghui.storybookapi.adminapi.manager.service;


import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import com.ifenghui.storybookapi.style.PublishType;
import org.springframework.data.domain.Page;

public interface ManagerService {

    Page<Manager> findAll(int pageNo, int pageSize);

    Manager save(Manager manager);

    Manager findOne(Integer id);

    Manager findByUserAndPwd(String username, String password);

    Page<Manager> findByPublishType(PublishType publishType, int pageNo, int pageSize);

     boolean checkRole(Manager manager,String uri);
}
