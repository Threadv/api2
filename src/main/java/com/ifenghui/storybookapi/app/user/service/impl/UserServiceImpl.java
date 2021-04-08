package com.ifenghui.storybookapi.app.user.service.impl;

import com.ifenghui.storybookapi.app.social.service.ContinueDay;
import com.ifenghui.storybookapi.app.transaction.dao.CouponGetRecordDao;
import com.ifenghui.storybookapi.app.transaction.dao.UserSvipDao;
import com.ifenghui.storybookapi.app.transaction.entity.vip.UserSvip;
import com.ifenghui.storybookapi.app.transaction.entity.coupon.CouponGetRecord;
import com.ifenghui.storybookapi.app.transaction.service.CouponService;
import com.ifenghui.storybookapi.app.transaction.service.PayVipOrderService;
import com.ifenghui.storybookapi.app.transaction.service.UserSvipService;
import com.ifenghui.storybookapi.app.user.dao.UserAccountDao;
import com.ifenghui.storybookapi.app.user.dao.UserDao;
import com.ifenghui.storybookapi.app.user.dao.UserTokenDao;
import com.ifenghui.storybookapi.app.user.entity.User;
import com.ifenghui.storybookapi.app.user.entity.UserAccount;
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import com.ifenghui.storybookapi.app.user.service.UserExtendService;
import com.ifenghui.storybookapi.app.user.service.UserService;
import com.ifenghui.storybookapi.app.wallet.entity.Wallet;
import com.ifenghui.storybookapi.app.wallet.service.WalletService;
import com.ifenghui.storybookapi.exception.ApiException;
import com.ifenghui.storybookapi.exception.ApiIsAddException;
import com.ifenghui.storybookapi.exception.ApiNotFoundException;
import com.ifenghui.storybookapi.exception.ApiNotTokenException;
import com.ifenghui.storybookapi.style.SvipStyle;
import com.ifenghui.storybookapi.style.UserAccountStyle;
import com.ifenghui.storybookapi.util.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * Created by wslhk on 2016/12/20.
 */
@Transactional(rollbackFor = Exception.class)
@Component
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Autowired
    UserTokenDao userTokenDao;
//    @Autowired
//    WalletDao walletDao;
    @Autowired
    UserAccountDao userAccountDao;

    @Autowired
    UserSvipDao userSvipDao;
//    @Transactional
    @Autowired
    WalletService walletService;

    @Autowired
    UserExtendService userExtendService;

    @Autowired
    CouponService couponService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    CouponGetRecordDao couponGetRecordDao;

    @Autowired
    PayVipOrderService payVipOrderService;

    @Autowired
    UserSvipService userSvipService;

    @Override
    public User getUser(long userId) {
        if(userId<=0){
            return null;
        }
        return userDao.findOne(userId);
    }
    @Override
    public Integer getAge(Date birthDay){
        Integer age = 0;
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
//            throw new IllegalArgumentException(
//                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

         age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth){
                    age--;
                }
            }else{
                age--;
            }
        }
        return age;
    }
    @Override
    public User addUser(User user) {
        User u=userDao.save(user);

        walletService.initReg(u.getId().intValue());

        return u;
    }

    @Override
    public User updateUser(User user) {
        User u=userDao.save(user);
        return u;
    }

    @Override
    public User bindPhone(long userId, String phone) {

        User user=userDao.findOne(userId);
        user.setPhone(phone);
        userDao.save(user);
        CouponGetRecord couponGetRecord= couponGetRecordDao.getByPhone(phone);
        if(couponGetRecord!=null){
            userExtendService.editParentId((int)userId,couponGetRecord.getUserId().intValue());
        }

        return null;
    }

    @Override
    public void deleteUserByPhone(String phone)throws ApiException {
        Integer pageNo = 0;
        Integer pageSize = 2;
        Page<User> puser = this.getUserByPhonePage(phone,pageNo,pageSize);
        if(puser.getContent().size() == 0){
            throw new ApiIsAddException("此手机号未注册");
        }
        for (User user : puser.getContent()){
            userDao.delete(user.getId());
        }
        return ;
    }

    @Override
    public User getUserByPhone(String phone) {

        if ("".equals(phone)){
            return null;
        }
        User user = userDao.getUserByPhoneAndUnsubscribe(phone,0);
        if(user == null){
            return null;
        }
        //获取用户星星值
        Wallet wallet = walletService.getWalletByUserId(user.getId());
        if(wallet != null ){
            user.setStarCount(wallet.getStarCount());
        }

        return user;
    }

    @Override
    public Page<User> getUserByPhonePage(String phone, Integer pageNo, Integer pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        //未注销的用户
        Integer unsubscribe = 0;
        Page<User> userPage =  userDao.getUserByPhoneAndUnsubscribe(phone,unsubscribe,pageable);
        return userPage;
    }
    @Override
    public long checkAndGetCurrentUserId(String token)throws ApiNotTokenException{
        UserToken userToken=this.getUserIdByToken(token);
        if(userToken==null){
            throw new ApiNotTokenException();
        }
        User user=this.getUser(userToken.getUserId());
        if(user==null){
            throw new ApiNotTokenException("token相关用户不存在");
        }

        if("".equals(user.getIpAddress())){
            String ipAddress = HttpRequest.getIpAddr(request);
            if(ipAddress != null){

                user.setIpAddress(ipAddress);
                userDao.save(user);
            }
        }


            return user.getId().intValue();


    }



    @Override
    public User editUserNick(long userId, String userNick)throws Exception {


            User user=userDao.getOne(userId);

            user.setNick(userNick);
             user=userDao.save(user);
        return user;

    }



    @Override
    public List<User> getUsersByNick(String userNick, int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));

        //未注销的用户
        Integer unsubscribe = 0;
        Page<User> userPage =  userDao.getUserByNickAndUnsubscribe(userNick,unsubscribe,pageable);
        return userPage.getContent();
    }

    @Override
    public UserToken getUserToken(long id){
        UserToken userToken = userTokenDao.findOne(id);
        if(userToken != null) {
            userToken.setUser(this.getUser(userToken.getUserId()));
        }
        return  userToken;
    }

    @Override
    public UserToken addUserToken(UserToken userToken) {
        UserToken u=userTokenDao.save(userToken);
        return u;
    }

    @Override
    public UserToken updateUserToken(UserToken userToken) {
        UserToken u=userTokenDao.save(userToken);
        return u;
    }

    @Override
    public UserToken getUserIdByToken(String token){
        UserToken userToken = userTokenDao.findOneByToken(token);
        if(userToken != null) {
            userToken.setUser(this.getUser(userToken.getUserId()));
        }
        return userToken;
    }

    @Override
    public UserToken getTokenByUserId(long userId){
        UserToken userToken = userTokenDao.findOneByUserId(userId);
        if(userToken != null) {
            userToken.setUser(this.getUser(userId));
        }
        return userToken;
    }

    @Override
    public UserToken getUserTokenByUserIdAndDevice(long userId,String device){
        UserToken userToken = userTokenDao.findOneByUserIdAndDevice(userId,device);
        if(userToken != null) {
            userToken.setUser(this.getUser(userToken.getUserId()));
        }
        return userToken;
    }
    @Override
    public List<UserToken> getUserTokensByUserIdAndDevice(long userId,String device){
        List<UserToken> userTokenList = userTokenDao.findUserTokensByUserIdAndDevice(userId,device);
        for(UserToken item : userTokenList) {
            item.setUser(this.getUser(item.getUserId()));
        }
        return userTokenList;
    }

    @Override
    public UserAccount getUserAccount(long id) {
        return userAccountDao.findOne(id);
    }

    @Override
    public UserAccount getUserAccountBySrcId(String srcId) {
        List<UserAccount> userAccounts=userAccountDao.findUserAccountsBySrcId(srcId);
        if(userAccounts.size()!=1){
            return null;
        }
        //获取用户星星值
        Wallet wallet = walletService.getWalletByUserIdInCache(userAccounts.get(0).getUserId().intValue());
        if(wallet != null ){
            userAccounts.get(0).getUser().setStarCount(wallet.getStarCount());
        }
        return userAccounts.get(0);
    }

    @Override
    public UserAccount getUserAccountByUserId(Long userId) {
        return userAccountDao.getOneByUserId(userId);
    }

    @Override
    public UserAccount getUserAccountByUserIdAndSrcType(Long userId,Integer srcType) {
        return userAccountDao.getOneByUserIdAndSrcType(userId,srcType);
    }

    /**
     * 获得用户账号列表
     * @param userId
     * @return
     */
    public List<UserAccount> getUserAccountsByUserId(Long userId) {

        return userAccountDao.getUserAccountListByUserId(userId);
    }
    @Override
    public UserAccount addUserAccount(UserAccount userAccount) {
        UserAccount u=userAccountDao.save(userAccount);
        return u;
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        UserAccount u=userAccountDao.save(userAccount);
        return u;
    }

    @Override
    public void delUserAccountByUserIdAndSrcType(Long userId,Integer srcType) {
        //查询数据
        UserAccount userAccount = userAccountDao.getOneByUserIdAndSrcType(userId,srcType);
        userAccountDao.delete(userAccount.getId());
    }

    @Override
    public UserSvip getUserSvip(Long userId) {
        //查询数据
        UserSvip userSvip = userSvipService.getLastestUserSvipRecord(userId);
        if(userSvip==null){
            return null;
        }

        //判断是否在svip内
        Date nowTime = new Date();
        long timeStr = userSvip.getEndTime().getTime() - nowTime.getTime();
        if(timeStr<0){
            //已过期
            return null;
        }
        int days = (int) ((timeStr / (1000*3600*24)));
//        if(days==0 && timeStr>0){
//            days = 1;
//        }
        userSvip.setDays(days+1);

        return userSvip;
    }

    @Override
    public void setContinueDay(Long userId, Integer clockSeveralCount, Integer clockMaxSeveralCount) {
        User user = userDao.findOne(userId);
        if(user == null){
            throw new ApiNotFoundException("没有该用户！");
        }
        user.setClockSeveralCount(clockSeveralCount);
        user.setClockMaxSeveralCount(clockMaxSeveralCount);
        userDao.save(user);
    }

    @Override
    public ContinueDay getContinueDay(Long userId) {
        User user = userDao.findOne(userId);
        if(user == null){
            throw new ApiNotFoundException("没有该用户！");
        }
        ContinueDay continueDay = new ContinueDay();
        continueDay.setMaxContinueDay(user.getClockMaxSeveralCount());
        continueDay.setCurrentContinueDay(user.getClockSeveralCount());
        return continueDay;
    }


    @Override
    public void checkUserPhoneCondition(UserAccountStyle userAccountStyle, String phone, String srcId) {

//        if(type != 4){
//            //非游客注册手机号不能为空
//            if(phone.equals("") || phone == null){
//                throw new ApiNotFoundException("手机号为空",3);
//            }
        /**
         * 屏蔽掉注册电话注册时 电话号码为空限制
         */
            User user = this.getUserByPhone(phone);
            if(user != null){
                throw new ApiNotFoundException("已注册",2);
            }
//        }

        UserAccount userAccount = this.getUserAccountBySrcId(srcId);
        if(userAccount != null){
            throw new ApiNotFoundException("已注册",2);
        }
    }

    @Override
    public User otherRegisterUser(String ver, String channel, UserAccountStyle userAccountStyle, String phone, String nick, Integer sex, String birthday, String avatar) {
        User user = new User();
        if(nick == null || nick == ""){
            nick = "宝宝";
        }
        if(sex == null){
            sex = -1;
        }
        Date birthdayDate = new Date();
        if(birthday != null && !"".equals(birthday)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birthdayDate = sdf.parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(avatar == null){
            avatar = "";
        }
        Integer isTourist = 0;

        user.setCreateTime(new Date());
        user.setPhone(phone);
        user.setPassword("");
        user.setVip(0);
        user.setIsAbilityPlan(0);
        user.setNick(nick);
        user.setSex(sex);
        user.setAddr("");
        user.setAvatar(avatar);
        user.setSlogen("");
        user.setIsAccount(userAccountStyle.getId());
        user.setBirthday(birthdayDate);
        user.setIsTourist(isTourist);
        user.setIsTest(0);
        user.setUnsubscribe(0);
        user.setCity("");
        user.setProvince("");
        String ipAddress = HttpRequest.getIpAddr(request);

        if(ipAddress != null){
            user.setIpAddress(ipAddress);
        } else {
            user.setIpAddress("");
        }

        Integer svip = 0 ;
        user.setSvip(svip);//1所有故事可看2只有订阅栏中故事可看
        user.setReadDays(0);
        user.setReadCount(0);
        user.setClockMaxSeveralCount(0);
        user.setClockSeveralCount(0);
        user.setChannel(channel);
        user = this.addUser(user);
        if(userAccountStyle == UserAccountStyle.GUEST){
            nick = "游客"+ user.getId();
            isTourist = 1;
            user.setNick(nick);
            user.setIsTourist(isTourist);

        }

        user = userDao.save(user);
        return user;
    }

    @Override
    public UserAccount addUserAccountByUser(User user, String srcId, UserAccountStyle accountStyle) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUser(user);
        userAccount.setCreateTime(new Date());
        userAccount.setSrcId(srcId);
        userAccount.setAccountStyle(accountStyle);
        userAccount = this.addUserAccount(userAccount);
        return userAccount;
    }

    @Override
    public User phoneRegisterUser(String phone, String password, String ver, String channel) {
        User user = this.getUserByPhone(phone);

        if(user!=null){
            throw new ApiNotFoundException("已注册",2);
        }



        user = new User.Builder().initAdd()//待添加宝宝小名
                .setPhone(phone)
                .setPassword(password)
                .setNick("宝宝")
                .setBirthday(new Date())
                .setSex(-1)
                .setIsAccount(0)
                .setIsTourist(0)
                .build();
        user.setIsTest(0);
        String ipAddress = HttpRequest.getIpAddr(request);
        if(ipAddress != null){
            user.setIpAddress(ipAddress);
        } else {
            user.setIpAddress("");
        }

        user.setUnsubscribe(0);
        Integer svip = 0 ;
        user.setSvip(svip);
        user.setReadDays(0);
        user.setReadCount(0);
        user.setIsAbilityPlan(0);
        user.setClockMaxSeveralCount(0);
        user.setClockSeveralCount(0);
        user.setChannel(channel);
        user.setCity("");
        user.setProvince("");
        User newUser=this.addUser(user);
        newUser.setAge(0);
        newUser.setBalance(0);

        CouponGetRecord couponGetRecord = couponService.getCouponGetRecord(phone);
        if(couponGetRecord != null){
            userExtendService.editParentId(newUser.getId().intValue(), couponGetRecord.getUserId().intValue());
        } else {
            userExtendService.createUserExtend(newUser.getId().intValue(), 0);
        }
        return newUser;
    }

    @Override
    public void removeUser(int userId) {
        User user =userDao.findOne((long)userId);
        user.setPhone(user.getPhone()+"-"+userId);
        user.setUnsubscribe(1);
        userDao.save(user);



        List<UserAccount> userAccounts= this.getUserAccountsByUserId((long)userId);
        for(UserAccount userAccount :userAccounts){
            userAccount.setSrcId(userAccount.getSrcId()+"-"+userId);
            userAccountDao.save(userAccount);
        }
        UserToken findUser=new UserToken();
        findUser.setUserId((long)userId);

        List<UserToken> userTokens= userTokenDao.findAll(Example.of(findUser));
        for(UserToken userToken:userTokens){
            userToken.setToken(userToken.getToken()+"-r");
            userTokenDao.save(userToken);
        }

//        if(user.getPhone()+"-")
    }

    @Override
    public void changeUserSvipStyle(Long userId, SvipStyle svipStyle) {
        User user = this.getUser(userId);
        user.setSvip(svipStyle.getId());
        userDao.save(user);
    }

    @Override
    public void changeUserIsAbilityPlanStyle(Long userId, Integer isAbilityPlan) {
        User user = this.getUser(userId);
        user.setIsAbilityPlan(isAbilityPlan);
        userDao.save(user);
    }

    @Override
    public Page<User> findAllBySvipLevelThreeAndFour(int pageNo, int pageSize) {
        Pageable pageable = new PageRequest(pageNo, pageSize, new Sort(Sort.Direction.DESC,"id"));
        return userDao.findAllBySvipLevelThreeAndFour(pageable);
    }

    @Override
    public Page<User> findAll(User user, PageRequest pageRequest) {
        return userDao.findAll(Example.of(user),pageRequest);
    }

    @Override
    public User findUserByToken(String token){

        UserToken userToken = userTokenDao.findOneByToken(token);
        if(userToken == null){
            throw new ApiNotFoundException("用户身份信息异常");
        }
        User user = userDao.findOne(userToken.getUserId());
        return user;
    }
}
