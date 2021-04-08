package com.ifenghui.storybookapi.app.wallet.service;

import com.ifenghui.storybookapi.app.wallet.entity.CashAccountCashApply;
import com.ifenghui.storybookapi.style.CashAccountApplyStatusStyle;
import com.ifenghui.storybookapi.style.CashAccountApplyStyle;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public interface CashAccountCashApplyService {

    /**
     * 添加现金账户提现申请
     * @param amount
     * @param cashAccountApplyStyle
     * @param userId
     * @param account
     * @param userInfo
     * @return
     */
    public CashAccountCashApply addCashAccountCashApply(Integer amount, CashAccountApplyStyle cashAccountApplyStyle, Integer userId, String account, String userInfo);

    /**
     * 创建现金账户提现申请
     * @param amount
     * @param type
     * @param userId
     * @param account
     * @param userInfo
     * @return
     * @throws ParseException
     */
    CashAccountCashApply createCashAccountCashApply(Integer amount, CashAccountApplyStyle type, Integer userId, String account, String userInfo) throws ParseException;

    /**
     * 转账/退款驳回
     * @param id
     */
    public void turnCashAccountCashApply( Integer id );

    /**
     * 查看是否超过了一个月的申请限额
     * @param userId
     * @throws ParseException
     */
    public void isExceedOneMonthMaxApplyAmount(Integer userId) throws ParseException;

    /**
     * 处理现金提现申请
     * @throws Exception
     */
    public void dealCashAccountApply() throws Exception;

    /**
     * 处理阿里的提现申请
     * @param cashAccountCashApply
     */
    public void dealAliPayRefund(CashAccountCashApply cashAccountCashApply);

    /**
     * 处理微信提现申请
     * @param cashAccountCashApply
     * @throws Exception
     */
    public void dealWechatPayRefund(CashAccountCashApply cashAccountCashApply) throws Exception;

    /**
     * 修改现金提现记录状态
     * @param cashAccountCashApply
     * @param callbackMsg
     * @param cashAccountApplyStatusStyle
     * @param callbackStatus
     * @param resultStatus
     */
    public void changeCashAccountApplyStatus(
            CashAccountCashApply cashAccountCashApply,
            String callbackMsg,
            CashAccountApplyStatusStyle cashAccountApplyStatusStyle,
            Integer callbackStatus,
            Integer resultStatus
    );

}
