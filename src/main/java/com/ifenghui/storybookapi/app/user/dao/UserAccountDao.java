package com.ifenghui.storybookapi.app.user.dao;

/**
 * Created by jia on 2016/12/23.
 */

import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface UserAccountDao extends JpaRepository<UserAccount, Long> {

    /**
     * userId查询UserAccount列表
     */
    @Query("from UserAccount  as u where u.userId=:userId")
    List<UserAccount> getUserAccountListByUserId(@Param("userId") Long userId);

//    @Cacheable(cacheNames = "findUserAccountsBySrcId",key = "'findUserAccountsBySrcId'+#p0")
    List<UserAccount> findUserAccountsBySrcId(String srcId);

    UserAccount getOneByUserId(Long userId);

    UserAccount getOneByUserIdAndSrcType(Long userId, Integer srcType);

    @CacheEvict(cacheNames = "findUserAccountsBySrcId",key = "'findUserAccountsBySrcId'+#p0.srcId")
    @Override
    UserAccount save(UserAccount userAccount);

    UserAccount findBySrcId(String srcId);
}
