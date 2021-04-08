package com.ifenghui.storybookapi.adminapi.manager.dao;


import com.ifenghui.storybookapi.adminapi.manager.entity.ManagerToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ManagerTokenDao extends JpaRepository<ManagerToken, Integer>,JpaSpecificationExecutor {

    @Cacheable(cacheNames = "manager_token",key = "'namager_token_v2_'+#p0")
    ManagerToken findOneByToken(String token);

    @Override
    @CacheEvict(cacheNames = "manager_token",key = "'namager_token_v2_'+#p0.token")
    ManagerToken save(ManagerToken managerToken);

}
