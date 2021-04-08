package com.ifenghui.storybookapi.dao;

import com.ifenghui.storybookapi.app.story.dao.SerialStoryDao;
import com.ifenghui.storybookapi.app.story.entity.SerialStory;
import com.ifenghui.storybookapi.app.wallet.dao.PayCallbackRecordDao;
import com.ifenghui.storybookapi.app.wallet.entity.PayCallbackRecord;

import com.ifenghui.storybookapi.style.OrderPayStyle;
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

import java.util.Date;

/**
 * Created by wslhk on 2017/1/5.
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc //mockmvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@Rollback
//@Transactional
public class PayDaoTest {

    @Autowired
    PayCallbackRecordDao payCallbackRecordDao;

    @Autowired
    SerialStoryDao serialStoryDao;

    @Test
    public void testPayCallbackRecord(){
        String a="";
//        Page<GroupRelevance> page= groupRelevanceDao.getGroupRelevancesByGroupIdAndStatus(1l,1,new PageRequest(1,1));
//
//        this.groupRelevanceService.getGroupRevelanceByGroupId(1l,1,1);
//        page=page;

//        PayCallbackRecord payCallbackRecord=new PayCallbackRecord();
//        payCallbackRecord.setPayType(OrderPayStyle.HUAWEI_PAY);
//        payCallbackRecord.setCreateTime(new Date());
//        payCallbackRecord.setOrderCode("xxx1");
//        payCallbackRecord.setOrderId(1L);
//        payCallbackRecord.setCallbackMsg("asdf");
//        PayCallbackRecord payCallbackRecord2= payCallbackRecordDao.save(payCallbackRecord);

//        payCallbackRecord2=payCallbackRecord2;
//        payCallbackRecordDao.findOneByOrderId()
    }

    @Test
    public void testCache(){
        SerialStory serialStory= serialStoryDao.findOne(1L);
        int memfree=serialStory.getMemberFree();
        serialStory.setMemberFree(0);
        serialStoryDao.save(serialStory);
        serialStory= serialStoryDao.findOne(1L);
        int memfree2=serialStory.getMemberFree();

        serialStory.setMemberFree(1);
        serialStoryDao.save(serialStory);
        serialStory= serialStoryDao.findOne(1L);
        int memfree3=serialStory.getMemberFree();

        memfree3=memfree3;
    }
}
