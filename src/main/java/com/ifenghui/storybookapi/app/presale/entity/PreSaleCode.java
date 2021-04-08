package com.ifenghui.storybookapi.app.presale.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.adminapi.controlleradmin.code.resp.CodeTypeItem;
import com.ifenghui.storybookapi.style.VipGoodsStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "story_pre_sale_code")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PreSaleCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    //payId
    Integer payId;

    //userId
    Integer userId;

    //goodsid
    /**
     * 参考枚举
     * @see com.ifenghui.storybookapi.style.VipGoodsStyle
     */
    @Column(name = "goodsId")
    Integer codeType;

    //活动id
    Integer activity_id;

    //码
    String code;

    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date createTime;

    //结束时间时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date endTime;

    //结束时间时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    Date successTime;

    Integer isExpire;

    //状态
    Integer status;

    String name;
//1后台生成 销售，2后台生成赠送，3app生成，4服务号生成
    Integer saleType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

//    public Integer getGoodsId() {
//        return codeType;
//    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public CodeTypeItem getCodeTypeItem(){
        if(codeType==null || codeType==0){
            return null;
        }
        return new CodeTypeItem(VipGoodsStyle.getById(codeType));
    }

    @Deprecated
    public void setGoodsId(Integer goodsId) {
        this.codeType = goodsId;
    }

    public Integer getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Integer getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Integer isExpire) {
        this.isExpire = isExpire;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSaleType() {
        return saleType;
    }

    public void setSaleType(Integer saleType) {
        this.saleType = saleType;
    }
}
