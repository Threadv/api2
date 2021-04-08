package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Label;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
/**
 * Created by jia on 2016/12/22.
 */
@Transactional
public interface LabelDao extends JpaRepository<Label, Long> {
    @Cacheable(cacheNames = "ktx_labek_findone_",key = "'ktx_labek_findone_'+#p0")
    Label findOneLabelById(Long id);
}
