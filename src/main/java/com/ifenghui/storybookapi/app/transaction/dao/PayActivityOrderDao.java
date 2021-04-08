package com.ifenghui.storybookapi.app.transaction.dao;

import com.ifenghui.storybookapi.app.transaction.entity.OrderPayActivity;

import org.springframework.data.jpa.repository.JpaRepository;


import javax.transaction.Transactional;



@Transactional
public interface PayActivityOrderDao extends JpaRepository<OrderPayActivity, Integer> {



}