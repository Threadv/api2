package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.AppNavButton;
import com.ifenghui.storybookapi.app.app.response.AppNavContainStyle;

import java.util.List;

public interface AppNavButtonService {

    /**
     * 根据设备类型 获得导航栏列表
     * @param deviceType
     * @return
     */
    List<AppNavButton> getAppNavButtonListByDeviceType(Integer deviceType, Integer style);

    /**
     * 获取有主题导航栏
     * @param deviceType
     * @return
     */
    public List<AppNavContainStyle> getAppNavContainStyleList(Integer deviceType);
}
