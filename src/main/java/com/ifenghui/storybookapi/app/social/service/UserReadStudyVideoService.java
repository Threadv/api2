package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.UserReadStudyVideo;
import org.springframework.data.domain.Page;

public interface UserReadStudyVideoService {

    UserReadStudyVideo addUserReadStudyVideo(Integer itemId, Integer videoId, Integer userId);

    public boolean isNeedAddNewRecord(Integer videoId, Integer userId);

    public void addUserReadStudyVideoRecord(Integer itemId, Integer videoId, Integer userId);

    public Page<UserReadStudyVideo> getUserReadStudyVideoByUserIdAndVideoId(Integer userId, Integer videoId);
}
