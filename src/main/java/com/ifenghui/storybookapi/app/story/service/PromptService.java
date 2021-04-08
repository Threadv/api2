package com.ifenghui.storybookapi.app.story.service;

import com.ifenghui.storybookapi.app.story.response.Prompt;
import com.ifenghui.storybookapi.style.WeekPlanStyle;

public interface PromptService {


    /**
     * 添加提示框 首页学习报告
     * @param userId
     * @return
     */
   Prompt getPrompt(Integer userId)throws  Exception;


    /***
     * 是否达到参加计划下周
     * @param userId
     * @return
     */
    Integer isAchieve(Integer userId) throws  Exception;

    /***
     * 通过planStyle是否达到参加计划下周
     * @param userId
     * @return
     */
    Integer isAchieveByPlanStyle(Integer userId,WeekPlanStyle planStyle)throws  Exception;
}
