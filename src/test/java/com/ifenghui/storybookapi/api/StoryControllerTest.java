package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.analysis.dao.GroupRelevanceDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wslhk on 2016/12/26.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //mockmvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Rollback
public class StoryControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Autowired
    GroupRelevanceDao groupRelevanceDao;

//    @Autowired
//    CacheManager cacheManager;

    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 测试查询阅读记录
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetStoryDetailById()throws Exception{
        //创建数据
//        Story story=new Story.Builder().initAdd().setName("123").build();
//        Story newStory=storyDao.save(story);
//
//        User user=new User.Builder().initAdd().setPhone("138001382542").build();
//        User newUser=userDao.save(user);
//
//
//        UserToken userToken=new UserToken();
//        userToken.setUser(user);
//        userToken.setCreateTime(new Date());
//        userToken.setToken("123");
//        userToken.setRefreshToken("123");
//        userToken.setDevice("");
//        userToken.setUserAgent("ua");
//        UserToken newToken=userTokenDao.save(userToken);
//
//        Pageable pageable=new PageRequest(0, 10, new Sort("id"));
//        Page list=viewRecordDao.getViewrecordsByUserId(newUser.getId(), pageable);

//        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/story/getStoryDetailById?token="+newToken.getToken()+"&id="+newStory.getId()))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        ApiResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetStoryDetailByIdResponse.class);
//        if(apireponse.getStatus().getCode()!=1){
//            Assert.assertTrue(false);
//        }
//        GetStoryDetailByIdResponse getStoryDetailByIdResponse= objectMapper.readValue(result.getResponse().getContentAsString(),GetStoryDetailByIdResponse.class);
//        if(getStoryDetailByIdResponse.getStory()==null||getStoryDetailByIdResponse.getStory().getId().intValue()!=newStory.getId().intValue()){
//            Assert.assertTrue(false);
//        }

    }
//    @Transactional
//    @Test
//    public void testgetStorysByCategoryId() throws Exception {
//        //创建数据
//        Story story=new Story.Builder().initAdd().setName("123").setCategoryId(StoryCategory.SINGLE.getId()).build();
//        Story newStory=storyDao.save(story);
//
//        User user=new User.Builder().initAdd().setPhone("138001382542").build();
//        User newUser=userDao.save(user);
//
//
//        UserToken userToken=new UserToken();
//        userToken.setUser(user);
//        userToken.setCreateTime(new Date());
//        userToken.setToken("123");
//        userToken.setRefreshToken("123");
//        userToken.setDevice("");
//        userToken.setUserAgent("ua");
//        UserToken newToken=userTokenDao.save(userToken);
//
//        Pageable pageable=new PageRequest(0, 10, new Sort("id"));
//        Page list=viewRecordDao.getViewrecordsByUserId(newUser.getId(), pageable);
//
//        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/story/getStorysByCategoryId?token="+newToken.getToken()+"&categoryId="+ StoryCategory.SINGLE.getId()+"&pageNo="+ 1+"&pageSize="+ 10))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        ApiPageResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetStorysByCategoryResponse.class);
//        if(apireponse.getStatus().getCode()!=1){
//            Assert.assertTrue(false);
//        }
//        GetStorysByCategoryResponse getStoryDetailByIdResponse= objectMapper.readValue(result.getResponse().getContentAsString(),GetStorysByCategoryResponse.class);
//        if(getStoryDetailByIdResponse.getStorys()==null||getStoryDetailByIdResponse.getStorys().size()==0){
//            Assert.assertTrue(false);
//        }
//    }


//    @Autowired
//    DisplayGroupDao displayGroupDao;

//    @Autowired
//    GroupRelevanceDao groupRelevanceDao;

    @Transactional
    @Test
    public void testgetStoryBooks() throws Exception {

    }



}
