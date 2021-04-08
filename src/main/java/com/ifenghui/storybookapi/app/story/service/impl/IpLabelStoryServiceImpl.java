package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.story.dao.IpLabelStoryDao;
import com.ifenghui.storybookapi.app.story.entity.IpLabelStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.IpLabelStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IpLabelStoryServiceImpl implements IpLabelStoryService {

    @Autowired
    IpLabelStoryDao ipLabelStoryDao;

    @Autowired
    StoryService storyService;


    @Override
    public IpLabelStory findOne(Integer id) {
        return ipLabelStoryDao.findOne(id);
    }

    @Override
    public IpLabelStory addIpLabelStory(IpLabelStory ipLabelStory) {
        return ipLabelStoryDao.save(ipLabelStory);
    }

    @Override
    public IpLabelStory updateIpLabelStory(IpLabelStory ipLabelStory) {
        return ipLabelStoryDao.save(ipLabelStory);
    }

    @Override
    public void deleteIpLabelStory(Integer id) {
        ipLabelStoryDao.delete(id);
    }

    @Override
    public void deleteIpLabelStoryByStoryId(Integer storyId) {
        ipLabelStoryDao.deleteAllByStoryId(storyId);
    }

    @Override
    public void deleteIpLabelStoryByipLabelId(Integer ipLabelId) {
        ipLabelStoryDao.deleteAllByIpLabelId(ipLabelId);
    }

    @Override
    public Page<IpLabelStory> getIpLabelStoryPage(
        Integer pageNo,
        Integer pageSize,
        Integer ipId,
        Integer ipLabelId,
        Integer ipLabelParentId
    ) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        if(ipLabelId.equals(0)) {
            return ipLabelStoryDao.getIpLabelStoriesByIpIdAndIpLabelParentId(ipId, ipLabelParentId, pageable);
        } else {
            return ipLabelStoryDao.getIpLabelStoriesByIpIdAndAndIpLabelId(ipId, ipLabelId, pageable);
        }
    }

    @Override
    public List<Story> getStoryListByIpLabelStoryPage(Page<IpLabelStory> ipLabelStoryPage, Long userId) {
        List<IpLabelStory> ipLabelStoryList = ipLabelStoryPage.getContent();
        List<Story> storyList = new ArrayList<>();
        if(ipLabelStoryList != null && ipLabelStoryList.size() > 0){
            for(IpLabelStory item : ipLabelStoryList){
                Story story = storyService.getStoryDetailById(item.getStoryId().longValue(), userId);
                storyService.setStoryAppFile(story);
                storyList.add(story);
            }
        }
        return storyList;
    }

    @Override
    public Page<IpLabelStory> getIpLabelStorysByIpLabelId(Integer ipLabelId, PageRequest pageRequest) {
        IpLabelStory ipLabelStory=new IpLabelStory();
        ipLabelStory.setIpLabelId(ipLabelId);
        return ipLabelStoryDao.findAll(Example.of(ipLabelStory),pageRequest );
    }

    @Override
    public List<IpLabelStory> getIpLabelStorysByIpLabelId(Integer ipLabelId) {
//        IpLabelStory ipLabelStory=new IpLabelStory();
//        ipLabelStory.setIpLabelId(ipLabelId);
        return ipLabelStoryDao.getAllpLabelStoriesByIpLabelId(ipLabelId,new Sort(Sort.Direction.DESC,"orderBy"));
    }

    @Override
    public List<IpLabelStory> getIpLabelStorysAndSetStoryByIpLabelId(Integer ipLabelId) {
        List<IpLabelStory> labelStories=this.getIpLabelStorysByIpLabelId(ipLabelId);
        for(IpLabelStory ipLabelStory:labelStories){
            ipLabelStory.setStory(storyService.getStory(ipLabelStory.getStoryId()));
        }
        return labelStories;
    }
}
