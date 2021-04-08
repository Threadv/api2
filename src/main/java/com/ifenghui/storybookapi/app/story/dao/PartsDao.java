package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wml on 2017/4/10.
 */
//@Transactional
    @Deprecated
public interface PartsDao extends JpaRepository<Parts, Long> {


}