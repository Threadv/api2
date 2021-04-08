package com.ifenghui.storybookapi.app.user.dao;

/**
 * Created by jia on 2016/12/23.
 */
import com.ifenghui.storybookapi.app.user.entity.UserToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface UserTokenDao  extends JpaRepository<UserToken, Long> {
    @Cacheable(cacheNames = "shiptoken",key = "'shiptoken_'+#p0")
    public UserToken findOneByToken(String token);
    UserToken findOneByUserId(long userId);
    UserToken findOneByUserIdAndDevice(long userId,String device);
    List<UserToken> findUserTokensByUserIdAndDevice(long userId, String device);

    @Query("select userToken from UserToken as userToken where userToken.isValid=1 and userToken.userId=:userId ")
    List<UserToken> findValidUserTokensByUserId(@Param("userId") Long userId, Sort sort);

    @Query("select userToken from UserToken as userToken where userToken.userId=:userId and userToken.deviceUnique=:deviceUnique ")
    UserToken findUserTokenByUserIdAndDeviceUnique(@Param("userId") long userId, @Param("deviceUnique") String deviceUnique);

    @Override
    @CacheEvict(cacheNames = "shiptoken",key = "'shiptoken_'+#p0.token")
    public UserToken save(UserToken token);
}
