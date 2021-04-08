package com.ifenghui.storybookapi.app.presale.service.impl;


import com.ifenghui.storybookapi.app.presale.dao.ActivityDao;
import com.ifenghui.storybookapi.app.presale.dao.VideoReadStudyRecordDao;
import com.ifenghui.storybookapi.app.presale.entity.Activity;
import com.ifenghui.storybookapi.app.presale.service.YiZhiActivityUserReadRecordService;
import com.ifenghui.storybookapi.app.social.dao.UserReadRecordDao;
import com.ifenghui.storybookapi.app.social.entity.UserReadRecord;


import com.ifenghui.storybookapi.util.DateCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Component
public class YiZhiActivityUserReadRecordServiceImpl implements YiZhiActivityUserReadRecordService {

    @Autowired
    ActivityDao activityDao;

    @Autowired
    UserReadRecordDao userReadRecordDao;

    @Autowired
    VideoReadStudyRecordDao videoReadStudyRecordDao;

    /**
     * 根据类型查询故事记录 活动 3
     *
     * @param userId
     * @param typeList
     * @return
     */
    @Override
    public Map<Integer, UserReadRecord> getReadRecordListByTypes(Integer userId, List<Integer> typeList, Integer activityId) {

        Activity activity = activityDao.findOne(activityId);
        UserReadRecord userReadRecord = new UserReadRecord();
        Map<Integer, UserReadRecord> map = new HashMap();
        List<UserReadRecord> userReadRecordList;
        if (typeList != null && typeList.size() > 0) {
            userReadRecord.setUserId(userId.longValue());
            userReadRecordList = userReadRecordDao.findAll(Example.of(userReadRecord));
            for (UserReadRecord p : userReadRecordList) {
                //判断类型
                for (int i = 0; i < typeList.size(); i++) {
                    if (p.getType() == typeList.get(i) && DateCheckUtil.isEffectiveDate(p.getCreateTime(), activity.getStartTime(), activity.getEndTime())) { //判断类型和时间
                        map.put(p.getStoryId().intValue(), p);
                    }
                }
            }
            return map;
        }
        return map;
    }

    /**
     * 查询阅读记录  活动2 益智活动
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserReadRecord> getUserReadRecordList(Integer userId, Integer activityId) {

        Activity activity = activityDao.findOne(activityId);
        List<UserReadRecord> list = new ArrayList();
        //益智游戏类型 7、8
        List<UserReadRecord> userReadRecordList = userReadRecordDao.findYiZhiYouXiByUserId(userId);
        for (UserReadRecord p : userReadRecordList) {
            //判断时间
            if (DateCheckUtil.isEffectiveDate(p.getCreateTime(), activity.getStartTime(), activity.getEndTime())) {
                list.add(p);
            }
        }
        return list;
    }


    /**
     * 查看益智阅读记录 type 7/8  活动 陪娃成长
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserReadRecord> getUserReadRecordListByUserId(Integer userId, Integer activityId) {
        List<UserReadRecord> list = new ArrayList();
        HashSet<UserReadRecord> set = new HashSet<UserReadRecord>();
        Map<String, UserReadRecord> map = new HashMap<>();
        //益智游戏类型 7、8
        List<UserReadRecord> userReadRecordList = userReadRecordDao.findYiZhiYouXiByUserId(userId);
        for (UserReadRecord p : userReadRecordList) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //正式版
            //判断 第一天 小龙王
            if (p.getStoryId() == 252 && sdf.format(p.getCreateTime()).equals("2018-08-01")) {
                map.put("252", p);
//                set.add(p);
//                list.add(p);
            }
            //判断 第二天 草坪
            if (p.getStoryId() == 279 && sdf.format(p.getCreateTime()).equals("2018-08-02")) {
                map.put("279", p);
//                set.add(p);
            }
            //判断 第三天 小国王上学去
            if (p.getStoryId() == 256 && sdf.format(p.getCreateTime()).equals("2018-08-03")) {
                map.put("256", p);
//                set.add(p);
            }
            //判断 第四天 海盗的房间
            if (p.getStoryId() == 257 && sdf.format(p.getCreateTime()).equals("2018-08-04")) {
                map.put("257", p);
//                set.add(p);
            }
            //判断 第五天 喷泉广场
            if (p.getStoryId() == 280 && sdf.format(p.getCreateTime()).equals("2018-08-05")) {
                map.put("280", p);
//                set.add(p);
            }
            //判断 第六天 海底大救援
            if (p.getStoryId() == 258 && sdf.format(p.getCreateTime()).equals("2018-08-06")) {
                map.put("258", p);
//                set.add(p);
            }
            //判断 第七天 蛋壳公主
            if (p.getStoryId() == 273 && sdf.format(p.getCreateTime()).equals("2018-08-07")) {
                map.put("273", p);
//                set.add(p);
            }
            //判断 第八天 游乐场
            if (p.getStoryId() == 283 && sdf.format(p.getCreateTime()).equals("2018-08-08")) {
                map.put("283", p);
//                set.add(p);
            }
            //判断 第九天 小海盗
            if (p.getStoryId() == 274 && sdf.format(p.getCreateTime()).equals("2018-08-09")) {
                map.put("274", p);
//                set.add(p);
            }
            //判断 第十天 卫生间
            if (p.getStoryId() == 253 && sdf.format(p.getCreateTime()).equals("2018-08-10")) {
                map.put("253", p);
//                set.add(p);
            }
            //判断 第十一天 小小高卢人
            if (p.getStoryId() == 275 && sdf.format(p.getCreateTime()).equals("2018-08-11")) {
                map.put("275", p);
//                set.add(p);
            }
            //判断 第十二天 厨房
            if (p.getStoryId() == 260 && sdf.format(p.getCreateTime()).equals("2018-08-12")) {
                map.put("260", p);
//                set.add(p);
            }
            //判断 第13天 大英雄
            if (p.getStoryId() == 272 && sdf.format(p.getCreateTime()).equals("2018-08-13")) {
                map.put("272", p);
//                set.add(p);
            }
            //判断 第14天 小龙王
            if (p.getStoryId() == 252 && sdf.format(p.getCreateTime()).equals("2018-08-14")) {
                map.put("2522", p);
//                set.add(p);
            }
            //判断 第15天 草坪
            if (p.getStoryId() == 279 && sdf.format(p.getCreateTime()).equals("2018-08-15")) {
                map.put("2792", p);
//                set.add(p);
            }
            //判断 第16天 小国王上学去
            if (p.getStoryId() == 256 && sdf.format(p.getCreateTime()).equals("2018-08-16")) {
                map.put("2562", p);
//                set.add(p);
            }
            //判断 第17天 海盗的房间
            if (p.getStoryId() == 257 && sdf.format(p.getCreateTime()).equals("2018-08-17")) {
                map.put("2572", p);
//                set.add(p);
            }
            //判断 第18天 喷泉广场
            if (p.getStoryId() == 280 && sdf.format(p.getCreateTime()).equals("2018-08-18")) {
                map.put("2802", p);
//                set.add(p);
            }
            //判断 第19天 海底大救援
            if (p.getStoryId() == 258 && sdf.format(p.getCreateTime()).equals("2018-08-19")) {
                map.put("2582", p);
//                set.add(p);
            }
            //判断 第20天 蛋壳公主
            if (p.getStoryId() == 273 && sdf.format(p.getCreateTime()).equals("2018-08-20")) {
                map.put("2732", p);
//                set.add(p);
            }
        }
        Iterator<Map.Entry<String, UserReadRecord>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, UserReadRecord> next = iterator.next();
            UserReadRecord record = next.getValue();
            list.add(record);
        }
        return list;
    }
}
