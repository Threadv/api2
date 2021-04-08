package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.config.StoryConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wml on 2016/12/26.
 */
public interface ConfigService {

    Config getConfigByKey(String key);

    /**
     * 是否是审核中的ios
     * @return
     */
    boolean isIosReview(HttpServletRequest request);

    Page<Config> findAll(Config config, Pageable pageable);

    Config findOne(Integer id);

    void save(Config config);

    void  del(Integer id);

}
