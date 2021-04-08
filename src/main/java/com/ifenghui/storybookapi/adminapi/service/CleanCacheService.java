package com.ifenghui.storybookapi.adminapi.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

public interface CleanCacheService {

    /**
     * 清除用户购买记录
     */
    @Caching(evict = {@CacheEvict(cacheNames = "countByUserIdAndStoryId",key = "'countByUserIdAndStoryId'+#p0+'_'+#p1")})
    public void cleanUserBuyRecord(Integer userId,Integer storyId);
}
