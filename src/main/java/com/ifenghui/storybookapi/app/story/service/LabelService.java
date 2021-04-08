package com.ifenghui.storybookapi.app.story.service;

/**
 * Created by jia on 2016/12/22.
 */
import com.ifenghui.storybookapi.app.story.entity.Label;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

//@CacheConfig(cacheNames = "storyBookLabel")
//@Cacheable
//@CmsCacheclear(entityType = Label.class)
public interface LabelService {

    @Cacheable(value = "labellist",key = "'labellist'+#pageNo+''+#pageSize")
    Page getLabels(int pageNo,int pageSize);

    @Cacheable(value = "lahuanbel",key = "'getLabel'+#id")
    Label getLabel(long id);
//    @CachePut(value="user",key="#id")
    @CachePut(value="label",key="'getLabel'+#id")
    @CacheEvict(value={"labellist","labellist"},allEntries = true)
    Label updateLabel(long id,String content)throws Exception;

    Label addLabel(Label label);

    Label delLabel(long id);






}
