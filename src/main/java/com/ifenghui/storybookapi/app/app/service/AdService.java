package com.ifenghui.storybookapi.app.app.service;

import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.entity.Ads2;
import com.ifenghui.storybookapi.app.app.response.IndexAds;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */
public interface AdService {

    /**
     * 获得首页广告列表
     * @param pageNo
     * @param pageSize
     * @param isCheckVer
     * @param channel
     * @param platform
     * @return
     */
    List<Ads> getAds(int pageNo, int pageSize,Integer isCheckVer,String channel,String platform,String ver,User user);//首页广告

    /**
     * 获取第一屏广告
     * @return
     */
    IndexAds getIndexAds();

    public Ads findOneAds(Long id);

    /**
     * 测试广告列表
     * @return
     */
    List<Ads> getTestAds();

    /**
     * 获取商城广告列表
     * @param status
     * @param adsPosition
     * @return
     */
    List<Ads> getAdsByStatusAndAdsPosition(Integer status,Integer adsPosition);

    List<Ads2> getAds2ByStatusAndAdsPosition(Integer status, Integer adsPosition);

    Ads getAdsById(Integer id);


//    后台用搜索

    /**
     * 综合搜索
     * @param ads
     * @param pageRequest
     * @return
     */
    Page<Ads> getAdsList(Ads ads, PageRequest pageRequest);

    /**
     * 删除广告
     */
    void del(Integer id);

    /**
     * 编辑广告
     */
    void  save(Ads ads);
}
