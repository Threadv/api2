package com.ifenghui.storybookapi.app.presale.dao;



import com.ifenghui.storybookapi.app.presale.entity.PreSaleGiftCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PreSaleGiftCountDao extends JpaRepository<PreSaleGiftCount, Integer> {

    /**
     * 通过giftId查询
     * @param giftId
     * @return
     */
    @Query("select c from PreSaleGiftCount as c where c.giftId=:giftId")
    PreSaleGiftCount findByGiftId(@Param("giftId") Integer giftId);
}
