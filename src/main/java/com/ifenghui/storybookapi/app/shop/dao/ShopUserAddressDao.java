package com.ifenghui.storybookapi.app.shop.dao;

import com.ifenghui.storybookapi.app.shop.entity.ShopUserAddress;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface ShopUserAddressDao extends JpaRepository<ShopUserAddress, Integer> {


    @Query("select s from ShopUserAddress s where s.userId=:userId")
    List<ShopUserAddress> getLastestUserAddress(
            @Param("userId") Integer userId,
            Sort sort
    );

    @Query("from ShopUserAddress  as address where address.userId =:userId")
    List<ShopUserAddress> findAddressListByUserId(@Param("userId") Integer userId);

    @Query("select  address from ShopUserAddress  as address where address.userId =:userId and address.id =:id")
    ShopUserAddress findOneByUserIdAndId(@Param("id") Integer id, @Param("userId") Integer userId);
}
