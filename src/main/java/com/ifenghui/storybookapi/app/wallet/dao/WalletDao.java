package com.ifenghui.storybookapi.app.wallet.dao;

import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.style.WalletStyle;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Transient;
import javax.transaction.Transactional;


/**
 * Created by wml on 2017/2/16.
 */
@Transactional
public interface WalletDao extends JpaRepository<Wallet, Long> {

    Wallet getWalletByUserId(Integer userId);

    @Query("select w from Wallet w where w.userId=:userId")
    @Cacheable(cacheNames = "getWalletByUserIdInCache",key = "'getWalletByUserIdInCache'+#p0")
    Wallet getWalletByUserIdInCache(@Param("userId") Integer userId);


    @CacheEvict(cacheNames = "getWalletByUserIdInCache",key = "'getWalletByUserIdInCache'+#p0.userId")
    @Override
    Wallet save(Wallet wallet);
}