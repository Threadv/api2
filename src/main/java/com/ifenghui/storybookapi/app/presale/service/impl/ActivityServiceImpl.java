package com.ifenghui.storybookapi.app.presale.service.impl;



import com.ifenghui.storybookapi.app.presale.dao.ActivityDao;
import com.ifenghui.storybookapi.app.presale.entity.Activity;
import com.ifenghui.storybookapi.app.presale.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityDao activityDao;

    /**
     * id查询活动
     * @param activityId
     * @return
     */
    @Override
    public Activity findById(Integer activityId) {

        Activity activity = activityDao.findOne(activityId);
        return activity;
    }

    @Override
    public Activity findByKey(String key) {
        return activityDao.findOneByWxRedirectKey(key);
    }

    @Override
    public Page<Activity> findAll(Activity activity, PageRequest pageRequest) {
        return activityDao.findAll(Example.of(activity),pageRequest);
    }

    @Override
    public Activity save(Activity activity) {
        return activityDao.save(activity);
    }

    @Override
    public void delete(Integer id) {
        activityDao.delete(id);
    }
}
