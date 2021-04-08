package com.ifenghui.storybookapi.app.app.dao;


import com.ifenghui.storybookapi.app.app.entity.AppNavButton;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppNavButtonDao extends JpaRepository<AppNavButton, Integer> {

    @Query("select nav from AppNavButton as nav where nav.status=:status and nav.deviceType=:deviceType and nav.style=:style")
    Page<AppNavButton> getAppNavButtonsByStatusAndDeviceTypeAndStyle(
        @Param("status") Integer status,
        @Param("deviceType") Integer deviceType,
        @Param("style") Integer style,
        Pageable pageable
    );

}
