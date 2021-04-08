package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.adminapi.controlleradmin.resp.IpBrand;
import com.ifenghui.storybookapi.app.story.dao.IpLabelDao;
import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import com.ifenghui.storybookapi.app.story.service.IpBrandService;
import com.ifenghui.storybookapi.app.story.service.IpLabelService;
import com.ifenghui.storybookapi.style.IpBrandStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IpBrandServiceImpl implements IpBrandService {

    @Override
    public List<IpBrand> getAllIpBrand() {
        List<IpBrand> ipBrands=new ArrayList<>();
//        for(IpBrandStyle ipBrandStyle:IpBrandStyle.values()){
//            if(ipBrandStyle==IpBrandStyle.DEFAULT){
//                continue;
//            }
//            ipBrands.add(new IpBrand(ipBrandStyle));
//        }
        ipBrands.add(new IpBrand(IpBrandStyle.WULALA));
        ipBrands.add(new IpBrand(IpBrandStyle.XIYOUJI));
        ipBrands.add(new IpBrand(IpBrandStyle.CHIELD));
        return ipBrands;
    }
}
