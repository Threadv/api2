package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.GetUserSerialStorysResponse;
import com.ifenghui.storybookapi.app.story.response.SerialStoryGroup;
import com.ifenghui.storybookapi.app.story.service.VipSerialGetRecordService;
import com.ifenghui.storybookapi.app.transaction.dao.BuySerialStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.transaction.entity.serial.BuySerialStoryRecord;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.SerialStoryStyle;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wslhk on 2017/1/5.
 */
@Component
public class SerialStoryServiceImpl implements SerialStoryService {

    @Autowired
    SerialStoryDao serialStoryDao;

    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;

    @Autowired
    StoryService storyService;

    @Autowired
    BuySerialStoryRecordDao buySerialStoryRecordDao;

    @Autowired
    UserService userService;

    @Autowired
    VipSerialGetRecordService vipSerialGetRecordService;

    @Override
    public List<SerialStory> getSerialStoryBySerialType(SerialStoryStyle serialStoryStyle) {


        return serialStoryDao.findSerialStoriesByTypeAndStatus(serialStoryStyle.getId(),1);
    }

    @Override
    public SerialStory getSerialStory(long id) {
        return serialStoryDao.findOne(id);
    }
    @Override
    public SerialStory getSerialStoryDetail(Long userId,Long serialStoryId){
        User user = userService.getUser(userId);

        SerialStory  serialStory = serialStoryDao.findOne(serialStoryId);
        if(serialStory == null) {
            throw new ApiNotFoundException("没有找到该益智故事集！");
        }
        if(serialStory.getPrice().equals(0)){
            serialStory.setIsBuy(1);
        } else {
            this.setSerialStoryIsBuy(user,serialStory);
        }
        return serialStory;
    }

    @Override
    public void setSerialStoryIsBuy(User user,SerialStory serialStory){
        serialStory.setIsBuy(0);

        if(serialStory == null){
            return;
        }
        if(serialStory.getPrice()==0){
            serialStory.setIsBuy(1);
        }

        if(user == null){
            return;
        }
        Integer isBuy = 0;
        Long buySerialStoryRecordsCount=buySerialStoryRecordDao.countBuySerialStoryRecordByUserIdAndSerialStoryId(user.getId().intValue(),serialStory.getId().intValue());
        if (buySerialStoryRecordsCount>0){
            isBuy = 1;
        } else if(serialStory.getType().equals(SerialStoryStyle.PARENT_LESSON_SERIAL.getId()) && (user.getSvip().equals(SvipStyle.LEVEL_THREE.getId()) || user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId())) ){
            Integer isGet = vipSerialGetRecordService.isGetVipSerialRecord(user.getId().intValue(), serialStory.getId().intValue());
            if(isGet.equals(1)){
                isBuy = 1;
            } else {
                isBuy = 2;
            }
        }
        if(serialStory.getId()==13 && user.getIsAbilityPlan() == 1){
            isBuy = 1;
        }
        /**音频合集 宝宝会读权限*/
        if((serialStory.getId()==71 ||serialStory.getId()==72 || serialStory.getId()==73) && user.getIsAbilityPlan() == 1){
            isBuy = 1;
        }

        if(serialStory.getPrice()==0){
            isBuy=1;
        }
        if((serialStory.getMemberFree()!=null&&serialStory.getMemberFree()==1)&&user.getIsAbilityPlanAndVip()>0){
            isBuy=1;
        }
        serialStory.setIsBuy(isBuy);
    }
    @Override
    public SerialStory saveSerialStory(SerialStory serialStory) {
        return serialStoryDao.save(serialStory);
    }

    @Override
    public Page<SerialStory> getCommonSerialStoryPage(Long userId, SerialStoryStyle serialStoryStyle, Integer pageNo, Integer pageSize) {
        //第0页，数量小于6时使用缓存查询
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"type","orderBy"));

        Page<SerialStory> serialStoryPage = null;
//        if(isGame.equals(true)){
//            serialStoryPage = serialStoryDao.getCommonSerialStoryPage(pageable, serialStoryStyle.getId());
        if(pageSize==1){
            pageSize=pageSize;
        }
        if(pageNo==0&&pageSize<5){
            List<SerialStory> serialStories=serialStoryDao.getCommonSerialStoryPageOnCache(serialStoryStyle.getId(),new Sort(Sort.Direction.DESC,"type","orderBy"));
            List<SerialStory> serialStoriesNew=new ArrayList<>();
            if(pageSize>serialStories.size()){
                pageSize=serialStories.size();
            }
            for(int i=0;i<pageSize;i++){
                serialStoriesNew.add(serialStories.get(i));
            }
            serialStoryPage=new PageImpl<SerialStory>(serialStoriesNew,pageable,serialStoriesNew.size());
        }else{
            serialStoryPage = serialStoryDao.getCommonSerialStoryPage(pageable, serialStoryStyle.getId());
        }

//        } else {
//            BuyStoryRecord buyStoryRecord = new BuyStoryRecord();
//            buyStoryRecord.setUserId(userId);
//            List<BuyStoryRecord> buyStoryRecordList = buyStoryRecordDao.findAll(Example.of(buyStoryRecord));
//            if(buyStoryRecordList.size() > 0){
//                serialStoryPage = serialStoryDao.getCommonSerialStoryAndMagazinePage(pageable);
//            } else {
//                serialStoryPage = serialStoryDao.getCommonSerialStoryPage(pageable, SerialStoryStyle.INDEX_SERIAL.getId());
//            }
//        }
        if(userId != null && userId != 0L){
            for(SerialStory item : serialStoryPage){
                SerialStory serialStory = this.getSerialStoryDetail(userId, item.getId());
                item.setIsBuy(serialStory.getIsBuy());
            }
        }

        return serialStoryPage;
    }

//    @Override
//    public Page<SerialStory> getIndexSerialStoryPage(Long userId, Integer pageNo, Integer pageSize){
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"type","orderBy"));
//        Page<SerialStory> serialStoryPage = null;
//        if(pageSize<6&&pageNo==0){
//            serialStoryPage = this.getCommonSerialStoryPage(userId,SerialStoryStyle.INDEX_SERIAL,pageNo,pageSize);
//        }else{
//            serialStoryPage = serialStoryDao.getCommonSerialStoryPage(pageable, SerialStoryStyle.INDEX_SERIAL.getId());
//        }
//
//        List<SerialStory> serialStoryList = serialStoryPage.getContent();
//        if(userId != null && userId != 0L){
//            for(SerialStory item : serialStoryList){
//                SerialStory serialStory = storyService.getSerialStoryDetail(userId, item.getId());
//                item.setIsBuy(serialStory.getIsBuy());
//            }
//        }
//        return serialStoryPage;
//    }

    @Override
    public List<SerialStoryGroup> getSerialStoryGroupList(Long userId, Integer serialStoryId){
        List<SerialStoryGroup> serialStoryGroupList = new ArrayList<SerialStoryGroup>();
        List<SerialStory> serialStoryList = serialStoryDao.getSerialStoriesByParentId(serialStoryId);
        for(SerialStory item : serialStoryList){
            SerialStoryGroup serialStoryGroup = new SerialStoryGroup();
            serialStoryGroup.setName(item.getName());
            List<Story> storyList = storyService.getSecondStorySerialDetail(userId, item.getId(), serialStoryId.longValue());
            serialStoryGroup.setStoryList(storyList);
            serialStoryGroupList.add(serialStoryGroup);
        }
        return serialStoryGroupList;
    }

    @Override
    public List<SerialStory> getBuySerialStoryRecordByUserIdAndSerialType(Long userId, Page<BuySerialStoryRecord> buySerialStoryRecordPage){
        User user = userService.getUser(userId);
        SerialStory serialStory;
        List<SerialStory> serialStories = new ArrayList<>();
        for (BuySerialStoryRecord item:buySerialStoryRecordPage.getContent()){
            serialStory = serialStoryDao.findOne(item.getSerialStoryId().longValue());
            this.setSerialStoryIsBuy(user,serialStory);
            serialStories.add(serialStory);
        }
        return serialStories;
    }

    @Override
    public GetUserSerialStorysResponse getUserSerialStorys(Long userId, Integer pageNo, Integer pageSize){
        GetUserSerialStorysResponse response = new GetUserSerialStorysResponse();
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        //获取普通故事集 serialType=1 用户购买记录
        Page<BuySerialStoryRecord> buySerialStoryRecordPage = buySerialStoryRecordDao.getBuySerialStoryRecordsByUserId(userId.intValue(),pageable);
        response.setSerialStoryList(this.getBuySerialStoryRecordByUserIdAndSerialType(userId,buySerialStoryRecordPage));
        response.setJpaPage(buySerialStoryRecordPage);
        return response;
    }

    @Override
    public SerialStory addSerialStory(SerialStory serialStory) {
        return serialStoryDao.save(serialStory);
    }

    @Override
    public SerialStory updateSerialStory(SerialStory serialStory) {
        return serialStoryDao.save(serialStory);
    }

    @Override
    public SerialStory findOne(Integer id) {
        return serialStoryDao.findOne(id.longValue());
    }

    @Override
    public Page<SerialStory> getSerialStoryList(SerialStory serialStory,PageRequest pageRequest) {
        return serialStoryDao.findAll(Example.of(serialStory),pageRequest);
    }


    @Override
    public List<SerialStory> getSerialStoryByIpBrandId(Integer ipBrandId) {
        SerialStory serialStory=new SerialStory();
        serialStory.setIpBrandId(ipBrandId);
        return serialStoryDao.findAll(Example.of(serialStory),new Sort(Sort.Direction.DESC,"orderBy"));
    }
}
