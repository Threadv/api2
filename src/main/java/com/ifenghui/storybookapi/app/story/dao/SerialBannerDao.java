package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.SerialBanner;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Created by wml on 2016/12/23.
 */
@Transactional
public interface SerialBannerDao extends JpaRepository<SerialBanner, Integer >{

//    findBySerialId
    List<SerialBanner> findAllBySerialId(Integer serialId);

}