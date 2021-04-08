package com.ifenghui.storybookapi.app.analysis.dao;

import com.ifenghui.storybookapi.app.analysis.entity.StoryHotSale;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
@Deprecated
public interface StoryHotSaleDao extends JpaRepository<StoryHotSale, Long> {


}