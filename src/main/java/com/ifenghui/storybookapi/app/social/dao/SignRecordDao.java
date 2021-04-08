package com.ifenghui.storybookapi.app.social.dao;

import com.ifenghui.storybookapi.app.social.entity.SignRecord;
import com.ifenghui.storybookapi.style.SignRecordStyle;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SignRecordDao extends JpaRepository<SignRecord, Integer> {


    /**
     * 查找记录
     * @param userId
     * @param type
     * @return
     */
    SignRecord getSignRecordByUserIdAndType(Integer userId,Integer type);


    /**
     * 判断是否有添加记录
     * @param userId
     * @param type
     * @return
     */
    @Cacheable(cacheNames = "countSignRecordByUserIdAndType",key = "'countSignRecordByUserIdAndType'+#p0+'_'+#p1")
    @Query("select count(s) from SignRecord as s where s.userId=:userId and s.type=:type")
    Long countSignRecordByUserIdAndType(@Param("userId")Integer userId,  @Param("type") Integer type);


    @CacheEvict(cacheNames = "countSignRecordByUserIdAndType",key = "'countSignRecordByUserIdAndType'+#p0.userId+'_'+#p0.type")
    @Override
    SignRecord save(SignRecord signRecord);
}
