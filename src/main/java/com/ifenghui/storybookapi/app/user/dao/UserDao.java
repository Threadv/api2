package com.ifenghui.storybookapi.app.user.dao;


import com.ifenghui.storybookapi.app.user.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


/**
 * Created by wslhk on 2016/12/20.
 */
@Transactional(rollbackFor = Exception.class)
public interface  UserDao  extends JpaRepository<User, Long>  {


//    @Override
//    @Query("select :id")
//    public Object clearCache(@Param("id") Integer id);

    /**
     * 查一个
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "ship_user_v1",key = "'shipuser_v4'+#p0")
    @Override
    public User findOne(Long id);

    @CacheEvict(cacheNames = "ship_user_v1",key = "'shipuser_v4'+#p0.id")
    @Override
    public User save(User user);

    /**
     * 昵称查询User
     * @param userNick n昵称
     * @param unsubscribe 是否被注销，默认传0带表未注销
     * @param pageable 页号
     * @return
     */
    @Query("from User as u where u.nick =:userNick and u.unsubscribe =:unsubscribe")
    Page<User> getUserByNickAndUnsubscribe(@Param("userNick") String userNick,@Param("unsubscribe") Integer unsubscribe,Pageable pageable);
//    User findByName(String name);
//
//    User findByNameAndAge(String name, Integer age);
//
//    @Query("from User u where u.name=:name")
//    User findUser(@Param("name") String name);

    /**
     * 模糊查询用户
     * @param nick 昵称
     * @param pageable
     * @return
     */
    List<User> findAllUserByNick(String nick,Pageable pageable);

    /**
     * 通过手机号查询用户,只返回条
     * @param phone
     * @return
     */
    User findOneByPhone(String phone);

    /**
     * 通过手机号查询多个用户
     * @param phone
     * @param pageable
     * @return
     */
    Page<User> getUserByPhone(String phone, Pageable pageable);


    @Query("select user from User as user where user.phone=:phone and user.unsubscribe =:unsubscribe" )
    User getUserByPhoneAndUnsubscribe(@Param("phone")String phone, @Param("unsubscribe")Integer unsubscribe);


    @Query("select u from User as u where u.phone=:phone and u.unsubscribe=:unSubscribe")
    Page<User> getUserByPhoneAndUnsubscribe(
            @Param("phone") String phone,
            @Param("unSubscribe") Integer unSubscribe,
            Pageable pageable
    );

    @Query("select u from User as u where u.svip=:svip")
    Page<User> findAllBySvip(@Param("svip") Integer svip, Pageable pageable);

    @Query("select u from User as u where u.svip=3 or u.svip=4")
    Page<User> findAllBySvipLevelThreeAndFour(
        Pageable pageable
    );

    @Query("select u from User as u where u.svip > 0")
    Page<User> findAllSvip(Pageable pageable);

    @Query("select u from User as u where u.isAbilityPlan > 0")
    Page<User> findAllAbilityPlan(Pageable pageable);

}