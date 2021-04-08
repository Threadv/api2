package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.Ver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by wml on 2017/4/10.
 */
@Transactional
public interface VerDao extends JpaRepository<Ver, Long> {

    Page<Ver> getHotwordsByStatus(Integer status, Pageable pageable);
//        @Query("from Hotword h where h.status=1")
//        List<Hotword> getHotwordsHasPublish();
}