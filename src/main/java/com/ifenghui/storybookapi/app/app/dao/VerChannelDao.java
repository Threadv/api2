package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.VerChannel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
public interface VerChannelDao extends JpaRepository<VerChannel, Long> {

    Page<VerChannel> getVerChannelByChannelId(Long channelId, Pageable pageable);

    @Query("from VerChannel vc where vc.channelId =:channelId  and vc.appId=:appId ")
    Page<VerChannel> getVerChannelByChannelIdAndAppId(@Param("channelId") Long channelId,@Param("appId") Integer appId, Pageable pageable);
//        @Query("from Hotword h where h.status=1")
//        List<Hotword> getHotwordsHasPublish();
}