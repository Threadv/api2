package com.ifenghui.storybookapi.app.analysis.service.impl;

import com.ifenghui.storybookapi.app.analysis.dao.GroupRelevanceDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.response.GetNewGroupStoryListResponse;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.analysis.service.GroupRelevanceService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.style.StoryGroupRelateStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Created by wml on 2016/12/27.
 */
@Transactional
@Component
public class GroupRelevanceServiceImpl implements GroupRelevanceService {


    @Autowired
    GroupRelevanceDao groupRelevanceDao;


    @Autowired
    StoryService storyService;

    @Autowired
    UserService userService;

    @Autowired
    StoryDao storyDao;

    @Transactional
    @Override
    public Page< GroupRelevance> getGroupRevelanceByGroupId(Long groupId, Integer pageNo, Integer pageSize) {

        Integer status = 1;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        Page<GroupRelevance> groupRelevance=this.groupRelevanceDao.getGroupRelevancesByGroupIdAndStatus(groupId,status,pageable);
        return groupRelevance;
    }

    @Override
    public Page< GroupRelevance> getRevelanceByGroupId(Long groupId, Integer pageNo, Integer pageSize) {

        Integer status = 1;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        Page<GroupRelevance> groupRelevance=this.groupRelevanceDao.getGroupRelevancesByGroupIdAndStatus(groupId,status,pageable);
        Story story;
//        BookPrice bookPrice;
        for (GroupRelevance relevance :groupRelevance.getContent()) {
            story = storyDao.findOne(relevance.getStoryId());// relevance.getStory();
            relevance.setStory(story);


        }

        return groupRelevance;
    }

    @Override
    public GroupRelevance saveRevelance(GroupRelevance groupRelevance) {

        GroupRelevance groupRelevance1= groupRelevanceDao.findByGroupIdAndStoryId(groupRelevance.getGroupId(),groupRelevance.getStoryId());
        if(groupRelevance1!=null){
            groupRelevance1.setStatus(1);
            return groupRelevanceDao.save(groupRelevance1);
        }else{
            groupRelevance.setCreateTime(new Date());
            return groupRelevanceDao.save(groupRelevance);
        }

    }

    @Override
    public Page<Story> getNewGroupStoryList(User user, Integer groupId, Integer pageNo, Integer pageSize) {
        GetNewGroupStoryListResponse response = new GetNewGroupStoryListResponse();
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy","id"));
        Integer status = 1;
        Page<GroupRelevance> groupRelevancePage=null;
        //0,6 分页加缓存，用于首页内容缓存
        if(pageNo==0&&pageSize==6){
            groupRelevancePage = groupRelevanceDao.getGroupRelevancesByGroupIdAndStatus_cache(groupId.longValue(),status, pageable);
        }else{
            groupRelevancePage = groupRelevanceDao.getGroupRelevancesByGroupIdAndStatus(groupId.longValue(),status, pageable);
        }
//        Page<GroupRelevance> groupRelevancePage = groupRelevanceDao.getGroupRelevancesByGroupIdAndStatus(groupId.longValue(),status, pageable);
        response.setJpaPage(groupRelevancePage);

        List<GroupRelevance> groupRelevanceList = groupRelevancePage.getContent();

        Page<Story> stories=new PageImpl<Story>(new ArrayList<>());;
        if(groupRelevanceList.size() != 0){
            List<Story> storyList = new ArrayList<>();
//            User user = userService.getUser(userId);


//            ExecutorService exec = Executors.newFixedThreadPool(groupRelevanceList.size());
            Story story;
//            TreeMap<Integer,Story> map=new TreeMap<>();
            for(GroupRelevance item : groupRelevanceList){
//                map.put(item.getStoryId().intValue(),null);
//
//
//                Runnable task=new Runnable() {
//                    @Override
//                    public void run() {
//                        Long hasuser=null;
                        story = storyDao.findOne(item.getStoryId());
                        storyService.setStoryIsBuy(user,story);
                        storyService.setStoryAppFile(story);
//                        map.put(item.getStoryId().intValue(),story);
//                    }
//                };
//                exec.execute(task);
//                story = storyDao.findOne(item.getStoryId());

//                storyService.setStoryIsBuy(user,story);
//                storyService.setStoryAppFile(story);
                storyList.add(story);
            }

//            try {
//                exec.shutdown();
//                exec.awaitTermination(2, TimeUnit.SECONDS);
//            } catch (InterruptedException ignored) {
//                ignored.printStackTrace();
//            }
//            Iterator<Map.Entry<Integer,Story>> iterator =map.entrySet().iterator();
//            while(iterator.hasNext()) {
//                Map.Entry<Integer, Story> entry = iterator.next();
//                storyList.add(entry.getValue());
//            }
            response.setStorys(storyList);
            stories=new PageImpl<Story>(storyList,pageable,groupRelevancePage.getTotalElements());

        }
//        Page<Story> stories=new PageImpl<Story>(storyList);
        return stories;
    }

    @Override
    public void addGroupRelevanceByNewType() {
        List<Story> storyList = storyDao.findAll();
        for(Story item : storyList){
            if(item.getIsDel().equals(0) && (item.getCategoryNewId().equals(1) || item.getCategoryNewId().equals(2) || item.getCategoryNewId().equals(3) || item.getCategoryNewId().equals(4))){
                StoryGroupRelateStyle storyGroupRelateStyle = StoryGroupRelateStyle.getByCategoryNewId(item.getCategoryNewId());
                if(item.getType() != 2){
                    this.addGroupRelevance(item.getId(), storyGroupRelateStyle.getGroupId(), 1, item.getOrderBy(),item.getIsDel());
                }
            }
        }
    }

    @Override
    public GroupRelevance addGroupRelevance(Long storyId, Integer groupId, Integer status, Integer orderBy, Integer isDel){
        GroupRelevance groupRelevance = new GroupRelevance();
        groupRelevance.setStoryId(storyId);
        groupRelevance.setGroupId(groupId.longValue());
        groupRelevance = groupRelevanceDao.findByGroupIdAndStoryId(groupId.longValue(),storyId);
        if(groupRelevance == null){
            groupRelevance = new GroupRelevance();
            groupRelevance.setStoryId(storyId);
            groupRelevance.setGroupId(groupId.longValue());
            groupRelevance.setIsDel(isDel);
            groupRelevance.setOrderBy(orderBy);
            groupRelevance.setStatus(status);
            groupRelevance.setCreateTime(new Date());
            return groupRelevanceDao.save(groupRelevance);
        } else {
            return groupRelevance;
        }
    }

    @Override
    public void addGroupRelevanceByNewTypeTest(){
        List<Story> storyList = storyDao.findAll();
        for(Story item : storyList){
            if(item.getIsDel().equals(0) && (item.getCategoryId().equals(1) || item.getCategoryId().equals(2) || item.getCategoryId().equals(5) || item.getCategoryId().equals(6))){
                Integer cateId = item.getCategoryId();
                if(cateId.equals(5)){
                    cateId = 3;
                } else if(cateId.equals(6)){
                    cateId = 4;
                }
                StoryGroupRelateStyle storyGroupRelateStyle = StoryGroupRelateStyle.getByCategoryNewId(cateId);
                this.addGroupRelevance(item.getId(), storyGroupRelateStyle.getGroupId(), 1, item.getOrderBy(),item.getIsDel());
            }
        }
    }

    @Override
    public List<Story> getUserStoryByGroupId(Long userId, Integer groupId){
        User user=userService.getUser(userId);
        Page<Story> getNewGroupStoryListResponse = this.getNewGroupStoryList(user, groupId, 0, 100);
        List<Story> storyList = new ArrayList<>(getNewGroupStoryListResponse.getContent());
        Iterator<Story> iterator = storyList.iterator();
        while (iterator.hasNext()) {
            Story item = iterator.next();
            if(item.getIsFree().equals(1)){
                iterator.remove();
            } else if(item.getIsPurchased().equals(0)){
                iterator.remove();
            }
        }
        return storyList;
    }

    @Override
    public void deleteGroupRelevance(Integer id) {
        groupRelevanceDao.delete(id.longValue());
    }

    @Override
    public GroupRelevance finOne(Integer id) {
        return groupRelevanceDao.findOne(id.longValue());
    }

    @Override
    public GroupRelevance update(GroupRelevance groupRelevance) {
        return groupRelevanceDao.save(groupRelevance);
    }
}
