package com.pea.wechat.eshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "E_USER")
public class User extends AbstractEntity {

	private static final long serialVersionUID = -5844347335060823058L;

	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	@Column(name = "OPENID")
	private String openID;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

}
