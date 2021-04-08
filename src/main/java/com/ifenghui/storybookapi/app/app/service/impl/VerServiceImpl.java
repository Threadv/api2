package com.ifenghui.storybookapi.app.app.service.impl;

import com.ifenghui.storybookapi.app.app.entity.Channel;
import com.ifenghui.storybookapi.app.app.entity.Config;
import com.ifenghui.storybookapi.app.app.entity.Ver;
import com.ifenghui.storybookapi.app.app.entity.VerChannel;
import com.ifenghui.storybookapi.app.app.dao.ChannelDao;
import com.ifenghui.storybookapi.app.app.dao.VerChannelDao;
import com.ifenghui.storybookapi.app.app.dao.VerDao;
import com.ifenghui.storybookapi.app.app.service.ConfigService;
import com.ifenghui.storybookapi.app.app.service.VerService;
import com.ifenghui.storybookapi.util.VersionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by wml on 2016/12/23.
 */
//@Transactional
@Component
public class VerServiceImpl implements VerService{

    @Autowired
    VerDao verDao;

    @Autowired
    ChannelDao channelDao;

    @Autowired
    VerChannelDao verChannelDao;

    @Autowired
    ConfigService configService;

//    @Transactional
    @Override
    public Ver getVer(String channelName, String ver, Integer type, Integer appId) {
        if(type == null){
            type = 1;//安卓
        }
        //通过渠道名称获取渠道
        Channel channel= channelDao.findOneByName(channelName);
        if(channel==null){
            return null;
        }

        //通过渠道id获取关联最新版本信息
        Integer pageNo = 0;
        Integer pageSize = 10;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));

//        VerChannel verChannelFind = new VerChannel();
//        verChannelFind.setChannelId(channel.getId());
//        verChannelFind.setAppId(appId);

//        Page<VerChannel> verChannels=this.verChannelDao.getVerChannelByChannelId(channel.getId(),pageable);
        Page<VerChannel> verChannels=this.verChannelDao.getVerChannelByChannelIdAndAppId(channel.getId(),appId,pageable);
//        Page<VerChannel> verChannels= verChannelDao.findAll(Example.of(verChannelFind),pageable);

        Ver verRow =new Ver();
        for (VerChannel item:verChannels.getContent()){
            verRow = item.getVer();
            verRow.setIsUper(0);
            if(verRow == null){
                continue;
            }
            if(item.getVer().getStatus().equals(0)){
                verRow = null;
            } else {
                //比较版本大小，先分割字符串，逐一比较
                if(VersionUtil.compare(verRow.getVer(),ver)==-1){
//                if(ver.compareTo(verRow.getVer())<=0){
                    //当前版本小于等于更新版本，则返回数据
                    verRow.setChannelName(channel.getName());
                    verRow.setUrl(item.getUrl());
                    verRow.setIsUper(1);
                    break;
                } else{
                    verRow=null;
                }
            }

        }
        //此时verRow是当前渠道最新一个版本信息
        //如果verRow!=null ,且不强制更新，则判断大于ver的所有版本是否有强制更新版本，有则给最后一条verRow,setIsUpdate=1
        if(verRow == null){
            return null;
        }
        verRow.setIsUpdate(0);
        //判断版本是否测试（0开头）

        //查询最低强制版本号
        String key = "";
        if(type == 1){
            key = "android_up_ver";
        }else{
            key = "ios_up_ver";
        }
        if(ver.compareTo("1.0.0")<0){
            //测试版
            key = key + "_test";
        }
        Config config = configService.getConfigByKey(key);
        if(config != null){
            if(VersionUtil.compare(config.getVal(),ver)==-1){
//            if(VersionUtil.isAllow(ver,config.getVal())==false){
                verRow.setIsUpdate(1);
            }
        }

        return verRow;
    }


}
