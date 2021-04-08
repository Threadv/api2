package com.ifenghui.storybookapi.app.express.dao;

import com.ifenghui.storybookapi.app.express.entity.ExpressCenterMag;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressCenterMagDao extends JpaRepository<ExpressCenterMag, Integer> {

    /**
     * 查找时间关联的杂志，用于某月的封面展示
     * @param year
     * @param month
     * @param sort
     * @return
     */
    List<ExpressCenterMag> findAllByYearAndMonth(Integer year, Integer month, Sort sort);

    @Query("select e from ExpressCenterMag as e where e.year=:year and e.month=:month")
    List<ExpressCenterMag> findAllByYearAndMonth1(@Param("year") Integer year, @Param("month") Integer month);


}
