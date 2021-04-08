package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.Paster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
@Deprecated
public interface PasterDao extends JpaRepository<Paster, Integer>,JpaSpecificationExecutor {
}
