package com.ifenghui.storybookapi.app.user.service;

import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.app.social.service.ContinueDay;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by wslhk on 2016/12/20.
 */
public interface UserService {
    User getUser(long userId);
    Integer getAge(Date birthDay);
    User addUser(User user);
    User updateUser(User user);

    /**
     * 绑定手机号
     * @param userId
     * @param phone
     * @return
     */
    User bindPhone(long userId,String phone);


    void deleteUserByPhone(String phone)throws ApiException;
    User getUserByPhone(String phone);
    Page<User> getUserByPhonePage(String phone, Integer pageNo, Integer pageSize);
    //通过token获得当前用户id，自动抛出异常
    long checkAndGetCurrentUserId(String token)throws ApiNotTokenException;


    UserToken getUserToken(long id);

    UserToken addUserToken(UserToken userToken);
    UserToken updateUserToken(UserToken userToken);

    UserToken getUserIdByToken(String token);
    UserToken getTokenByUserId(long userId);
    /**
     * 通过 userId 和 设备信息 获取token
     */
    UserToken getUserTokenByUserIdAndDevice(long userId,String device);
    List<UserToken> getUserTokensByUserIdAndDevice(long userId,String device);


    UserAccount getUserAccount(long id);
    /**
     *  通过 srcId (第三方账号) 获取用户账号信息
     */
    UserAccount getUserAccountBySrcId(String srcId);
    UserAccount getUserAccountByUserId(Long userId);
    UserAccount getUserAccountByUserIdAndSrcType(Long userId,Integer srcType);
    UserAccount addUserAccount(UserAccount userAccount);
    UserAccount updateUserAccount(UserAccount userAccount);
    void delUserAccountByUserIdAndSrcType(Long userId,Integer srcType);

    User editUserNick(long userId,String userNick)throws Exception;

    List<User> getUsersByNick(String userNick, int pageNo,int pageSize);

    /**
     * 获取svip
     */
    public UserSvip getUserSvip(Long userId);

    /**
     * 设置打卡连续天数统计
     * @param userId
     * @param clockSeveralCount
     * @param clockMaxSeveralCount
     */
    void setContinueDay(Long userId, Integer clockSeveralCount, Integer clockMaxSeveralCount);

    /**
     * 获得连续打卡时间
     * @param userId
     * @return
     */
    ContinueDay getContinueDay(Long userId);


    /**
     * 通过类型判断用户注册情况
     * @param userAccountStyle 没啥用
     * @param phone
     */
    void checkUserPhoneCondition(UserAccountStyle userAccountStyle, String phone, String srcId);

    /**
     * 注册公共方法
     * @param ver
     * @param channel
     * @param userAccountStyle
     * @param phone
     * @param nick
     * @param sex
     * @param birthday
     * @param avatar
     * @return
     */
    User otherRegisterUser(String ver, String channel, UserAccountStyle userAccountStyle, String phone, String nick, Integer sex, String birthday, String avatar);

    /**
     * 添加第三方注册时userAccount
     * @param user
     * @param srcId
     * @param accountStyle
     * @return
     */
    UserAccount addUserAccountByUser(User user, String srcId, UserAccountStyle accountStyle);

    /**
     * 电话号码注册使用
     * @param phone
     * @param password
     * @param ver
     * @param channel
     * @return
     */
    User phoneRegisterUser(String phone, String password, String ver, String channel);

    /**
     * 注销用户，让用户无法登录，并没有删除数据
     * @param userId
     */
    public void removeUser(int userId);

    /**
     * 改变用户svip状态
     * @param userId
     * @param svipStyle
     */
    public void changeUserSvipStyle(Long userId, SvipStyle svipStyle);

    /**
     * 改变用户宝宝会读（优能计划）状态
     * @param userId
     * @param isAbilityPlan
     */
    void changeUserIsAbilityPlanStyle(Long userId,Integer isAbilityPlan);

    public Page<User> findAllBySvipLevelThreeAndFour(int pageNo, int pageSize);

    /**
     * 查找所有用户，用于后台
     * @param user
     * @param pageRequest
     * @return
     */
    Page<User> findAll(User user, PageRequest pageRequest);

    User findUserByToken(String token);
}
