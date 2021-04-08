package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.Help;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by wang
 */

public interface HelpService {

    Help findOne(long id);

    Help save(Help question);

    void del(Long id);

    List<Help> findAll();

    Page<Help> findAllByPage(Integer pageNo, Integer pageSize);

}
