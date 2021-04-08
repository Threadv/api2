package com.ifenghui.storybookapi.app.story.dao;

import com.ifenghui.storybookapi.app.story.entity.IpLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  IpLabelDao extends JpaRepository<IpLabel, Integer> {

    @Query("select label from IpLabel as label where label.parentId=:parentId and label.ipId=:ipId and label.status=1 order by label.orderBy desc")
    List<IpLabel> getIpLabelsByParentIdAndIpId(
            @Param("parentId") Integer parentId,
            @Param("ipId") Integer ipId
    );

    @Query("select label from IpLabel as label where label.ipBrandId=:ipBrandId and label.status=1 order by label.orderBy desc")
    List<IpLabel> getIpLableByIpBrandId(
            @Param("ipBrandId") Integer ipBrandId
    );

}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 