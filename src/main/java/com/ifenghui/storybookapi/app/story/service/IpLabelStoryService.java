package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.entity.IpLabelStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IpLabelStoryService {

    IpLabelStory findOne(Integer id);

    IpLabelStory addIpLabelStory(IpLabelStory ipLabelStory);

    IpLabelStory updateIpLabelStory(IpLabelStory ipLabelStory);

    void deleteIpLabelStory(Integer id);

    /**
     *
     * @param storyId
     */
    void deleteIpLabelStoryByStoryId(Integer storyId);

    void deleteIpLabelStoryByipLabelId(Integer ipLabelId);

    Page<IpLabelStory> getIpLabelStoryPage(
            Integer pageNo,
            Integer pageSize,
            Integer ipId,
            Integer ipLabelId,
            Integer ipLabelParentId
    );

    List<Story> getStoryListByIpLabelStoryPage(Page<IpLabelStory> ipLabelStoryPage, Long userId);


    /**
     * 接口使用，带分页
     * @param ipLabelId
     * @param pageRequest
     * @return
     */
    Page<IpLabelStory> getIpLabelStorysByIpLabelId(
            Integer ipLabelId,
             PageRequest pageRequest
    );

    /**
     * 后台用返回iplabel中所有关联
     * @param ipLabelId
     * @return
     */
    List<IpLabelStory> getIpLabelStorysByIpLabelId(
            Integer ipLabelId
    );


    /**
     * 查询所有label关联，并且设置了story再关联中
     * @param ipLabelId
     * @return
     */
    List<IpLabelStory> getIpLabelStorysAndSetStoryByIpLabelId(
            Integer ipLabelId
    );
}
