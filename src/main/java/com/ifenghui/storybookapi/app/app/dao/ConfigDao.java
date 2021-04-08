package com.ifenghui.storybookapi.app.app.dao;

import com.ifenghui.storybookapi.app.app.entity.Config;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by wml on 2017/4/10.
 */
public interface ConfigDao extends JpaRepository<Config, Long> {

    @Cacheable(value = "story_config",key = "'story_config'+#p0")
    Config findOneByKey(String key);


    @Query("from Config c where c.key =:key")
    List<Config> getConfigByKey(@Param("key")String key);


    @CacheEvict(value = "story_config",key = "'story_config'+#p0.key")
    @Override
    Config save(Config config);
}