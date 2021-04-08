package com.ifenghui.storybookapi.app.system.service;



import org.elasticsearch.client.Response;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Map;

/**
 * Created by wslhk on 2017/7/24.
 */
public interface ElasticService {

    /**
     * 记录异常
     * @param ex
     * @throws Exception
     */
    public void addException(Exception ex) throws Exception;


    public Page<Map> searchStory(String content, int pageNo, int pageSize) throws Exception;
    public Page<Map> searchStoryNotNow(String content, int pageNo, int pageSize) throws Exception;


    /**
     * 需要定时执行的任务，定时清理状态是0的故事，
     * 如果后台编辑已经转到java后他，可以取消这个功能。
     */
    public void cleanStoryByStatus0();
}
