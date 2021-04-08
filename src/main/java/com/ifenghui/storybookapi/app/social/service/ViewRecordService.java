package com.ifenghui.storybookapi.app.social.service;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ViewRecordService {

    /**
     * 故事足迹  1 和 4 类型
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<ViewRecord> getViewrecordByUserIdAndType(Long userId,Integer pageNo ,Integer pageSize);

    ViewRecord getViewrecord(Long id);
    Void delViewrecord(Long id);

    Void eidtViewRecord(ViewRecord viewRecord);

    ViewRecord addViewrecord(ViewRecord viewrecord);
    List<ViewRecord> getViewrecordsByUser(Long userId);
    Page<ViewRecord> getViewrecordsByUserAndStoryId(Long userId,Long storyId,Integer pageNo,Integer pageSize);
    /**
     * 通过 userId  分页获取观看记录
     */
    Page<ViewRecord> getViewrecordsByUserAndPage(Long userId, Integer pageNo, Integer pageSize, HttpServletRequest request);
    /**
     * 分页获取观看记录
    */
    Page<ViewRecord> getViewrecordsByPage(Integer pageNo, Integer pageSize);

    /**
     * 处理累计阅读
     */
    void addUserReadRecordCount(Long userId, Long storyId);

    public Page<ViewRecord> getViewRecordsByUserIdAndPage(Long userId, Integer pageNo, Integer pageSize);

    @Deprecated
    Void addActivityViewRecord(Integer id);//添加活动浏览

    public void addLessonViewRecord(Integer userId, Integer storyId);
}
