package com.ifenghui.storybookapi.app.presale.dao;


import com.ifenghui.storybookapi.app.presale.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDao extends JpaRepository<Activity, Integer> {
    Activity findOneByWxRedirectKey(String wxRedirectKey);
}
