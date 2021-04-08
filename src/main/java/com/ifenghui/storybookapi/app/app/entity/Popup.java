package com.ifenghui.storybookapi.app.app.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.config.MyEnv;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 弹窗
 * @author wsl
 *
 */
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"},ignoreUnknown =true)
@Table(name="story_popup")
public class Popup implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String title;

	private String img;

	private Date createTime;

	private Date beginTime;

	private Date endTime;

	/**
	 * 1启用 0关闭
	 */
	private Integer status;

	/**
	 * 程序名称,如果填写则使用程序控制，不再依赖开放时间。如果没有填写，依赖createtime
	 */
	String className;

	Integer imgWidth;

	Integer imgHeight;

	Integer isShowBtn;

	String btnName;

	String btnColor;

	String btnTextColor;

	Integer btnBottom;

	String redirectUrl;


	public Popup(){

	}

//	public Popup(AdmResource admResource){
//		this.id=admResource.getId();
//		this.imgWidth=admResource.getMediaWidth();
//		this.imgHeight=admResource.getMediaHeight();
//		this.img=admResource.getMedia();
//		this.redirectUrl=admResource.getLink();
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public String getImgUrl() {
		return MyEnv.env.getProperty("oss.url")+"/"+img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}

	public Integer getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}

	public Integer getIsShowBtn() {
		return isShowBtn;
	}

	public void setIsShowBtn(Integer isShowBtn) {
		this.isShowBtn = isShowBtn;
	}

	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getBtnColor() {
		return btnColor;
	}

	public Integer getBtnBottom() {
		return btnBottom;
	}

	public void setBtnBottom(Integer btnBottom) {
		this.btnBottom = btnBottom;
	}

	public void setBtnColor(String btnColor) {
		this.btnColor = btnColor;
	}

	public String getBtnTextColor() {
		return btnTextColor;
	}

	public void setBtnTextColor(String btnTextColor) {
		this.btnTextColor = btnTextColor;
	}
}
