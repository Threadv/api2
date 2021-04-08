package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.app.dao.DisplayGroupDao;
import com.ifenghui.storybookapi.app.app.dao.FeedbackDao;
import com.ifenghui.storybookapi.app.app.response.AddFeedbackResponse;
import com.ifenghui.storybookapi.app.social.dao.ViewRecordDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.system.service.ElasticService;
import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.service.UserTestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wslhk on 2016/12/26.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //mockmvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Rollback
public class ElasticControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ElasticService elasticService;

    @Autowired
    StoryDao storyDao;


    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 测试定时运行程序，定时清理已经设置为不发布的故事
     * @throws Exception
     */
    @Transactional
    @Test
    public void testClearElastic()throws Exception{
        //创建数据

        elasticService.cleanStoryByStatus0();



    }

}
