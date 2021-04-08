package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.SerialBanner;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.response.SerialStoryGroup;
import com.ifenghui.storybookapi.app.transaction.entity.serial.BuySerialStoryRecord;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.SerialStoryStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by wml on 2016/12/23.
 */
public interface SerialBannerService {

    /**
     * 混合搜索
     * @param serialBanner
     * @param pageRequest
     * @return
     */
    Page<SerialBanner>  findAll(SerialBanner serialBanner,PageRequest pageRequest);


    SerialBanner addSerialBanner(SerialBanner serialBanner);

    SerialBanner updateSerialBanner(SerialBanner serialBanner);

    void deleteSerialBanner(Integer id);

    List<SerialBanner> findBySerialId(Integer serialId);


    SerialBanner findOne(int id);


}
