package com.ifenghui.storybookapi.app.transaction.service;

import com.ifenghui.storybookapi.app.presale.entity.PreSaleCode;
import com.ifenghui.storybookapi.app.presale.entity.SaleGoodsStyle;
import com.ifenghui.storybookapi.app.transaction.entity.vip.PayVipOrder;
import com.ifenghui.storybookapi.app.transaction.response.GetSvipPrivilegeResponse;
import com.ifenghui.storybookapi.app.transaction.response.GetSvipResponse;
import com.ifenghui.storybookapi.app.transaction.response.ReturnSvip;
import com.ifenghui.storybookapi.app.transaction.service.order.OrderPayProcess;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.wallet.response.SubscribeByCodeResponse;
import com.ifenghui.storybookapi.style.*;

import java.util.List;

public interface PayVipOrderService extends OrderPayProcess {

    /**
     * 获取用户vip页面数据
     * @param userId
     * @return
     */
    public GetSvipPrivilegeResponse getSvipPrivilege(Long userId);

    public PayVipOrder addPayVipOrder(User user, Integer orignalPrice, Integer couponAmount, Integer amount, Integer priceId, Integer userDiscount, String channel, String code, OrderPayStyle orderPayStyle);

    /**
     * 余额购买vip
     * @param userId
     * @param orderId
     * @param payStyle
     * @param walletStyle
     * @return
     */
    public PayVipOrder buyVipByBalance(Long userId, Integer orderId, OrderPayStyle payStyle, WalletStyle walletStyle);

    /**
     * 兑换码购买（执行函数）
     * @param userId
     * @param vipGoodsStyle
     * @param preSaleCode
     * @return
     */
    public PayVipOrder buyVipByCodeMethod(Long userId, VipGoodsStyle vipGoodsStyle, PreSaleCode preSaleCode);

    PayVipOrder createPayVipOrder(VipGoodsStyle vipGoodsStyle, Long userId, List<Integer> couponIds);

    PayVipOrder getPayVipOrderById(Integer id);

    /**
     * 兑换码购买vip
     * @param userId
     * @param vipGoodsStyle
     * @param preSaleCode
     * @param receiver
     * @param phone
     * @param address
     * @param area
     * @param codeType
     * @return
     */
    SubscribeByCodeResponse buyVipByCode(Long userId, VipGoodsStyle vipGoodsStyle, PreSaleCode preSaleCode, String receiver, String phone, String address, String area, Integer codeType);
}
