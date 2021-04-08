package com.ifenghui.storybookapi.app.app.service.impl;
import com.ifenghui.storybookapi.app.app.dao.ConfigDao;
import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.config.StoryConfig;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by wml on 2016/12/23.
 */

@Component
public class ConfigServiceImpl implements ConfigService {

    Logger logger= Logger.getLogger(ConfigServiceImpl.class);

    @Autowired
    ConfigDao configDao;
//    @Transactional
    @Override
    public Config getConfigByKey(String key) {
        //通过渠道名称获取渠道
        Config config = configDao.findOneByKey(key);
        logger.info("getConfigByKey:"+key);
//        List<Config> configList= configDao.getConfigByKey(key);
////        Config config= configDao.findOneByIntro(key);
//         config = new Config();
//        for (Config item:configList){
//            config = item;
//        }

        return config;
    }

    @Override
    public boolean isIosReview(HttpServletRequest request) {
        if(VersionUtil.getPlatform(request)!= StoryConfig.Platfrom.IOS){
            return false;
        }
        Config config = this.getConfigByKey("version");
        if(!config.getVal().equals(VersionUtil.getVerionInfo(request))){
            return false;
        }

        return true;
    }

    @Override
    public Page<Config> findAll(Config config, Pageable pageable) {
        return configDao.findAll(Example.of(config),pageable);
    }

    @Override
    public Config findOne(Integer id) {
        return configDao.findOne(id.longValue());
    }

    @Override
    public void save(Config config) {
        configDao.save(config);
    }

    @Override
    public void del(Integer id) {
        configDao.delete(id.longValue());
    }
}
