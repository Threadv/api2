package com.ifenghui.storybookapi.adminapi.controlleradmin.resp;

import com.ifenghui.storybookapi.api.response.base.ApiPageResponse;
import com.ifenghui.storybookapi.app.story.entity.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台使用ip专区维护,2.12版本
 */
public class GetIpBrandsResponse extends ApiPageResponse {

    List<IpBrand> ipBrands;

    public List<IpBrand> getIpBrands() {
        return ipBrands;
    }

    public void setIpBrands(List<IpBrand> ipBrands) {
        this.ipBrands = ipBrands;
    }
}
