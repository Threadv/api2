package com.ifenghui.storybookapi.active1902.service;

import com.ifenghui.storybookapi.active1902.entity.Awards;
import com.ifenghui.storybookapi.active1902.style.AwardsStyle;

import java.util.List;

/**
 * @Date: 2019/2/19 16:02
 * @Description:
 */
public interface AwardsService {

    /**
     * 根据类型获取礼品
     *
     * @param awardsStyle
     * @return
     */
     List<Awards> getAwardsByType(AwardsStyle awardsStyle, Integer scheduleId);
}
