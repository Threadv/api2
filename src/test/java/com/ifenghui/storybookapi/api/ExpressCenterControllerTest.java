package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.app.dao.DisplayGroupDao;
import com.ifenghui.storybookapi.app.app.dao.FeedbackDao;
import com.ifenghui.storybookapi.app.app.response.AddFeedbackResponse;
import com.ifenghui.storybookapi.app.express.response.ExpressCenterOrdersResponse;
import com.ifenghui.storybookapi.app.social.dao.ViewRecordDao;
import com.ifenghui.storybookapi.app.story.dao.StoryDao;
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
public class ExpressCenterControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserTestService userTestService;




    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();


    @Transactional
    @Test
    public void testExpressCenterOrders()throws Exception{
        //创建数据


        UserToken userToken=userTestService.getUserToken();

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/expresscenter/order/get_express_center_orders_token").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("sstoken",userToken.getToken())
                .header("userType","1")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ExpressCenterOrdersResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),ExpressCenterOrdersResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }

    }

}
