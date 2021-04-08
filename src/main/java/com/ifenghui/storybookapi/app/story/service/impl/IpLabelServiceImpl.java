package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.IpLabelDao;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IpLabelServiceImpl implements IpLabelService {

    @Autowired
    IpLabelDao ipLabelDao;

    @Override
    public IpLabel addIpLabel(IpLabel ipLabel) {
        return ipLabelDao.save(ipLabel);
    }

    @Override
    public IpLabel updateIpLabel(IpLabel ipLabel) {
        return ipLabelDao.save(ipLabel);
    }

    @Override
    public void deleteIpLabel(Integer id) {
        ipLabelDao.delete(id);


    }

    @Override
    public IpLabel findOne(Integer id) {
        return ipLabelDao.findOne(id);
    }

    @Override
    public List<IpLabel> getIpLabelListByIpId(Integer ipId) {
        List<IpLabel> ipLabelList = ipLabelDao.getIpLabelsByParentIdAndIpId(0, ipId);
        if(ipLabelList != null && ipLabelList.size() > 0){
            for(IpLabel item : ipLabelList) {
                List<IpLabel> newIpLabelList = new ArrayList<>();
                List<IpLabel> sonOfIpLabel = ipLabelDao.getIpLabelsByParentIdAndIpId(item.getId(), ipId);
                for(IpLabel son : sonOfIpLabel) {
                    son.setColor(item.getColor());
                    newIpLabelList.add(son);
                }
                item.setIpLabelList(newIpLabelList);
            }
        }
        return ipLabelList;
    }


    @Override
    public List<IpLabel> getIpLabelListByIpBrandId(Integer ipBrandId) {
        return ipLabelDao.getIpLableByIpBrandId(ipBrandId);
    }
}
