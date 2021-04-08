package com.ifenghui.storybookapi.app.transaction.service.lesson.impl;

import com.ifenghui.storybookapi.app.story.entity.Lesson;
import com.ifenghui.storybookapi.app.story.service.LessonService;
import com.ifenghui.storybookapi.app.transaction.dao.lesson.PayLessonPriceDao;
import com.ifenghui.storybookapi.app.transaction.entity.lesson.PayLessonPrice;
import com.ifenghui.storybookapi.app.transaction.service.lesson.BuyLessonItemRecordService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonOrderService;
import com.ifenghui.storybookapi.app.transaction.service.lesson.PayLessonPriceService;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PayLessonPriceServiceImpl implements PayLessonPriceService {

    @Autowired
    PayLessonPriceDao payLessonPriceDao;

    @Autowired
    BuyLessonItemRecordService buyLessonItemRecordService;

    @Autowired
    LessonService lessonService;

    @Autowired
    PayLessonOrderService payLessonOrderService;

    @Autowired
    WalletService walletService;

    @Override
    public List<PayLessonPrice> getPayLessonPriceList(){
        Pageable pageable = new PageRequest( 0, 100,new Sort(Sort.Direction.DESC,"orderBy"));
        Page<PayLessonPrice> payLessonPricePage = payLessonPriceDao.getAllByStatus(1, pageable);
        return new ArrayList<>(payLessonPricePage.getContent());
    }

    @Override
    public List<PayLessonPrice> getPayLessonPriceList(Integer lessonId, Integer userId) {

        List<PayLessonPrice> payLessonPriceList = this.getPayLessonPriceList();

        Integer buyLessonItemCount;

        Lesson lesson = lessonService.getLessonById(lessonId);
        if(!userId.equals(0)){
            /**
             * 已经购买的课程
             */
            Integer hasBuyLessonItemCount = buyLessonItemRecordService.getBuyLessonItemCount(userId, lessonId);
            buyLessonItemCount = lesson.getLessonItemCount() - hasBuyLessonItemCount;
        } else {
            buyLessonItemCount = lesson.getLessonItemCount();
        }

        Iterator<PayLessonPrice> iterator = payLessonPriceList.iterator();
        while(iterator.hasNext()) {
            PayLessonPrice item = iterator.next();
            item.setLessonNum(item.getLessonItemCount());
            item.setOrderDiscount(item.getDiscount());
            item.setLessonId(lessonId);
            if(item.getLessonItemCount() <= buyLessonItemCount){
                item.setIsCan(1);
            } else {
                item.setIsCan(0);
            }

            if(item.getLessonItemCount().equals(0)){
                item.setLessonNum(buyLessonItemCount);
                item.setName("剩余" + buyLessonItemCount + "节课程");
                item.setOrderDiscount(this.getRemainPayLessonPriceDiscount(buyLessonItemCount));
            }

            Integer perPrice = lesson.getPerPrice();
            Integer lessonNum = item.getLessonNum();
            Float orderDiscount = (float) item.getOrderDiscount() / 100;
            Float price = perPrice * lessonNum * orderDiscount;
            item.setPrice(price.intValue());
            //TODO 课程章节修改待用
            if((item.getId().equals(5) && item.getLessonNum().equals(50)) || (item.getId().equals(5) && item.getLessonNum().equals(0))){
//            if((item.getId().equals(5) && item.getLessonNum().equals(38)) || (item.getId().equals(5) && item.getLessonNum().equals(0))){
                iterator.remove();
            }
        }

        return payLessonPriceList;
    }

    /**
     * 根据课程章节数得到折扣数量
     * @param lessonItemCount
     * @return
     */
    @Override
    public Integer getRemainPayLessonPriceDiscount(Integer lessonItemCount) {
        Integer remainDiscount = 100;
        if(lessonItemCount >= 5 && lessonItemCount <= 9){
            remainDiscount = 90;
        } else if(lessonItemCount >= 10 && lessonItemCount <= 19){
            remainDiscount = 85;
        } else if(lessonItemCount >= 20 && lessonItemCount <= 29){
            remainDiscount = 80;
        } else if(lessonItemCount >= 30 && lessonItemCount <= 39){
            remainDiscount = 75;
        } else if(lessonItemCount >= 40 && lessonItemCount <= 49){
            remainDiscount = 70;
        } else if(lessonItemCount.equals(50)){
            remainDiscount = 67;
        }
        return remainDiscount;
    }

    @Override
    public PayLessonPrice getPayLessonPrice(Integer payLessonPriceId, Integer lessonNum) {
        PayLessonPrice payLessonPrice = payLessonPriceDao.findOne(payLessonPriceId);
        payLessonPrice.setLessonNum(lessonNum);
        return payLessonPrice;
    }

    @Override
    public PayLessonPrice getPayLessonPriceItem(Integer priceId, Integer lessonId) {
        PayLessonPrice item = payLessonPriceDao.findOne(priceId);
        item.setLessonNum(item.getLessonItemCount());
        item.setOrderDiscount(item.getDiscount());
        item.setLessonId(lessonId);
        item.setIsCan(1);
        Lesson lesson = lessonService.getLessonById(lessonId);
        Integer perPrice = lesson.getPerPrice();
        Integer lessonNum = item.getLessonNum();
        Float orderDiscount = (float) item.getOrderDiscount() / 100;
        Float price = perPrice * lessonNum * orderDiscount;
        item.setPrice(price.intValue());
        return item;
    }
}
