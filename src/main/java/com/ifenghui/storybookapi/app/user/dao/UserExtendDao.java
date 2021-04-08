package com.ifenghui.storybookapi.app.user.dao;

import com.ifenghui.storybookapi.app.user.entity.UserExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserExtendDao extends JpaRepository<UserExtend, Integer> {


    @Cacheable(cacheNames = "getUserExtendByUserId",key = "'getUserExtendByUserId'+#p0")
    @Query("select extend from UserExtend as extend where extend.userId = :userId")
    UserExtend getUserExtendByUserId(@Param("userId") Integer userId);

    @Query("select extend from UserExtend as extend where extend.userParentId=:userParentId")
    Page<UserExtend> getUserExtendsByUserParentId(@Param("userParentId") Integer userParentId, Pageable pageable);

    @Query("select count(u) from UserExtend as u where u.userParentId=:userParentId")
    Integer getCountUserExtendByUserParentId(@Param("userParentId") Integer userParentId);

    @Override
    @CacheEvict(cacheNames = "getUserExtendByUserId",key = "'getUserExtendByUserId'+#p0.userId")
    UserExtend save(UserExtend userExtend);
}
