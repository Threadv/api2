package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryMusic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserGrowthDiaryMusicService {

    public Page<UserGrowthDiaryMusic> getUserGrowthDiaryMusicPage(Integer pageNo, Integer pageSize);

}
