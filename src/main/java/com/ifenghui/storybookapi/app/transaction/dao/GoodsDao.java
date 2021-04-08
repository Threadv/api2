package com.ifenghui.storybookapi.app.transaction.dao;



import com.ifenghui.storybookapi.app.transaction.entity.goods.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@Deprecated
public interface GoodsDao extends JpaRepository<Goods, Long> {

    @Query("from Goods g where g.type <5 and g.status=1")
    Page<Goods> getGoodsByPage( Pageable pageable);

}
