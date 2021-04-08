package com.ifenghui.storybookapi.app.transaction.service;




import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.style.VipPriceStyle;

import java.util.Date;

public interface UserSvipService {

    /**
     * 增加vip
     * 购买过或增加vip的时候使用
     * @param userId
     * @return
     */
    public UserSvip addUserSvip(Integer userId, SvipStyle svipStyle, Date startTime, Date endTime);

    public UserSvip createUserSvip(Integer userId, VipPriceStyle vipPriceStyle);

    /**
     * 重置vip
     * 定时清理的时候使用
     */
    public void resetSvipUser();

    public UserSvip getLastestUserSvipRecord(Long userId);
}
