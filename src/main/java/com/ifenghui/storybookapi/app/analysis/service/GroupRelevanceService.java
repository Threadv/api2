package com.ifenghui.storybookapi.app.analysis.service;

import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.response.GetNewGroupStoryListResponse;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by wml on 2016/12/27.
 */
public interface GroupRelevanceService {

    /**
     * 不包含故事
     * @param groupId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<GroupRelevance> getGroupRevelanceByGroupId(Long groupId, Integer pageNo, Integer pageSize);//首页分组关联

    /**
     * 返回包含故事
     * @param groupId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<GroupRelevance> getRevelanceByGroupId(Long groupId, Integer pageNo, Integer pageSize);//首页分组关联

    GroupRelevance saveRevelance(GroupRelevance groupRelevance);//首页分组关联

    Page<Story> getNewGroupStoryList(User user, Integer groupId, Integer pageNo, Integer pageSize);

    void addGroupRelevanceByNewType();

    public GroupRelevance addGroupRelevance(Long storyId, Integer groupId, Integer status, Integer orderBy, Integer isDel);

    public void addGroupRelevanceByNewTypeTest();

    public List<Story> getUserStoryByGroupId(Long userId, Integer groupId);

    void deleteGroupRelevance(Integer id);

    GroupRelevance finOne(Integer id);

    GroupRelevance update(GroupRelevance groupRelevance);
}
