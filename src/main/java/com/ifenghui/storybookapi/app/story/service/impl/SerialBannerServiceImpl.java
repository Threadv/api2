package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.SerialBannerDao;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialBanner;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.response.SerialStoryGroup;
import com.ifenghui.storybookapi.app.story.service.SerialBannerService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.story.service.VipSerialGetRecordService;
import com.ifenghui.storybookapi.app.transaction.dao.BuySerialStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.entity.serial.BuySerialStoryRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.SerialStoryStyle;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wslhk on 2017/1/5.
 */
@Component
public class SerialBannerServiceImpl implements SerialBannerService {

    @Autowired
    SerialBannerDao serialBannerDao;


    @Override
    public Page<SerialBanner> findAll(SerialBanner serialBanner, PageRequest pageRequest) {
        return serialBannerDao.findAll(Example.of(serialBanner),pageRequest);
    }

    @Override
    public SerialBanner addSerialBanner(SerialBanner serialBanner) {
        return serialBannerDao.save(serialBanner);
    }

    @Override
    public SerialBanner updateSerialBanner(SerialBanner serialBanner) {
        return serialBannerDao.save(serialBanner);
    }

    @Override
    public void deleteSerialBanner(Integer id) {
        serialBannerDao.delete(id);
    }

    @Override
    public List<SerialBanner> findBySerialId(Integer serialId) {
        return serialBannerDao.findAllBySerialId(serialId);
    }

    @Override
    public SerialBanner findOne(int id) {
        return serialBannerDao.findOne(id);
    }
}
