package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.Hotword;

import java.util.List;

/**
 * Created by wml on 2016/12/26.
 */
public interface HotwordService {


    List<Hotword> getHotwordsByStatus(Integer status,Integer pageNo,Integer pageSize);

}
