package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import com.ifenghui.storybookapi.app.analysis.dao.GroupRelevanceDao;
import com.ifenghui.storybookapi.app.analysis.entity.GroupRelevance;
import com.ifenghui.storybookapi.app.app.dao.DisplayGroupDao;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.social.dao.ViewRecordDao;
import com.ifenghui.storybookapi.app.social.entity.ViewRecord;
import com.ifenghui.storybookapi.app.social.response.AddViewRecordResponse;
import com.ifenghui.storybookapi.app.social.response.GetViewRecordsByUserIdResponse;
import com.ifenghui.storybookapi.app.story.dao.MagazineDao;
import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.story.entity.Magazine;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.story.entity.Story;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.service.UserTestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wslhk on 2016/12/26.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //mockmvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Rollback
public class ViewRecordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDao userDao;

    @Autowired
    StoryDao storyDao;

    @Autowired
    UserService userService;

    @Autowired
    UserTestService userTestService;

    @Autowired
    ViewRecordDao viewRecordDao;

    @Autowired
    UserTokenDao userTokenDao;

    @Autowired
    DisplayGroupDao displayGroupDao;

    @Autowired
    MagazineDao magazineDao;

    @Autowired
    GroupRelevanceDao groupRelevanceDao;

    @Autowired
    SerialStoryDao serialStoryDao;

    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    DisplayGroup newGroup;
    Magazine magazine;
    SerialStory newSerialStory;
    User newUser;
//    UserToken newToken;
//    Story newStory;
//    @Before
//    public void testBefore() {
//        //创建数据
//        DisplayGroup group=new DisplayGroup();
//        group.setName("test123");
//        group.setStatus(1);
//        group.setTargetType(1);
//        group.setTargetValue(1);
//        group.setContent("");
//        group.setOrderBy(1);
//
////        group.setStorys(stories);
//
//        newGroup= displayGroupDao.save(group);
//        newGroup.setTargetValue(newGroup.getId().intValue());
//        displayGroupDao.save(newGroup);
//
//        magazine=new Magazine();
//        magazine.setCreateTime(new Date());
//        magazine.setOrderBy(1);
//        magazine.setIsNow(0);
//        magazine.setStatus(1);
//        magazine.setName("");
//        magazine=magazineDao.save(magazine);
//
//        //创建故事
//        Story story=new Story.Builder().initAdd().setName("123").setCateGroup(newGroup).build();
////        story.setMagazine(magazine);
//        newStory=storyDao.save(story);
//        List<Story> stories=new ArrayList<>();
//        stories.add(newStory);
//
//
//
//        //创建关联
//        GroupRelevance relevance=new GroupRelevance();
//        relevance.setStatus(1);
//        relevance.setGroupId(newGroup.getId());
//        relevance.setStoryId(newStory.getId());
//        relevance.setCreateTime(new Date());
//        relevance.setOrderBy(1);
//        GroupRelevance newRelevance= groupRelevanceDao.save(relevance);
//
//        User user=new User.Builder().initAdd().setPhone("138001382542").build();
//        newUser=userDao.save(user);
//
//        SerialStory serialStory=new SerialStory();
//        serialStory.setOrderBy(1);
//        serialStory.setStatus(1);
//        serialStory.setName("test");
//        serialStory.setCreateTime(new Date());
//        newSerialStory=serialStoryDao.save(serialStory);
//
//
//
//        UserToken userToken=new UserToken();
//        userToken.setUser(user);
//        userToken.setCreateTime(new Date());
//        userToken.setToken("123");
//        userToken.setRefreshToken("123");
//        userToken.setDevice("");
//        userToken.setUserAgent("ua");
//        newToken=userTokenDao.save(userToken);
//
//
//    }

    /**
     * 测试查询阅读记录
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetViewrecordsByUser()throws Exception{
        //创建数据
User newUser=userTestService.getUser();
UserToken newToken=userTestService.getUserToken();
        Pageable pageable=new PageRequest(0, 10, new Sort("id"));
        Page list=viewRecordDao.getViewrecordsByUserId(newUser.getId(), pageable);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/viewrecord/getViewrecordsByUser?token="+newToken.getToken()+"&pageNo=1&pageSize=10"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        GetViewRecordsByUserIdResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetViewRecordsByUserIdResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
//        GetViewRecordsByUserIdResponse getUserResponse= objectMapper.readValue(result.getResponse().getContentAsString(),GetViewRecordsByUserIdResponse.class);
//        if(getUserResponse.getViewrecords().size()!=2){
//            Assert.assertTrue(false);
//        }

    }


    @Transactional
    @Test
    public void testaddViewrecord()throws Exception{
//        //创建数据
User newUser=userTestService.getUser();
UserToken newToken=userTestService.getUserToken();
Story newStory=new Story();
        newStory.setId(1L);
        newStory.setType(1);

        Pageable pageable=new PageRequest(0, 10, new Sort("id"));
        Page list=viewRecordDao.getViewrecordsByUserId(newUser.getId(), pageable);

        MvcResult result;
        result=mockMvc.perform(MockMvcRequestBuilders.post("/api/viewrecord/addViewrecord").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("storyId", newStory.getId()+"")
                .param("type", newStory.getType()+"")
                .header("sstoken",newToken.getToken())
        ).andDo(MockMvcResultHandlers.print())
                .andReturn();

        ApiResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),AddViewRecordResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
        AddViewRecordResponse getUserResponse= objectMapper.readValue(result.getResponse().getContentAsString(),AddViewRecordResponse.class);
        if(getUserResponse.getViewrecord()==null){
            Assert.assertTrue(false);
        }

        ViewRecord viewRecordSave= viewRecordDao.getOne(getUserResponse.getViewrecord().getId());
        if(viewRecordSave==null){
            Assert.assertTrue(false);
        }
//
//        //删除测试数据
//        userDao.delete(newUser);
//        result=mockMvc.perform(MockMvcRequestBuilders.get("/api/user/getUser?id="+newUser.getId()))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetUserResponse.class);
//        if(apireponse.getStatus().getCode()!=201){
//            Assert.assertTrue(false);
//        }
    }


}
