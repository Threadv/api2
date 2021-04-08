package com.ifenghui.storybookapi.web;
/**
 * Created by jia on 2016/12/22.
 */


import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.story.service.StoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@EnableAutoConfiguration
//@EnableAutoConfiguration
//@SpringBootApplication
//@ConfigurationProperties(prefix = "spring")
@RequestMapping("/web")
public class ShareController {

    @Autowired
    StoryService storyService;
    @Autowired
    public Environment env;

    @RequestMapping(value="/shareStory/{storyIdStr}",method = RequestMethod.GET)
    @ResponseBody
    void shareStory(HttpServletResponse response,@PathVariable String storyIdStr) throws IOException {
//        return "Hello World!";
try{


        int storyId=Integer.parseInt(storyIdStr);
        Story story=storyService.getStory(storyId);

        if(story.getWebFile().indexOf("zip")==-1){
            response.getWriter().write("not find share page.");
            return;
        }

        String url=env.getProperty("oss.url")+"webfolder/"+story.getWebFile().replace(".zip","/index.html");
        response.sendRedirect(url);
}catch (Exception e){
    e.printStackTrace();;
    response.getWriter().write("not find share page.");
    return;
}


    }



}
