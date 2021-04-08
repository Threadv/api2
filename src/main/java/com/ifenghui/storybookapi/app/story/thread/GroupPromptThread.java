package com.ifenghui.storybookapi.app.story.thread;

import com.ifenghui.storybookapi.app.app.entity.Ads;
import com.ifenghui.storybookapi.app.app.service.AdService;
import com.ifenghui.storybookapi.app.story.response.Prompt;
import com.ifenghui.storybookapi.app.story.service.PromptService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.style.PromptStyle;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GroupPromptThread implements Runnable {
    Logger logger=Logger.getLogger(GroupPromptThread.class);
    PromptService promptService;
    Prompt prompt;
    User user;

    public GroupPromptThread(PromptService promptService, User user){

        this.promptService=promptService;
        this.user=user;

    }
    @Override
    public void run() {
//        logger.info("----begin prompt");
        int userId=0;
        if(user!=null){
            userId=user.getId().intValue();
        }
        if(userId!=0){
            try {
                prompt = promptService.getPrompt(userId);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
              prompt=new Prompt(PromptStyle.STUDY_PLAN.getId(),PromptStyle.STUDY_PLAN.getName(),PromptStyle.STUDY_PLAN.getUrl(),0);
        }

//        logger.info("----end prompt");
    }

    public Prompt getPrompt() {
        return prompt;
    }
}
