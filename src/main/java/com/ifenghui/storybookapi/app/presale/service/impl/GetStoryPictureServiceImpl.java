package com.ifenghui.storybookapi.app.presale.service.impl;


import com.ifenghui.storybookapi.app.presale.entity.StoryPicture;
import com.ifenghui.storybookapi.app.presale.service.GetStoryPictureService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@Component
public class GetStoryPictureServiceImpl implements GetStoryPictureService {

    @Override
    public StoryPicture findOne() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        StoryPicture storyPicture = new StoryPicture();

        //正式
        if(today.equals("2018-08-01")){

            storyPicture.setId(1);
            storyPicture.setGameStoryId(252);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/01xiaoongwang.jpg");
            storyPicture.setGameTitle("益智游戏-小龙王");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(221);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/01xiaoongwanglittle.jpg");
            storyPicture.setTitle("互动故事：小龙王");
        }
        if(today.equals("2018-08-02")){

            storyPicture.setId(2);
            storyPicture.setGameStoryId(279);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/02wulalatansuonanfei.jpg");
            storyPicture.setGameTitle("益智游戏-草坪");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(285);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/02wulalatansuonanfeilittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—探索南非草原");
        }
        if(today.equals("2018-08-03")){

            storyPicture.setId(3);
            storyPicture.setGameStoryId(256);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/03xiaoguowangshangxue.jpg");
            storyPicture.setGameTitle("益智游戏-小国王上学去");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(230);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/03xiaoguowangshangxuelittle.jpg");
            storyPicture.setTitle("互动故事：小国王上学去");
        }
        if(today.equals("2018-08-04")){

            storyPicture.setId(4);
            storyPicture.setGameStoryId(257);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/04haidaodefangjain.jpg");
            storyPicture.setGameTitle("益智游戏-海盗的房间");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(152);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/04haidaodefangjainlittle.jpg");
            storyPicture.setTitle("互动故事：海盗的房间");
        }
        if(today.equals("2018-08-05")){

            storyPicture.setId(5);
            storyPicture.setGameStoryId(280);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/05wulalakafei.jpg");
            storyPicture.setGameTitle("益智游戏-喷泉广场");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(249);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/05wulalakafeilittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—罗马咖啡事件");
        }
        if(today.equals("2018-08-06")){

            storyPicture.setId(6);
            storyPicture.setGameStoryId(258);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/06wulalahaidijiuyuan.jpg");
            storyPicture.setGameTitle("益智游戏-海底大救援");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(125);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/06wulalahaidijiuyuanlittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—海底大救援");
        }
        if(today.equals("2018-08-07")){

            storyPicture.setId(7);
            storyPicture.setGameStoryId(273);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/07dankegongzhu.jpg");
            storyPicture.setGameTitle("益智游戏-蛋壳公主");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(137);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/07dankegongzhulittle.jpg");
            storyPicture.setTitle("互动故事：长着鸡蛋脑壳的公主");
        }
        if(today.equals("2018-08-08")){

            storyPicture.setId(8);
            storyPicture.setGameStoryId(283);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/08wulalariben.jpg");
            storyPicture.setGameTitle("益智游戏-游乐场");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(76);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/08wulalaribenlittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—日本奇遇记");
        }
        if(today.equals("2018-08-09")){

            storyPicture.setId(9);
            storyPicture.setGameStoryId(274);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/09xiaohaidaodamaoxian.jpg");
            storyPicture.setGameTitle("益智游戏-小海盗");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(34);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/09xiaohaidaodamaoxianlittle.jpg");
            storyPicture.setTitle("互动故事：小海盗大冒险");
        }
        if(today.equals("2018-08-10")){

            storyPicture.setId(10);
            storyPicture.setGameStoryId(253);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/10aijiqimiao.jpg");
            storyPicture.setGameTitle("益智游戏-卫生间");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(141);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/10aijiqimiaolittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—埃及奇妙夜");
        }
        if(today.equals("2018-08-11")){

            storyPicture.setId(11);
            storyPicture.setGameStoryId(275);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/11xiaoxiaogaoluren.jpg");
            storyPicture.setGameTitle("益智游戏-小小高卢人");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(132);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/11xiaoxiaogaolurenlittle.jpg");
            storyPicture.setTitle("互动故事：小小高卢人的胡子");
        }
        if(today.equals("2018-08-12")){

            storyPicture.setId(12);
            storyPicture.setGameStoryId(260);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/12wulalafeichuan.jpg");
            storyPicture.setGameTitle("益智游戏-厨房");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(31);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/12wulalafeichuanlittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—飞船迫降");
        }


        if(today.equals("2018-08-13")){

            storyPicture.setId(13);
            storyPicture.setGameStoryId(272);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/haigelisi.jpg");
            storyPicture.setGameTitle("益智游戏-超级大英雄");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(45);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/haigelisilittle.jpg");
            storyPicture.setTitle("互动故事：超级大英雄海格力斯");
        }


        if(today.equals("2018-08-14")){

            storyPicture.setId(14);
            storyPicture.setGameStoryId(252);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/01xiaoongwang.jpg");
            storyPicture.setGameTitle("益智游戏-小龙王");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(221);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/01xiaoongwanglittle.jpg");
            storyPicture.setTitle("互动故事：小龙王");
        }
        if(today.equals("2018-08-15")){

            storyPicture.setId(15);
            storyPicture.setGameStoryId(279);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/02wulalatansuonanfei.jpg");
            storyPicture.setGameTitle("益智游戏-草坪");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(285);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/02wulalatansuonanfeilittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—探索南非草原");
        }
        if(today.equals("2018-08-16")){

            storyPicture.setId(16);
            storyPicture.setGameStoryId(256);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/03xiaoguowangshangxue.jpg");
            storyPicture.setGameTitle("益智游戏-小国王上学去");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(230);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/03xiaoguowangshangxuelittle.jpg");
            storyPicture.setTitle("互动故事：小国王上学去");
        }
        if(today.equals("2018-08-17")){

            storyPicture.setId(17);
            storyPicture.setGameStoryId(257);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/04haidaodefangjain.jpg");
            storyPicture.setGameTitle("益智游戏-海盗的房间");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(152);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/04haidaodefangjainlittle.jpg");
            storyPicture.setTitle("互动故事：海盗的房间");
        }
        if(today.equals("2018-08-18")){

            storyPicture.setId(18);
            storyPicture.setGameStoryId(280);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/05wulalakafei.jpg");
            storyPicture.setGameTitle("益智游戏-喷泉广场");
            storyPicture.setLittleTitle("(提升认知)");

            storyPicture.setStoryId(249);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/05wulalakafeilittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—罗马咖啡事件");
        }
        if(today.equals("2018-08-19")){

            storyPicture.setId(19);
            storyPicture.setGameStoryId(258);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/06wulalahaidijiuyuan.jpg");
            storyPicture.setGameTitle("益智游戏-海底大救援");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(125);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/06wulalahaidijiuyuanlittle.jpg");
            storyPicture.setTitle("互动故事：小怪兽乌拉拉—海底大救援");
        }
        if(today.equals("2018-08-20")){

            storyPicture.setId(20);
            storyPicture.setGameStoryId(273);
            storyPicture.setGameImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/07dankegongzhu.jpg");
            storyPicture.setGameTitle("益智游戏-蛋壳公主");
            storyPicture.setLittleTitle("(锻炼观察力)");

            storyPicture.setStoryId(137);
            storyPicture.setImgUrl("//storybook.oss-cn-hangzhou.aliyuncs.com/presale/storypicture/07dankegongzhulittle.jpg");
            storyPicture.setTitle("互动故事：长着鸡蛋脑壳的公主");
        }


        return storyPicture;
    }
}
