package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.UserGrowthDiaryMusicDao;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryMusic;
import com.ifenghui.storybookapi.app.social.service.UserGrowthDiaryMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGrowthDiaryMusicServiceImpl implements UserGrowthDiaryMusicService {
    @Autowired
    UserGrowthDiaryMusicDao userGrowthDiaryMusicDao;

    @Override
    public Page<UserGrowthDiaryMusic> getUserGrowthDiaryMusicPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"orderBy"));
        return userGrowthDiaryMusicDao.getUserGrowthDiaryMusicsByStatus(1, pageable);
    }
}

