package com;


import com.DemoApplication;
import com.ifenghui.storybookapi.app.studyplan.dao.WeekPlanReadRecordDao;
import com.ifenghui.storybookapi.app.studyplan.entity.WeekPlanReadRecord;
import com.ifenghui.storybookapi.style.LabelTargetStyle;
import com.ifenghui.storybookapi.style.ReadRecordTypeStyle;
import com.ifenghui.storybookapi.style.WeekPlanStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by wslhk on 2016/12/26.
 */
//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
//@AutoConfigureMockMvc //mockmvc
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ComponentScan({"com.ifenghui.storybookapi.app.studyplan.dao","com.ifenghui.storybookapi.app.studyplan.entiey"})
//@EnableJpaRepositories({"com.ifenghui.storybookapi.app.studyplan.dao"})
//@EntityScan({"com.ifenghui.storybookapi.app.studyplan.entity"})
@Rollback
public class CacheTest {

//    @Autowired
//    CacheManager cacheManager;

//    @Autowired
//    MockMvc mockMvc;

//    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    WeekPlanReadRecordDao weekPlanReadRecordDao;

    @Transactional
    @Test
    public void testCache()throws Exception{
//        cacheManager.getCache("test").put("test_1",1);
//        Integer newid=cacheManager.getCache("test").get("test_1",Integer.class);
//        if(newid!=1){
//            Assert.assertTrue(false);
//        }

//        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/simplecms/test.action"))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        String respStr=result.getResponse().getContentAsString();
//        respStr=respStr;
//        ApiResponse apireponse=objectMapper.readValue(result.getResponse().getContentAsString(),GetUserResponse.class);
//        if(apireponse.getStatus().getCode()!=1){
//            Assert.assertTrue(false);
//        }

//        Integer sum=weekPlanReadRecordDao.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(1,1,1,1,1);
//        WeekPlanReadRecord w=new WeekPlanReadRecord();
//        w.setIsStory(1);
//        w.setCreateTime(new Date());
//        w.setWordCount(1);
//        w.setPlanType(WeekPlanStyle.TWO_FOUR);
//        w.setReadType(ReadRecordTypeStyle.STORY);
//        w.setLabelTargetType(LabelTargetStyle.Story);
//        w.setTargetValue(1);
//        w.setUserId(1);
//        weekPlanReadRecordDao.save(w);
//        sum=weekPlanReadRecordDao.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(1,1,1,1,1);
//        sum=weekPlanReadRecordDao.getSumWordCountByPlanTypeAndTargetTypeAndUserIdAndReadType(1,1,1,1,1);
//        sum=sum;
    }


}
