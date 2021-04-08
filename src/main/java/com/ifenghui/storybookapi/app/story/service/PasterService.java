package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.Paster;
import org.springframework.data.domain.Page;

import java.util.List;
@Deprecated
public interface PasterService {

    /**
     * 获得所有的paster
     * @return
     */
    List<Paster> getAllPasterList();

    /**
     * 获得所有的paster分页
     * @param pageSize
     * @param pageNo
     * @return
     */
    Page<Paster> getAllPasterPage(Integer pageSize, Integer pageNo);

}
