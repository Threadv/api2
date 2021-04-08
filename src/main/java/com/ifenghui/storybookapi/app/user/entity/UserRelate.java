package com.ifenghui.storybookapi.app.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @program: api2
 * @description:
 * @author: wjs
 * @create: 2018-12-13 09:33
 **/
@Entity
@Table(name = "story_small_program_user_relate")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserRelate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String unionid;

    String phone;

    public UserRelate() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
