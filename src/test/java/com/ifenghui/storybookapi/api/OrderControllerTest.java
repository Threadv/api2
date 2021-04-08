package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;


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
 * Created by jch on 2017/1/9
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //mockmvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Rollback
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDao userDao;


    @Autowired
    UserService userService;
    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;



    @Autowired
    UserTestService userTestService;

    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    public void testcreateOrder()throws Exception {
        //创建数据

        UserToken newToken = userTestService.getUserToken();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/order/createOrder").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("token", newToken.getToken())
                .param("payTarget", "1")
                .param("buyType", "2")
                .param("targetType", "2")
                .param("amount", "20")
                .param("month","1")
                .param("dealerId", "0")
//                .param("payAccount", "125566")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();




//        Long vipCardId= Long.parseLong(payOrder.getOrderCode().split("_")[1]);
//
//        VipCard vipCard=vipCardDao.getOne(vipCardId);
//        if(vipCard==null){
//            Assert.assertTrue(false);
//        }



    }




}
