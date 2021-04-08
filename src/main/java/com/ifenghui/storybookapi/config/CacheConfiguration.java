package com.ifenghui.storybookapi.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by wslhk on 2016/12/19.
 */
//@Configuration
// 标注启动了缓存
//@EnableCaching
public class CacheConfiguration {

    /*
       * ehcache 主要的管理器
       */
//    @Bean(name = "cmsCache")
    public EhCacheCacheManager ehCacheCacheManager(){
        return  null;
//        return new EhCacheCacheManager (ehCacheManagerFactoryBean().getObject ());
    }


//    @Bean("ehCacheManagerFactoryBean")
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared (true);
        return cacheManagerFactoryBean;
    }
}
