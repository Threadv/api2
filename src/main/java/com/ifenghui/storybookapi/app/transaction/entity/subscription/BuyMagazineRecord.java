package com.ifenghui.storybookapi.app.transaction.entity.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.app.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 期刊购买记录
 */
@Entity
@Table(name="story_buy_magazine_record")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@CmsTable(name="用户购买期刊记录表",addAble = false,editAble = false,deleteAble = false)
/**
 * Created by wml on 2017/2/16.
 */
public class BuyMagazineRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
//    Long storyId;
//    Long userId;
//    @CmsColumn(name="期刊id")
    Long magazineId;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = true, updatable = true)
    @NotFound(action= NotFoundAction.IGNORE)
    User user;


//    @CmsColumn(name="创建时间",inputType = CmsInputType.DATE)
    Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Long magazineId) {
        this.magazineId = magazineId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
