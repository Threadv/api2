package com.ifenghui.storybookapi.app.presale.service;


import com.ifenghui.storybookapi.app.presale.entity.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ActivityService {

    /**
     * id查询活动
     * @param activityId
     * @return
     */
    Activity findById(Integer activityId);

    /**
     * 通过分销授权跳转过来的会带个type对应这里的key
     * @param key
     * @return
     */
    Activity findByKey(String key);

    Page<Activity> findAll(Activity activity, PageRequest pageRequest);


    Activity save(Activity activity);

    void delete(Integer id);
}
