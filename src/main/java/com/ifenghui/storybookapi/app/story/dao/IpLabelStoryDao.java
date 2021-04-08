package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.IpLabelStory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IpLabelStoryDao extends JpaRepository<IpLabelStory, Integer> {

    @Query("select ip from IpLabelStory as ip where ip.ipId=:ipId and ip.ipLabelId=:ipLabelId")
    Page<IpLabelStory> getIpLabelStoriesByIpIdAndAndIpLabelId(
            @Param("ipId") Integer ipId,
            @Param("ipLabelId") Integer ipLabelId,
            Pageable pageable
    );

    @Query("select ip from IpLabelStory as ip where ip.ipId=:ipId and ip.ipLabelParentId=:ipLabelParentId")
    Page<IpLabelStory> getIpLabelStoriesByIpIdAndIpLabelParentId(
            @Param("ipId") Integer ipId,
            @Param("ipLabelParentId") Integer ipLabelParentId,
            Pageable pageable
    );

    @Cacheable(cacheNames ="getAllpLabelStoriesByIpLabelId",key = "'getAllpLabelStoriesByIpLabelId'+#p0")
    @Query("select ip from IpLabelStory as ip where ip.ipLabelId=:ipLabelId")
    List<IpLabelStory> getAllpLabelStoriesByIpLabelId(
            @Param("ipLabelId") Integer ipLabelId,
            Sort sort
    );

    void deleteAllByStoryId(Integer storyId);


    void deleteAllByIpLabelId(Integer ipLabelId);

    @CacheEvict(cacheNames ="getAllpLabelStoriesByIpLabelId",key = "'getAllpLabelStoriesByIpLabelId'+#p0.ipLabelId")
    IpLabelStory save(IpLabelStory ipLabelStory);

    @CacheEvict(cacheNames ="getAllpLabelStoriesByIpLabelId",key = "'getAllpLabelStoriesByIpLabelId'+#p0.ipLabelId")
    void delete(IpLabelStory ipLabelStory);
}
