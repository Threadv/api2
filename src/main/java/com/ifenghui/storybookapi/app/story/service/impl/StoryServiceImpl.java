package com.ifenghui.storybookapi.app.story.service.impl;

import com.ifenghui.storybookapi.app.analysis.dao.StoryHotSaleDao;
import com.ifenghui.storybookapi.app.analysis.dao.StoryRecommendDao;
import com.ifenghui.storybookapi.app.analysis.entity.StoryHotSale;
import com.ifenghui.storybookapi.app.analysis.entity.StoryRecommend;
import com.ifenghui.storybookapi.app.app.dao.AdDao;
import com.ifenghui.storybookapi.app.app.dao.DisplayGroupDao;
import com.ifenghui.storybookapi.app.app.entity.DisplayGroup;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;
import com.ifenghui.storybookapi.app.social.service.UserReadRecordService;
import com.ifenghui.storybookapi.app.story.dao.*;
import com.ifenghui.storybookapi.app.story.entity.*;
import com.ifenghui.storybookapi.app.story.service.LessonItemRelateService;
import com.ifenghui.storybookapi.app.story.service.SerialStoryService;
import com.ifenghui.storybookapi.app.story.service.StoryService;
import com.ifenghui.storybookapi.app.story.service.VipSerialGetRecordService;
import com.ifenghui.storybookapi.app.studyplan.service.WeekPlanTaskRelateService;
import com.ifenghui.storybookapi.app.system.service.ElasticService;
import com.ifenghui.storybookapi.app.transaction.dao.*;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.BuyMagazineRecord;
import com.ifenghui.storybookapi.app.transaction.entity.subscription.SubscriptionRecord;
import com.ifenghui.storybookapi.app.transaction.service.UserAbilityPlanRelateService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.SerialStoryStyle;
import com.ifenghui.storybookapi.style.SvipStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by wml on 2016/12/23.
 */
@Component
public class StoryServiceImpl implements StoryService {

    @Autowired
    StoryDao storyDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    SerialStoryDao serialStoryDao;
    @Autowired
    BuySerialStoryRecordDao buySerialStoryRecordDao;
    @Autowired
    BuyStoryRecordDao buyStoryRecordDao;
    @Autowired
    BuyMagazineRecordDao buyMagazineRecordDao;
    @Autowired
    LabelStoryDao labelStoryDao;


    @Autowired
    LabelDao labelDao;


    @Autowired
    AdDao adsDao;

    @Autowired
    StoryHotSaleDao storyHotSaleDao;

//    @Autowired
//    BookPriceDao bookPriceDao;

    @Autowired
    DisplayGroupDao displayGroupDao;

    @Autowired
    StoryPackageDao storyPackageDao;

    @Autowired
    SubscriptionRecordDao subscriptionRecordDao;

    @Autowired
    SerialStoryService serialStoryService;

    @Autowired
    StoryRecommendDao storyRecommendDao;
    private static Logger logger = Logger.getLogger(StoryServiceImpl.class);

    @Autowired
    UserReadRecordService userReadRecordService;

    @Autowired
    StoryVideoIntroDao storyVideoIntroDao;

    @Autowired
    LessonItemRelateService lessonItemRelateService;

    @Autowired
    VipSerialGetRecordService vipSerialGetRecordService;

    @Autowired
    UserService userService;

    @Autowired
    UserAbilityPlanRelateService userAbilityPlanRelateService;

    @Autowired
    WeekPlanTaskRelateService weekPlanTaskRelateService;

    @Autowired
    ElasticService elasticService;

    @Autowired
    StoryAudioContentDao storyAudioContentDao;

    @Override
    public Story getStory(long id) {
        Story story = storyDao.findOne(id);
        if (story == null) {
            throw new ApiNotFoundException("没有对应故事！");
        }
        return story;
    }

//    @Override
//    public BookPrice getBookPriceByStoryId(Long storyId) {
//        return bookPriceDao.getBookPriceByStoryId(storyId);
//    }

    @Override
    public Page<Story> getStorysByCategoryId(Integer categoryId, Long userId, int pageNo, int pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "orderBy"));
        Integer isShow = 1;//查询需要显示的故事
        Story storyFind = new Story();
        storyFind.setCategoryId(categoryId);
        storyFind.setIsShow(isShow);
        storyFind.setStatus(1);
        storyFind.setIsNow(0);
        storyFind.setIsDel(0);
        storyFind.setScheduleType(0);
//        Page<Story> storys=this.storyDao.findAll(Example.of(storyFind),pageable);//  .findAllStoryByCategoryIdAndIsShow(categoryId,isShow,pageable);
        Page<Story> storys = storyDao.findAllStoryByCategoryIdAndIsShow(categoryId, isShow, pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }
        return storys;
    }

    @Override
    public String getStoryAppFile(Story story, Integer engineType) {
//        Story story = storyDao.findOne(storyId);
        StoryPackage storyPackageFind = new StoryPackage();
//        storyPackageFind.setStory(story);
        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id"));
        List<StoryPackage> storyPackages = storyPackageDao.findByStoryIdAndEngineTypeAndStatus(story.getId().intValue(), engineType, 1, pageable);
//        storyPackageFind.setStoryId(story.getId().intValue());
//        storyPackageFind.setEngineType(engineType);
//        storyPackageFind.setStatus(1);
//        Pageable pageable = new PageRequest(0, 1, new Sort(Sort.Direction.DESC,"id"));
//
//        Page<StoryPackage> storyPackagePage = storyPackageDao.findAll(Example.of(storyPackageFind),pageable);
        if (storyPackages.size() == 0) {
            return "";
        }
        return storyPackages.get(0).getAppFile();
    }


    //处理故事appFile
    @Override
    public void setStoryAppFile(Story story) {
        if (story == null) {
            return;
        }

        //全是新引擎，不再做版本判断
        int engineType = 2;

        if (story.getType().equals(2) || story.getType().equals(9)) {
            engineType = 1;
        }

        //获取filePath
        String filePath = this.getStoryAppFile(story, engineType);

        story.setAppFile(filePath);
    }

//    countBuySerialStoryRecordByUserIdAndSerialStoryId

    /**
     * 处理故事isBuy
     * 判断当前故事是否有阅读权限
     * 单本/订阅查单本订单记录或关联记录，故事集查故事集购买记录，课程查课程购买记录
     * <p>
     * 系列购买不再需要添加单本记录
     *
     * @param user
     * @param story
     */
    @Override
    public void setStoryIsBuy(User user, Story story) {
        if (story == null) {
            return;
        }
        story.setIsBuy(0);
        story.setIsPurchased(0);
        if (user == null) {
            return;
        }
        /**
         *  1 查看单本购买记录
         */
        Long buyStoryRecord = this.buyStoryRecordDao.countByUserIdAndStoryId(user.getId(), story.getId());
        if (buyStoryRecord > 0) {
            story.setIsBuy(1);
            story.setIsPurchased(1);
            return;
        }
        // 2故事集
        if (story.getSerialStoryId() != 0) {
            Long serialStoryRecordCount = buySerialStoryRecordDao.countBuySerialStoryRecordByUserIdAndSerialStoryId(user.getId().intValue(), story.getSerialStoryId().intValue());

            SerialStory serialStory = serialStoryService.getSerialStory(story.getSerialStoryId());
            if (serialStory == null) {
                return;
            } else if (serialStory.getPrice().equals(0)) {
                //如果价格免费
                story.setIsBuy(1);
                story.setIsPurchased(1);
                return;
            } else if (serialStoryRecordCount > 0) {
                //如果有购买记录
                story.setIsBuy(1);
                story.setIsPurchased(1);
                return;

            } else if (serialStory.getSerialStyle()==SerialStoryStyle.INDEX_SERIAL) {
                if(user.getIsAbilityPlanAndVip()>0&&(serialStory.getMemberFree()!=null&&serialStory.getMemberFree()==1)){
                    //如果是优能计划用户，并且故事集会员可见
                    story.setIsBuy(1);
                    story.setIsPurchased(1);
                    return;
                }else{
                    story.setIsBuy(0);
                    story.setIsPurchased(0);
                    return;
                }

            }
        }
        //3 课程
        boolean flag = lessonItemRelateService.checkIsBuy(story.getId().intValue(), user.getId().intValue());
        if (flag) {
            story.setIsBuy(1);
            story.setIsPurchased(1);
            return;
        }

        if (user.getSvip().equals(SvipStyle.LEVEL_TWO.getId())) {
            story.setIsBuy(4);
            story.setIsPurchased(1);
            return;
        }
        if (user.getSvip().equals(SvipStyle.LEVEL_ONE.getId()) && story.getIsNow() == 1) {
            story.setIsBuy(4);
            story.setIsPurchased(1);
            return;
        }
        if (user.getSvip().equals(SvipStyle.LEVEL_THREE.getId())) {
            story.setIsBuy(4);
            story.setIsPurchased(1);
            return;
        }

        if (user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId())) {
            story.setIsBuy(4);
            story.setIsPurchased(1);
            return;
        }
        //isbuy 设置为4 为畅读权限 v2.11.0
        if (user.getIsAbilityPlan() == 1) {
            story.setIsBuy(4);
            story.setIsPurchased(1);
            return;
        }
    }

    @Override
    public Page<Story> getStorysBySerialId(Long serialStoryId, Long userId, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "orderBy", "id"));
        Integer isBuySerial = this.getIsBuySerial(userId.intValue(), serialStoryId.intValue());
        Page<Story> storys = storyDao.getStorysBySerialStoryId(serialStoryId, pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            item.setIsBuySerial(isBuySerial);
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
            item.setIsShowSerialIcon(this.getIsShowSerialIcon(item, serialStoryId, isBuySerial));
        }
        return storys;
    }

    /**
     * 获得该故事所属故事集 用户是否买过 0 未买 1买过
     *
     * @param userId
     * @param serialStoryId
     * @return
     */
    private Integer getIsBuySerial(Integer userId, Integer serialStoryId) {
//        BuySerialStoryRecord buySerialStoryRecord = new BuySerialStoryRecord();
//        buySerialStoryRecord = buySerialStoryRecordDao.getBuySerialStoryRecordByUserIdAndSerialStoryId(userId,serialStoryId);
        Long buySerialStoryrEecordCount = buySerialStoryRecordDao.countBuySerialStoryRecordByUserIdAndSerialStoryId(userId, serialStoryId);
        Integer isBuySerial = 0;
        if (buySerialStoryrEecordCount != null && buySerialStoryrEecordCount > 0) {
            isBuySerial = 1;
        } else {
            SerialStory serialStory = serialStoryService.getSerialStory(serialStoryId.longValue());
            if (serialStory.getType().equals(SerialStoryStyle.PARENT_LESSON_SERIAL.getId())) {
                User user = userService.getUser(userId.longValue());
                if (user != null) {
                    if (user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId()) || user.getSvip().equals(SvipStyle.LEVEL_THREE.getId())) {
                        isBuySerial = vipSerialGetRecordService.isGetVipSerialRecord(userId, serialStoryId);
                    } else if (serialStoryId == 13 && user.getIsAbilityPlan() == 1) {
                        //大咖（家长课）13 宝宝会读（优能计划）1
                        isBuySerial = 1;
                    }
                }
            } else if (serialStory.getType().equals(SerialStoryStyle.AUDIO_SERIAL.getId())) {
                User user = userService.getUser(userId.longValue());
                if (user != null && user.getIsAbilityPlan() == 1) {
                    isBuySerial = 1;
                }
            }else if(serialStory.getMemberFree()==1){
                //会员免费状态
                User user = userService.getUser(userId.longValue());
                if(user!=null){
                    if(user.getIsAbilityPlanAndVip()>0){
                        isBuySerial = 1;
                    }
                }

            }
        }

        return isBuySerial;
    }

    @Override
    public Page<Story> getStorysByMagazineId(Long magazineId, Long userId, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "orderBy", "id"));
        Page<Story> storys = this.storyDao.getStorysByMagazineId(magazineId, pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
        }
        return storys;
    }

    @Override
    public Page<Story> getStorysByIsNow(Integer isNow, Long userId, Integer pageNo, Integer pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "orderBy", "id"));
        Page<Story> storys = this.storyDao.getStorysByIsNow(isNow, pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }
        return storys;
    }

    @Override
    public Page<Story> getMoreStorys(Long userId, Long storyId, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Story story = storyDao.findOne(storyId);
        Page<Story> storys = this.storyDao.getMoreStorysByType(storyId, story.getType(), pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }
        return storys;
    }

    @Override
    public Page<Story> getOtherStorys(Long userId, Long storyId, int pageNo, int pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));

        Page<Story> storys = this.storyDao.getOtherStorys(storyId, pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }
        return storys;
    }

    @Override
    public Page<StoryRecommend> getRecommendStorys(Long userId, Long storyId, int pageNo, int pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<StoryRecommend> reStorys = this.storyRecommendDao.getStoryRecommends(storyId, pageable);
        User user = userService.getUser(userId);
        for (StoryRecommend item : reStorys.getContent()) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item.getStory());
            this.setStoryAppFile(item.getStory());
        }
        return reStorys;
    }

    @Override
    public Integer isSubscribe(Long userId) {
        Integer isSubscribe = 0;
        Integer pageNo = 0;
        Integer pageSize = 1;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        //查询订阅时间最后一条记录
        User user = userService.getUser(userId);
        Page<SubscriptionRecord> subscriptionRecordPage = subscriptionRecordDao.getSubscriptionRecordsByUserId(user, pageable);
        if (subscriptionRecordPage.getContent().size() > 0) {
            //判断最后一条结束时间，是否晚于当前时间，否则是订阅过期，即未订阅
            Date nowTime = new Date();
            long nowTimeStemp = nowTime.getTime();//当前时间戳
            for (SubscriptionRecord item : subscriptionRecordPage.getContent()) {
                long endTimeStemp = item.getEndTime().getTime();//上一条记录结束时间戳
                long dv = nowTimeStemp - endTimeStemp;
                if (dv > 0) {//过期
                    isSubscribe = 0;
                } else {
                    isSubscribe = 1;
                }
            }
        } else {
            isSubscribe = 0;
        }

        return isSubscribe;
    }

    @Override
    public Integer getStorysCount(Long storyId) {
        Integer count = storyDao.getStorysCount(storyId);
        return count;
    }

    @Override
    public Page<Story> getNowStorys(Long userId, Long storyId, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<Story> storysPage = this.storyDao.getNowStorys(storyId, pageable);
        User user = userService.getUser(userId);
        for (Story item : storysPage) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }
        return storysPage;
    }

    @Override
    public Story getStoryDetailById(Long id, Long userId) {

        Story story = this.storyDao.findOne(id);
        if (story == null) {
            return null;
        }
        Label label;
        //判断此用户是否已购买
        User user = null;
        if (userId == 0) {
            user = null;
        } else {
            user = userService.getUser(userId);
        }

        this.setStoryIsBuy(user, story);
        //获取此故事标签
        List<LabelStory> labelStorys = this.labelStoryDao.findAllStoryLabelsByStoryId(id);
        story.setLabels(new ArrayList());

        for (LabelStory item : labelStorys) {

            label = this.labelDao.findOneLabelById(item.getLabelId());
            story.getLabels().add(label);
        }

        return story;

    }

    @Override
    public Story getStoryBookDetailById(Long id, Long userId) {

        Story story = this.storyDao.findOne(id);
        //查询单行本价格id
//        BookPrice bookPrice = this.bookPriceDao.getBookPriceByStoryId(id);

//        story.setAndroidPriceId(600);
//        story.setIosPriceId(600);
//        story.getIosPrice().setIap("");
//        story.setShopUrl("");

        Label label;
        //判断此用户是否已购买
        User user = userService.getUser(userId);
        this.setStoryIsBuy(user, story);
        //获取此故事标签
        List<LabelStory> labelStorys = this.labelStoryDao.findAllStoryLabelsByStoryId(id);
        story.setLabels(new ArrayList());
        for (LabelStory item : labelStorys) {

            label = this.labelDao.findOneLabelById(item.getLabelId());
            story.getLabels().add(label);
        }
        return story;
    }

    @Override
    public Page<Story> getStorysBySearch(Long userId, String content, Integer pageNo, Integer pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<Story> storys = this.storyDao.getStorysBySearch(content, pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }
        return storys;

    }

    @Override
    public Page<Story> getNotNowStorysBySearch(Long userId, String content, Integer pageNo, Integer pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<Story> storys = this.storyDao.getNotNowStorysBySearch(content, pageable);
        User user = userService.getUser(userId);
        for (Story item : storys) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }
        return storys;

    }

    @Override
    public List<Story> getStoryByMaps(Long userId, List<Map> maps, HttpServletRequest request) {

        User user = userService.getUser(userId);
        List<Story> stories = new ArrayList<>();
        for (Map map : maps) {
            long id = Long.valueOf(String.valueOf(map.get("id")));
            Story story = storyDao.findOne(id);
            stories.add(story);
        }
        for (Story item : stories) {
            //判断此用户是否已购买
            this.setStoryIsBuy(user, item);
            this.setStoryAppFile(item);
        }

        return stories;
    }


    @Override
    public Page<BuyStoryRecord> getUserBuyStoryRecords(Long userId, Integer type, int pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<BuyStoryRecord> buyStoryRecords;
        if (type == 0) {
            //两种类型都获取
            buyStoryRecords = this.buyStoryRecordDao.getBuyStoryRecordsByUserIdAndPage(userId, pageable);
        } else {
            buyStoryRecords = this.buyStoryRecordDao.getBuyStoryRecordsByUserIdAndType(userId, type, pageable);
        }
        for (BuyStoryRecord item : buyStoryRecords.getContent()) {
            item.getStory().setIsBuy(1);
            this.setStoryAppFile(item.getStory());
        }
        return buyStoryRecords;
    }

    @Override
    public Page<BuyStoryRecord> getUserStorys(Long userId, Integer type, Integer groupId, int pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<BuyStoryRecord> buyStoryRecords;
        if (groupId == 0) {
            //所有分组都获取
            if (type.equals(2)) {
                buyStoryRecords = this.buyStoryRecordDao.getBuyStoryRecordsByUserIdAndType(userId, type, pageable);
            } else {
                buyStoryRecords = this.buyStoryRecordDao.getBuyStoryRecords(userId, pageable); //查询单本故事，type=1,3,6
            }
        } else if (groupId.equals(8)) {
            buyStoryRecords = this.buyStoryRecordDao.getMusicBuyStoryRecordsByUserIdAndType(userId, type, pageable);
        } else {
            if (type.equals(2)) {
                buyStoryRecords = this.buyStoryRecordDao.getUserBuyRecordByTypeAndCatId(userId, type, groupId, pageable);
            } else {
                //查询单本故事，type=1，3
                buyStoryRecords = this.buyStoryRecordDao.getUserBuyStoryRecords(userId, groupId, pageable);
            }

        }

        for (BuyStoryRecord item : buyStoryRecords.getContent()) {
            item.getStory().setIsBuy(1);
            item.getStory().setIsPurchased(1);
            this.setStoryAppFile(item.getStory());
        }
        return buyStoryRecords;
    }

    @Override
    public Page<BuyStoryRecord> getUserHasBuyStorysByNewType(Long userId, Integer groupId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<BuyStoryRecord> buyStoryRecords;
        if (groupId.equals(0)) {
//            buyStoryRecords = buyStoryRecordDao.getAllUserBuyStoryRecordsByNewCategory(userId, pageable);

            buyStoryRecords = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndPage(userId, pageable);

        } else {
            buyStoryRecords = buyStoryRecordDao.getUserBuyStoryRecordsByNewCategory(userId, groupId, pageable);
        }

        for (BuyStoryRecord item : buyStoryRecords.getContent()) {
            item.getStory().setIsBuy(1);
            item.getStory().setIsPurchased(1);
            this.setStoryAppFile(item.getStory());
        }
        return buyStoryRecords;
    }


    @Override
    public List<Story> getUserBuyStorys(Long userId, Integer type, int pageNo, Integer pageSize, HttpServletRequest request) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "createTime", "id"));
        Page<BuyStoryRecord> buyStoryRecords;
        if (type == 0) {
            //两种类型都获取
            buyStoryRecords = this.buyStoryRecordDao.getBuyStoryRecordsByUserId(userId, pageable);
        } else {
            buyStoryRecords = this.buyStoryRecordDao.getBuyStoryRecordsByUserIdAndType(userId, type, pageable);
        }
        List<Story> storyList = new ArrayList<>();
        for (BuyStoryRecord item : buyStoryRecords.getContent()) {
            item.getStory().setIsBuy(1);
            item.getStory().setIsPurchased(1);
            this.setStoryAppFile(item.getStory());
            storyList.add(item.getStory());
        }

        return storyList;
    }

    @Override
    public Page<Story> getStorysByDateRange(Date beginDate, Date endDate, int pageSize) {
        if (pageSize > 32) {
            pageSize = 32;
        }
        return storyDao.getStorysByPublishDateRange(new java.sql.Date(beginDate.getTime()), new java.sql.Date(endDate.getTime())
                , new PageRequest(0, pageSize, new Sort(Sort.Direction.DESC, "id")));
    }

    @Transactional
    @Override
    public void moveToRecycle(long id) {
        Story story = storyDao.findOne(id);
        if (story == null || story.getIsDel() == 1) {
            return;
        }
        story.setIsDel(1);
        storyDao.save(story);
    }

    @Transactional
    @Override
    public void restoreByRecycle(long id) {
        Story story = storyDao.findOne(id);
        if (story == null || story.getIsDel() == 0) {
            return;
        }
        story.setIsDel(0);
        storyDao.save(story);
    }

    @Transactional
    @Override
    public Story saveStory(Story story) {
        return storyDao.save(story);
    }

    @Transactional
    @Override
    public Void addUserNewMagazineBuyRecord(Long magazineId) {
        //查询所有订阅期内用户

        Date time = new Date();
        Long userId;
        List<SubscriptionRecord> subscriptionRecords = subscriptionRecordDao.getSubscriptionRecordsByTime(time);
        //查询此期刊内的所有故事
        Integer pageNo = 0;
        Integer pageSize = 10;
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));

        Page<Story> storyPage = storyDao.getStorysByMagazineId(magazineId, pageable);
        List<BuyStoryRecord> buyStoryRecordList;
        Page<BuyMagazineRecord> buyMagazineRecordPage;
        //查询所有订阅用户
        for (SubscriptionRecord item : subscriptionRecords) {
            if (item.getUser() == null) {
                continue;
            }
            userId = item.getUser().getId();
            //给每个用户添加最新期刊购买记录和期刊内故事购买记录
            buyMagazineRecordPage = buyMagazineRecordDao.getRecordByUserIdAndMagazineId(userId, magazineId, pageable);
            if (buyMagazineRecordPage.getContent().size() == 0) {
                //添加购买期刊记录
                BuyMagazineRecord buyMagazineRecord = new BuyMagazineRecord();
                buyMagazineRecord.setMagazineId(magazineId);
                buyMagazineRecord.setUser(item.getUser());
                buyMagazineRecord.setCreateTime(new Date());
                buyMagazineRecordDao.save(buyMagazineRecord);
            }
            //添加故事购买记录
            for (Story storyItem : storyPage) {
                //添加购买故事记录
                BuyStoryRecord buyStoryRecord = new BuyStoryRecord();//必须new一个新的，不然会覆盖,值添加最后一条
                buyStoryRecordList = buyStoryRecordDao.getBuyStoryRecordsByUserIdAndStoryId(userId, storyItem.getId());
                if (buyStoryRecordList.size() == 0) {//添加过的数据不用添加
                    buyStoryRecord.setUserId(userId);
                    buyStoryRecord.setType(2);//2属于订阅
                    buyStoryRecord.setStory(storyItem);
                    buyStoryRecord.setCreateTime(new Date());
                    buyStoryRecordDao.save(buyStoryRecord);
                }
            }

        }
        return null;
    }

    @Override
    public Page<StoryHotSale> getMoreHotSaleStorys(Long userId, Integer pageNo, Integer pageSize) {
        User user = userService.getUser(userId);
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC, "id"));
        StoryHotSale storyHotSaleFind = new StoryHotSale();
        Page<StoryHotSale> storyHotSalePage = storyHotSaleDao.findAll(Example.of(storyHotSaleFind), pageable);
        for (StoryHotSale item : storyHotSalePage.getContent()) {
            this.setStoryIsBuy(user, item.getStory());
            this.setStoryAppFile(item.getStory());
        }
        return storyHotSalePage;
    }

//    @Override
//    public Page<SerialStory> getSerialStorys(Long userId, Integer pageNo, Integer pageSize){
//        User user = userDao.findOne(userId);
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
//        SerialStory serialStoryFind = new SerialStory();
//        serialStoryFind.setStatus(1);
//        Page<SerialStory> serialStoryPage = serialStoryDao.findAll(Example.of(serialStoryFind),pageable);
//        for (SerialStory item:serialStoryPage.getContent()){
//            this.setSerialStoryIsBuy(user,item);
//        }
//        return serialStoryPage;
//    }
//    @Override
//    public SerialStory getSerialStoryDetail(Long userId,Long serialStoryId){
//        User user = userDao.findOne(userId);
//
//        SerialStory  serialStory = serialStoryDao.findOne(serialStoryId);
//        if(serialStory == null) {
//            throw new ApiNotFoundException("没有找到该益智故事集！");
//        }
//        if(serialStory.getPrice().equals(0)){
//            serialStory.setIsBuy(1);
//        } else {
//            this.setSerialStoryIsBuy(user,serialStory);
//        }
//        return serialStory;
//    }
//    @Override
//    public GetUserSerialStorysResponse getUserSerialStorys(Long userId, Integer pageNo, Integer pageSize){
//        GetUserSerialStorysResponse response = new GetUserSerialStorysResponse();
//        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
//        //获取普通故事集 serialType=1 用户购买记录
//        Page<BuySerialStoryRecord> buySerialStoryRecordPage = buySerialStoryRecordDao.getBuySerialStoryRecordsByUserId(userId.intValue(),pageable);
//        response.setSerialStoryList(this.getBuySerialStoryRecordByUserIdAndSerialType(userId,buySerialStoryRecordPage));
//        response.setJpaPage(buySerialStoryRecordPage);
//        return response;
//    }
//    @Override
//    public List<SerialStory> getBuySerialStoryRecordByUserIdAndSerialType(Long userId, Page<BuySerialStoryRecord> buySerialStoryRecordPage){
//        User user = userDao.findOne(userId);
//        SerialStory serialStory;
//        List<SerialStory> serialStories = new ArrayList<>();
//        for (BuySerialStoryRecord item:buySerialStoryRecordPage.getContent()){
//            serialStory = serialStoryDao.findOne(item.getSerialStoryId().longValue());
//            this.setSerialStoryIsBuy(user,serialStory);
//            serialStories.add(serialStory);
//        }
//        return serialStories;
//    }

    @Override
    public List<DisplayGroup> getStoryGroups() {
        DisplayGroup displayGroupFind = new DisplayGroup();
        displayGroupFind.setIsShow(1);
        displayGroupFind.setStatus(1);
//        List<DisplayGroup> groups = displayGroupDao.findAll(Example.of(displayGroupFind));
        Sort sort = new Sort(Sort.Direction.DESC, "orderBy", "id");
        List<DisplayGroup> groups = displayGroupDao.findAll(Example.of(displayGroupFind), sort);

        return groups;
    }

    @Override
    public List<DisplayGroup> getNewStoryGroups() {
        return displayGroupDao.getDisplayGroupsByNewCategory();
    }

    @Override
    public List<DisplayGroup> getStoryGroupListByNewCategory() {
        return displayGroupDao.getDisplayGroupsByNewCategory();
    }

    @Override
    public Story getFirstReadStory(Long userId) {
        UserReadRecord userReadRecord = userReadRecordService.getFirstStoryReadRecordByUserId(userId, 1);
        if (userReadRecord != null) {
            Story story = this.getStoryDetailById(userReadRecord.getStoryId(), userId);
            return story;
        } else {
            return null;
        }
    }

    @Override
    public Story getFavoriteReadStory(Long userId) {
        Long storyId = userReadRecordService.getFavoriteUserReadRecord(userId, 1);
        if (storyId != null) {
            Story story = this.getStoryDetailById(storyId, userId);
            return story;
        } else {
            return null;
        }
    }

    @Override
    public List<Story> getStorySerialDetail(Long userId, Long serialStoryId) {
        List<Story> storyList = storyDao.getStoryListBySerialStoryId(serialStoryId);
        Integer isBuySerial = this.getIsBuySerial(userId.intValue(), serialStoryId.intValue());
        for (Story item : storyList) {
            //判断此用户是否已购买
            item.setIsBuySerial(isBuySerial);
            this.setStoryAppFile(item);
            item.setIsShowSerialIcon(this.getIsShowSerialIcon(item, serialStoryId, isBuySerial));
        }
        return storyList;
    }

    @Override
    public List<Story> getSecondStorySerialDetail(Long userId, Long serialStoryId, Long parentId) {
        Integer isBuySerial = this.getIsBuySerial(userId.intValue(), parentId.intValue());
        List<Story> storyList = storyDao.getStoryListBySecondSerialStoryId(serialStoryId);
        for (Story item : storyList) {
            item.setIsBuySerial(isBuySerial);
            this.setStoryAppFile(item);
            item.setIsShowSerialIcon(this.getIsShowSerialIcon(item, parentId, isBuySerial));
        }
        return storyList;
    }

    @Override
    public StoryVideoIntro getStoryVideoIntroByStoryId(Integer storyId) {
        return storyVideoIntroDao.findStoryVideoIntroByStoryId(storyId);
    }

    @Override
    public List<Story> getRadioList(Long userId) {

        List<Story> radioList = storyDao.getRadioList();
        User user = null;
        if (userId != 0L) {
            user = userService.getUser(userId);
        }
        for (Story s : radioList) {
            this.setStoryIsBuy(user, s);
            this.setStoryAppFile(s);
        }
        return radioList;
    }

    /**
     * 处理是否显示故事集中的付费图标字段
     *
     * @param story
     * @param serialStoryId
     * @param isBuySerial
     * @return
     */
    private Integer getIsShowSerialIcon(Story story, Long serialStoryId, Integer isBuySerial) {
        Integer isShowSerialIcon;
        if (!serialStoryId.equals(2L)) {

            if (isBuySerial.equals(1) || story.getIsFree().equals(1)) {
                isShowSerialIcon = 0;
            } else {
                isShowSerialIcon = 1;
            }
        } else {
            if (isBuySerial.equals(1)) {
                isShowSerialIcon = 0;
            } else {
                isShowSerialIcon = 1;
            }
        }
        if(story.getIsBuy()>0){
            isShowSerialIcon=0;
        }
        return isShowSerialIcon;
    }


    @Override
    public StoryAudioContent getStoryAudioContenById(Integer storyId) {

        return storyAudioContentDao.findByStoryId(storyId);
    }

    @Override
    public Page<Story> findAllByStory(Story story, PageRequest pageRequest) {
        return storyDao.findAll(apecStory(story),pageRequest);
    }

    /**
     * story混合条件搜索
     * @param story
     * @return
     */
    private Specification<Story> apecStory(Story story){
        return new Specification<Story>() {

            @Override
            public Predicate toPredicate(Root<Story> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<Predicate>();
                if(story.getName()!=null){
                    predicates.add(criteriaBuilder.like(root.get("name"),"%"+story.getName()+"%"));
                }
                if(story.getStatus()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("status"),story.getStatus()));
                }
                if(story.getSerialStoryId()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("serialStoryId"),story.getSerialStoryId()));
                }
//                if(story.getTargetTypeData()!=null){
//                    predicates.add(criteriaBuilder.equal(root.get("targetType"),story.getTargetTypeData()));
//                }
//                if(ads.getSuccessTimeBegin()!=null){
//                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"),ads.getSuccessTimeBegin()));
//                }
//                if(ads.getSuccessTimeEnd()!=null){
//                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"),ads.getSuccessTimeEnd()));
//                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
            }
        };
    }
}
