package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.IpLabel;

import java.util.List;

public interface IpLabelService {

    IpLabel addIpLabel(IpLabel ipLabel);

    IpLabel updateIpLabel(IpLabel ipLabel);


    void deleteIpLabel(Integer id);

    IpLabel findOne(Integer id);

    List<IpLabel> getIpLabelListByIpId(Integer ipId);

    List<IpLabel> getIpLabelListByIpBrandId(Integer ipBrandId);

}
