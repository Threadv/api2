package com.ifenghui.storybookapi.adminapi.manager.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ifenghui.storybookapi.style.PublishType;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="story_manager")
public class Manager implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String username;

	@JsonIgnore
	private String password;
	
	private String nick;

	private Integer publish;

	private String roles;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}



	public Integer getPublish() {
		return publish;
	}

//	public void setPublish(Integer publish) {
//		this.publish = publish;
//	}

	public PublishType getPublishType(){
		if(publish==null){
			return PublishType.UNPUBLISH;
		}
		return PublishType.getById(publish);
	}

	public void setPublishType(PublishType publishType){
		this.publish=publishType.getId();
	}


	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
