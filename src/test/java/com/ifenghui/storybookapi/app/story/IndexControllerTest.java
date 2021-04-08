package com.ifenghui.storybookapi.app.story;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ifenghui.storybookapi.app.social.response.GetViewRecordsByUserIdResponse;
import com.ifenghui.storybookapi.app.story.response.GetIndex280Response;
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

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //mockmvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Rollback
public class IndexControllerTest {
    @Autowired
    MockMvc mockMvc;

    String userAgent="phone_version:iPhone X; ios_version:iOS 11.4; app_version:2.12.0;";
    ObjectMapper objectMapper =new ObjectMapper();
    @Test
    public void testIndex2_12_0() throws Exception {

        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/api/index/getIndex2_12_0").header("User-Agent",userAgent))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Gson gson=new Gson();
        GetIndex280Response apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetIndex280Response.class);
        if(apireponse.getStatus().getCode()!=1){
            Assert.assertTrue(false);
        }
        if( apireponse.getAudioSerialGroup().getSerialStories().size()==0){
            //音频合集没有数据
            Assert.assertTrue(false);
        }
    }
}
