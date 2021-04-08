package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.response.GetUserGrowthImgPageByWeekNumResponse;
import com.ifenghui.storybookapi.app.social.dao.UserGrowthDiaryDao;
import com.ifenghui.storybookapi.app.social.dao.UserGrowthDiaryImgDao;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiary;
import com.ifenghui.storybookapi.app.social.entity.UserGrowthDiaryImg;
import com.ifenghui.storybookapi.app.social.service.ScheduleSmallImg;
import com.ifenghui.storybookapi.app.social.service.GrowthDiaryDay;
import com.ifenghui.storybookapi.app.social.service.GrowthDiaryWeek;
import com.ifenghui.storybookapi.app.social.service.UserGrowthDiaryImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UserGrowthDiaryImgServiceImpl implements UserGrowthDiaryImgService {

    @Autowired
    UserGrowthDiaryDao userGrowthDiaryDao;

    @Autowired
    UserGrowthDiaryImgDao userGrowthDiaryImgDao;

    @Override
    public void addUserGrowthDiaryImgList(List<ScheduleSmallImg> diaryImgList, Integer userId, Integer diaryId, Date recordDate) {
        if(diaryImgList != null){
            Collections.reverse(diaryImgList);
            for(ScheduleSmallImg item : diaryImgList){
                UserGrowthDiaryImg userGrowthDiaryImg = new UserGrowthDiaryImg();
                userGrowthDiaryImg.setCreateTime(new Date());
                userGrowthDiaryImg.setDiaryId(diaryId);
                userGrowthDiaryImg.setImgPath(item.getImgPath());
                userGrowthDiaryImg.setUserId(userId);
                userGrowthDiaryImg.setHeight(item.getHeight());
                userGrowthDiaryImg.setWidth(item.getWidth());
                userGrowthDiaryImg.setRecordDate(new Date());
                userGrowthDiaryImg.setStatus(1);
                userGrowthDiaryImg.setIsVideo(item.getIsVideo());
                if(item.getIsVideo() == null){
                    userGrowthDiaryImg.setIsVideo(0);
                } else {
                    userGrowthDiaryImg.setIsVideo(item.getIsVideo());
                }
                if(item.getVideoPath() == null){
                    userGrowthDiaryImg.setVideoPath("");
                } else {
                    userGrowthDiaryImg.setVideoPath(item.getVideoPath());
                }

                userGrowthDiaryImg.setWeekNum(this.getCurrentWeek(new Date()));
                userGrowthDiaryImgDao.save(userGrowthDiaryImg);
            }
        }
    }

    @Override
    public List<UserGrowthDiaryImg> getGrowthDiaryImgList(Integer diaryId) {
        UserGrowthDiaryImg userGrowthDiaryImg = new UserGrowthDiaryImg();
        userGrowthDiaryImg.setDiaryId(diaryId);
        List<UserGrowthDiaryImg> userGrowthDiaryImgList = userGrowthDiaryImgDao.findAll(Example.of(userGrowthDiaryImg));
        return userGrowthDiaryImgList;
    }

    @Override
    public void deleteUserGrowthDiaryImg(Integer diaryId, Integer userId) {
        UserGrowthDiaryImg userGrowthDiaryImg = new UserGrowthDiaryImg();
        userGrowthDiaryImg.setDiaryId(diaryId);
        userGrowthDiaryImg.setUserId(userId);
        List<UserGrowthDiaryImg> userGrowthDiaryImgList = userGrowthDiaryImgDao.findAll(Example.of(userGrowthDiaryImg));
        if(userGrowthDiaryImgList.size() > 0){
            for(UserGrowthDiaryImg item : userGrowthDiaryImgList){
                item.setStatus(0);
                userGrowthDiaryImgDao.save(item);
            }
        }
    }

    public Integer getCurrentWeek(Date time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String currentYear = simpleDateFormat.format(time);
        long realTime = time.getTime();
        long perWeek = 3600*24*7*1000;
        long week = realTime/perWeek;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        Integer dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek <= 4 && dayOfWeek > 1){
            week = week + 1;
        }
        String weekNum = currentYear + week;
        return Integer.parseInt(weekNum);
    }

    @Override
    public void recoverUserGrowthDiaryImgData() {
        List<UserGrowthDiaryImg> userGrowthDiaryImgList = userGrowthDiaryImgDao.findAll();
        for(UserGrowthDiaryImg item : userGrowthDiaryImgList){
            item.setWeekNum(this.getCurrentWeek(item.getRecordDate()));
            userGrowthDiaryImgDao.save(item);
        }
    }

    @Override
    public GetUserGrowthImgPageByWeekNumResponse getUserGrowthImgPageByWeekNum(Integer userId, Integer weekNum, Integer pageNo, Integer pageSize, Integer userGrowthImgId) {
        List<Sort.Order> orders=new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        List<UserGrowthDiaryImg> userGrowthDiaryImgList = null;
        Page<UserGrowthDiaryImg> userGrowthDiaryImgPage = null;

        if(!userGrowthImgId.equals(0)){
            Pageable pageable = new PageRequest(pageNo,pageSize, new Sort(orders));
            userGrowthDiaryImgPage = userGrowthDiaryImgDao.getUserGrowthImgPageByWeekNum(userId,weekNum,userGrowthImgId,pageable);
            userGrowthDiaryImgList = userGrowthDiaryImgPage.getContent();
        } else {
            Pageable pageable = new PageRequest(pageNo , pageSize, new Sort(orders));
            userGrowthDiaryImgPage = userGrowthDiaryImgDao.getUserGrowthImgPageByWeekNum(userId,weekNum,pageable);
            userGrowthDiaryImgList = userGrowthDiaryImgPage.getContent();
        }

        for(UserGrowthDiaryImg item : userGrowthDiaryImgList){
            UserGrowthDiary userGrowthDiary = userGrowthDiaryDao.findOne(item.getDiaryId());
            if(userGrowthDiary != null){
                item.setContent(userGrowthDiary.getContent());
            }
        }
        GetUserGrowthImgPageByWeekNumResponse response = new GetUserGrowthImgPageByWeekNumResponse();
        response.setUserGrowthDiaryImgList(userGrowthDiaryImgList);
        response.setJpaPage(userGrowthDiaryImgPage);
        response.setWeekNum(weekNum);
        return response;
    }

    @Override
    public List<GrowthDiaryWeek> getGrowthDiaryWeekList(Integer userId) {
        List<GrowthDiaryWeek> growthDiaryWeekList = new ArrayList<>();
        List<Integer> weekNumList = userGrowthDiaryImgDao.getAllWeekNumByUserId(userId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String nowYear = simpleDateFormat.format(new Date());
        for(Integer item : weekNumList){
            String itemYear = item.toString().substring(0,4);
            Integer crossYear = 0;
            if(!itemYear.equals(nowYear)){
                crossYear = 1;
            }
            Date beginTime = this.getGrowthDiaryDate(userId, item, Sort.Direction.ASC).getRecordDate();
            Date endTime = this.getGrowthDiaryDate(userId, item, Sort.Direction.DESC).getRecordDate();
            GrowthDiaryWeek growthDiaryWeek = new GrowthDiaryWeek();
            growthDiaryWeek.setBeginTime(beginTime);
            growthDiaryWeek.setEndTime(endTime);
            growthDiaryWeek.setWeekNum(item);
            growthDiaryWeek.setCrossYear(crossYear);
            GetUserGrowthImgPageByWeekNumResponse response = this.getUserGrowthImgPageByWeekNum(userId,item,0,8, 0);
            growthDiaryWeek.setUserGrowthDiaryImgList(response.getUserGrowthDiaryImgList());
            growthDiaryWeek.setPage(response.getPage());
            growthDiaryWeekList.add(growthDiaryWeek);
            nowYear = itemYear;
        }

        return growthDiaryWeekList;
    }

    @Override
    public UserGrowthDiaryImg getGrowthDiaryDate(Integer userId, Integer weekNum, Sort.Direction orderString){
        UserGrowthDiaryImg userGrowthDiaryImg = new UserGrowthDiaryImg();
        userGrowthDiaryImg.setUserId(userId);
        userGrowthDiaryImg.setWeekNum(weekNum);
        userGrowthDiaryImg.setStatus(1);
        Pageable pageable = new PageRequest(0, 1, new Sort(orderString,"recordDate"));
        Page<UserGrowthDiaryImg> userGrowthDiaryImgPage = userGrowthDiaryImgDao.findAll(Example.of(userGrowthDiaryImg),pageable);
        List<UserGrowthDiaryImg> userGrowthDiaryImgList = userGrowthDiaryImgPage.getContent();
        if(userGrowthDiaryImgList.size() > 0 ){
            userGrowthDiaryImg = userGrowthDiaryImgList.get(0);
        }
        return userGrowthDiaryImg;
    }

    @Override
    public List<GrowthDiaryDay> getGrowthDiaryDayList(Integer userId, Integer weekNum) throws ParseException {

        List<GrowthDiaryDay> growthDiaryDayList = new ArrayList<>();

        UserGrowthDiaryImg userGrowthDiaryImg = new UserGrowthDiaryImg();
        userGrowthDiaryImg.setUserId(userId);
        userGrowthDiaryImg.setWeekNum(weekNum);
        userGrowthDiaryImg.setStatus(1);
        Sort sort = new Sort(Sort.Direction.DESC,"recordDate","id");
        List<UserGrowthDiaryImg> userGrowthDiaryImgList = userGrowthDiaryImgDao.findAll(Example.of(userGrowthDiaryImg),sort);

        Map<Integer, List<UserGrowthDiaryImg>> userGrowthDiaryImgMap = new HashMap<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        for(UserGrowthDiaryImg item : userGrowthDiaryImgList){
            String recordDateString = simpleDateFormat.format(item.getRecordDate());
            Integer recordDateInteger = Integer.parseInt(recordDateString);
            UserGrowthDiary userGrowthDiary = userGrowthDiaryDao.findOne(item.getDiaryId());
            if(userGrowthDiary != null){
                item.setContent(userGrowthDiary.getContent());
            }
            if(userGrowthDiaryImgMap.get(recordDateInteger) != null){
                List<UserGrowthDiaryImg> userGrowthDiaryImgs = userGrowthDiaryImgMap.get(recordDateInteger);
                userGrowthDiaryImgs.add(item);
            } else {
                List<UserGrowthDiaryImg> userGrowthDiaryImgs = new ArrayList<>();
                userGrowthDiaryImgs.add(item);
                userGrowthDiaryImgMap.put(recordDateInteger,userGrowthDiaryImgs);
            }
        }

        for (Integer key : userGrowthDiaryImgMap.keySet()) {
            GrowthDiaryDay growthDiaryDay = new GrowthDiaryDay();
            Date recordDate = simpleDateFormat.parse(key.toString());
            growthDiaryDay.setRecordKey(key);
            growthDiaryDay.setRecordDate(recordDate);
            growthDiaryDay.setUserGrowthDiaryImgList(userGrowthDiaryImgMap.get(key));
            growthDiaryDayList.add(growthDiaryDay);
        }

        return growthDiaryDayList;
    }

    @Override
    public Integer getRsCountByUserIdAndWeekNum(Integer userId, Integer weekNum) {
        Integer rsCount = userGrowthDiaryImgDao.getRsCountByUserIdAndWeekNum(userId,weekNum,1);
        return rsCount;
    }
    @Override
    public UserGrowthDiaryImg getUserGrowthDiaryImgById(Integer id) {
        return userGrowthDiaryImgDao.findOne(id);
    }
}
