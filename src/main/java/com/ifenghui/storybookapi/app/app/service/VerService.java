package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.Ver;

/**
 * Created by wml on 2016/12/26.
 */
public interface VerService {


    Ver getVer(String channelName, String ver, Integer type, Integer appId);

}
