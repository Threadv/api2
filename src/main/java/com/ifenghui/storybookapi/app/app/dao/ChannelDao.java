package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
//@Transactional
public interface ChannelDao extends JpaRepository<Channel, Long> {

    Channel findOneByName(String name);
//        @Query("from Hotword h where h.status=1")
//        List<Hotword> getHotwordsHasPublish();
}