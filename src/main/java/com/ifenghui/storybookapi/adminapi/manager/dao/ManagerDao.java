package com.ifenghui.storybookapi.adminapi.manager.dao;


import com.ifenghui.storybookapi.adminapi.manager.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ManagerDao extends JpaRepository<Manager, Integer>,JpaSpecificationExecutor {

    Manager findOneByUsernameAndPassword(String username, String password);

    @Query("select m from Manager m where m.publish=:publish")
    Page<Manager> findAllByPublishType(@Param("publish") Integer publish, Pageable pageable);

}
