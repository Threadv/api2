package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.dao.AppNavButtonDao;
import com.ifenghui.storybookapi.app.app.entity.AppNavButton;
import com.ifenghui.storybookapi.app.app.response.AppNavContainStyle;
import com.ifenghui.storybookapi.app.app.service.AppNavButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class AppNavButtonServiceImpl implements AppNavButtonService {

    @Autowired
    AppNavButtonDao appNavButtonDao;

    @Override
    public List<AppNavButton> getAppNavButtonListByDeviceType(Integer deviceType, Integer style) {
        Pageable pageable = new PageRequest(0, 100, new Sort(Sort.Direction.DESC,"orderBy","id"));
        Page<AppNavButton> appNavButtonPage = appNavButtonDao.getAppNavButtonsByStatusAndDeviceTypeAndStyle(1,deviceType, style, pageable);
        return new ArrayList<>(appNavButtonPage.getContent());
    }

    @Override
    public List<AppNavContainStyle> getAppNavContainStyleList(Integer deviceType) {
        List<AppNavContainStyle> appNavContainStyleList = new ArrayList<>();
        List<AppNavButton> blackButtonList = this.getAppNavButtonListByDeviceType(deviceType, 1);
        List<AppNavButton> lightButtonList = this.getAppNavButtonListByDeviceType(deviceType, 2);
        appNavContainStyleList.add(new AppNavContainStyle(blackButtonList));
        appNavContainStyleList.add(new AppNavContainStyle(lightButtonList));
        return appNavContainStyleList;
    }

}
