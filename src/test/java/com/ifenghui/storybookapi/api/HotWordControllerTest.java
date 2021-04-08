package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.api.response.base.ApiResponse;
import com.ifenghui.storybookapi.app.app.dao.DisplayGroupDao;
import com.ifenghui.storybookapi.app.app.dao.HotwordDao;
import com.ifenghui.storybookapi.app.app.entity.Hotword;
import com.ifenghui.storybookapi.app.app.response.GetHotwordsResponse;
import com.ifenghui.storybookapi.app.social.dao.ViewRecordDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;

import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.service.UserService;
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
public class HotWordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDao userDao;

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
    HotwordDao hotwordDao;



    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 测试查询阅读记录
     * @throws Exception
     */
    @Transactional
    @Test
    public void testGetHotwords()throws Exception{
        //创建数据
        Hotword hotword=new Hotword();
        hotword.setContent("asdf");
        hotword.setCreateTime(new Date());
        hotword.setStatus(1);
        hotword.setOrderBy(9990000);

        Hotword newHotword= hotwordDao.save(hotword);

//        Pageable pageable=new PageRequest(0, 10, new Sort("id"));
//        List list=viewRecordDao.getViewrecordsByUserId(newUser.getId(), pageable);

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/hotword/getHotwords?pageNo="+0+"&pageSize="+10))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        ApiResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetHotwordsResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
        GetHotwordsResponse getStoryDetailByIdResponse= objectMapper.readValue(result.getResponse().getContentAsString(),GetHotwordsResponse.class);
        if(getStoryDetailByIdResponse.getHotwords()==null
                ||getStoryDetailByIdResponse.getHotwords().size()==0
                ||newHotword.getId().intValue()!=getStoryDetailByIdResponse.getHotwords().get(0).getId().intValue()){
            Assert.assertTrue(false);
        }
        result=mockMvc.perform(MockMvcRequestBuilders.get("/api/hotword/getHotwords?pageNo="+0+"&pageSize="+10))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

}
