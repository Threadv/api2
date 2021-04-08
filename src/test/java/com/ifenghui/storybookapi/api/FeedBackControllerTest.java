package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ifenghui.storybookapi.app.app.dao.DisplayGroupDao;
import com.ifenghui.storybookapi.app.app.dao.FeedbackDao;
import com.ifenghui.storybookapi.app.app.entity.Feedback;
import com.ifenghui.storybookapi.app.app.response.AddFeedbackResponse;
import com.ifenghui.storybookapi.app.social.dao.ViewRecordDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
//@ComponentScan({"com.ifenghui.storybookapi.service","com.ifenghui.storybookapi.service.impl"})
//@EnableJpaRepositories("com.ifenghui.storybookapi.dao")
//@EntityScan("com.ifenghui.storybookapi.entity")
@Rollback
public class FeedBackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserTestService userTestService;

    @Autowired
    StoryDao storyDao;

    @Autowired
    UserService userService;

    @Autowired
    ViewRecordDao viewRecordDao;

    @Autowired
    UserTokenDao userTokenDao;

    @Autowired
    DisplayGroupDao displayGroupDao;

    @Autowired
    FeedbackDao feedbackDao;



    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 测试查询阅读记录
     * @throws Exception
     */
    @Transactional
    @Test
    public void testaddFeedback()throws Exception{
        //创建数据



        User newUser=userTestService.getUser();


        UserToken userToken=userTestService.getUserToken();
        if(userToken==null){
            userToken=new UserToken();
            userToken.setUserId(newUser.getId());
            userToken.setCreateTime(new Date());
            userToken.setToken("123");
            userToken.setRefreshToken("123");
            userToken.setDevice("");
            userToken.setUserAgent("ua");
            userToken.setDeviceUnique("123");
            userToken.setDeviceName("123");
            userToken.setAddr("");
            userToken.setIsValid(1);
            userToken=userTokenDao.save(userToken);
        }



//        Pageable pageable=new PageRequest(0, 10, new Sort("id"));
//        List list=viewRecordDao.getViewrecordsByUserId(newUser.getId(), pageable);
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/feedback/addFeedback").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("content", "iphone 4.5")
                .param("contactInfo", "12345678901234567890123456789012")
                .param("ver", "4.5.1")
                .header("sstoken",userToken.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        AddFeedbackResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),AddFeedbackResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
//        Feedback newFeedback=feedbackDao.getOne(apireponse.getFeedback().getId());
//        if(newFeedback==null){
//            Assert.assertTrue(false);
//        }
//
//        result=mockMvc.perform(MockMvcRequestBuilders.post("/api/feedback/addFeedback").contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("token", "w2w2w2")
//                .param("content", "iphone 4.5")
//                .param("contactInfo", "12345678901234567890123456789012")
//                .param("ver", "4.5.1")
//        ).andDo(MockMvcResultHandlers.print())
//                .andReturn();
//
//        apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),AddFeedbackResponse.class);
//        if(apireponse.getStatus().getCode()!=203){
//            Assert.assertTrue(false);
//        }

    }

}
