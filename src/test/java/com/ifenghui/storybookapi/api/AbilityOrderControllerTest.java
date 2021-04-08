package com.ifenghui.storybookapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifenghui.storybookapi.app.app.response.AddFeedbackResponse;
import com.ifenghui.storybookapi.app.transaction.dao.BuyStoryRecordDao;
import com.ifenghui.storybookapi.app.transaction.response.GetAbilityPlanPrice211Response;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
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

/**
 * 宝宝会读的订单接口
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //mockmvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Rollback
public class AbilityOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserDao userDao;


    @Autowired
    UserService userService;




    @Autowired
    UserTestService userTestService;

    //jackson json转换工具
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 测试宝宝会读获取价格,创建是否会有异常
     * @throws Exception
     */
    @Transactional
    @Test
    public void testAbilityGetPrice()throws Exception {
        //创建数据

        UserToken newToken = userTestService.getUserToken();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/abilityPlanBuy/get_ability_plan_price_211").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("sstoken",newToken.getToken())
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        GetAbilityPlanPrice211Response apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetAbilityPlanPrice211Response.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
        result = mockMvc.perform(MockMvcRequestBuilders.get("/api/abilityPlanBuy/get_ability_plan_price_211").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("sstoken",newToken.getToken())
                .param("onlineOnly", "1")
        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetAbilityPlanPrice211Response.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }



    }




}
