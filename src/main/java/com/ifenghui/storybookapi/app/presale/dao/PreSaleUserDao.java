package com.ifenghui.storybookapi.app.presale.dao;

import com.ifenghui.storybookapi.app.presale.entity.PreSaleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PreSaleUserDao extends JpaRepository<PreSaleUser, Integer> {
    @Query("select user from PreSaleUser  as user where user.unionId=:unionid")
    PreSaleUser getUserByUnionid(@Param("unionid") String unionid);
}
