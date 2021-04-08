package com.ifenghui.storybookapi.app.social.service.impl;

import com.ifenghui.storybookapi.app.social.dao.VipFriendCardDao;
import com.ifenghui.storybookapi.app.social.entity.VipFriendCard;
import com.ifenghui.storybookapi.app.social.service.VipFriendCardService;
import com.ifenghui.storybookapi.app.transaction.entity.BuyStoryRecord;
import com.ifenghui.storybookapi.app.transaction.service.BuyStoryRecordService;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import com.ifenghui.storybookapi.style.VipFriendCardStatusStyle;
import com.ifenghui.storybookapi.style.VipFriendCardTypeStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class VipFriendCardServiceImpl implements VipFriendCardService {

    @Autowired
    VipFriendCardDao vipFriendCardDao;

    @Autowired
    UserService userService;

    @Autowired
    BuyStoryRecordService buyStoryRecordService;

    @Override
    public VipFriendCard addVipFriendCard(Integer userId, UserAccountStyle srcType, VipFriendCardTypeStyle cardType, String nick, String srcInfo) {
        VipFriendCard vipFriendCard = new VipFriendCard();
        vipFriendCard.setUserId(userId);
        vipFriendCard.setCardType(cardType);
        vipFriendCard.setCreateTime(new Date());
        vipFriendCard.setSrcInfo(srcInfo);
        vipFriendCard.setNick(nick);
        vipFriendCard.setSrcType(srcType);
        vipFriendCard.setStatus(VipFriendCardStatusStyle.HAS_NOT_GET);
        return vipFriendCardDao.save(vipFriendCard);
    }

    @Override
    public void createVipFriendCard(Integer userId, VipFriendCardTypeStyle cardType) {
        User user = userService.getUser(userId.longValue());
        if(user.getSvip().equals(SvipStyle.LEVEL_THREE.getId()) || user.getSvip().equals(SvipStyle.LEVEL_FOUR.getId())){
            for(int i = 1; i<= cardType.getCardNum(); i++){
                this.addVipFriendCard(userId, UserAccountStyle.PHONE, cardType, "", "");
            }
        }
    }

    @Override
    public Page<VipFriendCard> getVipFriendCardPageByUserId(Integer userId, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        return vipFriendCardDao.getVipFriendCardsByUserId(userId, pageable);
    }

    @Override
    public VipFriendCard changeVipFriendCardStatus(Integer id, VipFriendCardStatusStyle status) {
        VipFriendCard vipFriendCard = vipFriendCardDao.findOne(id);
        vipFriendCard.setStatus(status);
        return vipFriendCardDao.save(vipFriendCard);
    }

    @Override
    public Integer createUserGiveStoryFromVipFriendCard(String srcInfo, Long userId) {
        VipFriendCard vipFriendCard = vipFriendCardDao.getVipFriendCardBySrcInfo(srcInfo);
        if(vipFriendCard == null){
            return 0;
        }
        if(vipFriendCard.getStatus().equals(VipFriendCardStatusStyle.HAS_GET.getId())){
            VipFriendCardTypeStyle vipFriendCardTypeStyle = VipFriendCardTypeStyle.getById(vipFriendCard.getCardType());
            if (vipFriendCardTypeStyle != null){
                int[] storyList = vipFriendCardTypeStyle.getStoryIdList();
                for(int item : storyList){
                    buyStoryRecordService.createBuyStoryRecord(userId, (long) item, 4);
                }
                this.changeVipFriendCardStatus(vipFriendCard.getId(), VipFriendCardStatusStyle.HAS_USE);
                return 1;
            }
        }
        return 0;
    }

    @Override
    public VipFriendCard gainVipFriendCard(Integer id, UserAccountStyle srcType, String srcInfo, String nick) {
        VipFriendCard vipFriendCard = vipFriendCardDao.findOne(id);

        userService.checkUserPhoneCondition(srcType, srcInfo, srcInfo);

        if(vipFriendCard == null){
            throw new ApiNotFoundException("没有这个亲友卡无法领取！");
        }
        if(vipFriendCard.getStatus().equals(VipFriendCardStatusStyle.HAS_GET.getId()) || vipFriendCard.getStatus().equals(VipFriendCardStatusStyle.HAS_USE.getId())){
            throw new ApiNotFoundException("这张亲友卡已被人领取！");
        }
        if(srcType.equals(UserAccountStyle.PHONE)){
            String str = "****";
            StringBuilder stringBuilder = new StringBuilder(srcInfo);
            stringBuilder.replace(3, 7, str);
            nick = stringBuilder.toString();
        }

        vipFriendCard.setStatus(VipFriendCardStatusStyle.HAS_GET);
        vipFriendCard.setSrcType(srcType);
        vipFriendCard.setSrcInfo(srcInfo);
        vipFriendCard.setNick(nick);
        return vipFriendCardDao.save(vipFriendCard);
    }

    @Override
    public VipFriendCard findOne(Integer id){
        return vipFriendCardDao.findOne(id);
    }
}
