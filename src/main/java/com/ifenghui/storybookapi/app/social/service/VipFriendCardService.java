package com.ifenghui.storybookapi.app.social.service;

import com.ifenghui.storybookapi.app.social.entity.VipFriendCard;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import com.ifenghui.storybookapi.style.VipFriendCardStatusStyle;
import com.ifenghui.storybookapi.style.VipFriendCardTypeStyle;
import org.springframework.data.domain.Page;

public interface VipFriendCardService {


    VipFriendCard addVipFriendCard(
            Integer userId,
            UserAccountStyle srcType,
            VipFriendCardTypeStyle cardType,
            String nick,
            String srcInfo
    );

    void createVipFriendCard(
            Integer userId,
            VipFriendCardTypeStyle cardType
    );

    Page<VipFriendCard> getVipFriendCardPageByUserId(Integer userId, Integer pageNo, Integer pageSize);

    VipFriendCard changeVipFriendCardStatus(Integer id, VipFriendCardStatusStyle status);

    VipFriendCard gainVipFriendCard(Integer id, UserAccountStyle srcType, String srcInfo, String nick);

    Integer createUserGiveStoryFromVipFriendCard(String srcInfo, Long userId);

    VipFriendCard findOne(Integer id);
}
