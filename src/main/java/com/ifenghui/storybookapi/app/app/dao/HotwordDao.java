package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.Hotword;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wml on 2016/12/26.
 */
@Transactional
public interface HotwordDao extends JpaRepository<Hotword, Long> {

    List<Hotword> getHotwordsByStatus(Integer status,Pageable pageable);
//        @Query("from Hotword h where h.status=1")
//        List<Hotword> getHotwordsHasPublish();
}