package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ifenghui.storybookapi.api.response.base.ApiResponse;

import com.ifenghui.storybookapi.app.user.dao.UserAccountDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.response.*;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.service.UserTestService;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
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
@Rollback
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDao userDao;
    @Autowired
    private Environment env;

    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    UserTokenDao userTokenDao;

    @Autowired
    UserTestService userTestService;


    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    public void testGetUser()throws Exception{
        //创建数据

        //add测试数据
        User newUser=userTestService.getUser();

        UserToken newUser2=userTestService.getUserToken();


        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/user/getUser")
                .param("token",newUser2.getToken()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        ApiResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetUserResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
        result=mockMvc.perform(MockMvcRequestBuilders.get("/api/user/getUser").param("token",newUser2.getToken()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        GetUserResponse getUserResponse= objectMapper.readValue(result.getResponse().getContentAsString(),GetUserResponse.class);
        if(getUserResponse.getUserToken()==null||getUserResponse.getUserToken().getUser().getId()!=newUser.getId().intValue()){
            Assert.assertTrue(false);
        }

//        }
    }

    @Transactional
    @Test
    public void testPhoneReg() throws Exception {
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/phoneRegister").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("phone", "13800238000")
                .param("device", "iphone 4.5")
                .param("password", "12345678901234567890123456789012")
                .header("User-Agent", "ua")

        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ApiResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),PhoneRegisterResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
        result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/phoneRegister").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("phone", "13800238000")
                .param("device", "iphone 4.5")
                .param("password", "12345678901234567890123456789012")
                .header("User-Agent", "ua")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),UserTokenResponse.class);
        if(apireponse.getStatus().getCode()!=2){
            Assert.assertTrue(false);
        }
    }


    @Transactional
    @Test
    public void testOtherReg() throws Exception {
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/otherRegister").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("phone", "13800238000")
                .param("device", "iphone 4.5")
                .param("password", "12345678901234567890123456789012")
                .param("type", "1")
                .param("srcId", "123456")
                .header("User-Agent", "ua")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ApiResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),OtherRegisterResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
        result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/phoneRegister").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("phone", "13800238000")
                .param("device", "iphone 4.5")
                .param("password", "12345678901234567890123456789012")
                .param("type", "1")
                .param("srcId", "123456")
                .header("User-Agent", "ua")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),OtherRegisterResponse.class);
        if(apireponse.getStatus().getCode()!=2){
            Assert.assertTrue(false);
        }
    }


    @Transactional
    @Test
    public void testPhoneLogin() throws Exception {
        User user=userTestService.getUser();
//        UserToken newToken = userTestService.getUserToken();




        //增加用户
//        User user=new User.Builder().initAdd().setPhone("13800138000").setPassword("12345678901234567890123456789012").build();
//        userDao.save(user);
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/phoneLogin").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("phone", user.getPhone())
                .param("device", "iphone 4.5")
                .param("password", user.getPassword())
                .header("User-Agent", "ua")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    PhoneLoginResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),PhoneLoginResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
    }


    @Transactional
    @Test
    public void testOtherLogin() throws Exception {

        UserAccount userAccount=userTestService.getOtherUserAccount();

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/otherLogin").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("srcId", "13800238000")
                .param("device", "iphone 4.5")
                .param("type", "13")
                .header("User-Agent", "ua")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        PhoneLoginResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),PhoneLoginResponse.class);
        if(apireponse.getStatus().getCode()!=5){
            Assert.assertTrue(false);
        }


//        userAccount.setSrcType(13);

//        UserAccount newUserAccount=userAccountDao.save(userAccount);

        result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/otherLogin").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("srcId", userAccount.getSrcId())
                .param("device", "iphone 4.5")
                .param("type", ""+userAccount.getSrcType())
                .header("User-Agent", "ua")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),PhoneLoginResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
    }



    @Transactional
    @Test
    public void testfinishUser() throws Exception {

        User user=userTestService.getUser();
        UserToken newToken=userTestService.getUserToken();


        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/finishUser").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("token", "13800138000")
                .param("nick", "iphone 4.5")
                .param("sex", "13")
                .param("addr", "13")
                .param("birthday", "2015-01-13")
                .param("slogen", "13")
                .param("avatar", "13")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        FinishUserResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),FinishUserResponse.class);
        if(apireponse.getStatus().getCode()!=new ApiNotTokenException().getApicode()){
            Assert.assertTrue(false);
        }

        result=mockMvc.perform(MockMvcRequestBuilders.post("/api/user/finishUser").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("token", newToken.getToken())
                .param("nick", "iphone 4.5")
                .param("sex", "3")
                .param("addr", "13")
                .param("birthday", "2015-01-13")
                .param("slogen", "13")
                .param("avatar", "13")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),FinishUserResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }

//        User finduser=userDao.findOne(user.getId());
//        if(finduser.getSex()!=3||finduser.getAvatar().equals(env.getProperty("cmsconfig.oss.post")+"/"+"13")==false||finduser.getSlogen().equals("13")==false){
//            Assert.assertTrue(false);
//        }

    }

    @Transactional
    @Test
    public void testupdatePasswd() throws Exception {

        User user=userTestService.getUser();
        UserToken newToken = userTestService.getUserToken();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/updatePasswd").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("token", newToken.getToken())
                .param("oldPassword", user.getPassword())
                .param("newPassword", user.getPassword())
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        UpdatePasswdResponse apireponse = objectMapper.readValue(result.getResponse().getContentAsString(), UpdatePasswdResponse.class);

//        if(apireponse.getStatus().getCode()!=6 &&  apireponse.getStatus().getCode()!=7){
//            Assert.assertTrue(false);
//        }

        User finduser=userDao.findOne(user.getId());
        if(!finduser.getPassword().equals(user.getPassword())){
            Assert.assertTrue(false);
        }

    }

    @Transactional
    @Test
    public void testforgetPasswd() throws Exception {

//        User user = new User.Builder().initAdd().setPhone("138001382542").setPassword("111111").build();
        User newUser = userTestService.getUser();


//        UserToken userToken = new UserToken();
//        userToken.setUser(newUser);
//        userToken.setCreateTime(new Date());
//        userToken.setToken("123");
//        userToken.setRefreshToken("123");
//        userToken.setDevice("333333");
//        userToken.setUserAgent("ua");
//        UserToken userToken = userTestService.getUserToken();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/forgetPasswd").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("phone", newUser.getPhone())
                .param("device", "333333")
                .param("password", "111111")
                .header("User-Agent","ua")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        ForgetPasswdResponse apireponse = objectMapper.readValue(result.getResponse().getContentAsString(), ForgetPasswdResponse.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }

        User finduser=userDao.findOne(newUser.getId());
        if(!finduser.getPassword().equals("111111")){
            Assert.assertTrue(false);
        }

    }



}
