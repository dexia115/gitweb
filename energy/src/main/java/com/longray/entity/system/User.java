package com.longray.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.longray.entity.BaseEntity;

@Entity
@Table(name="account_user")
public class User extends BaseEntity{
	
	@Column(length=20)
	private String username;
	
	@Column(length=50)
	private String password;
	
	@Column(length=36)
	private String salts;
	
	private boolean locked = false;
	
	@Column(length=2)
	private String sex;
	
	@Column(length=20)
	private String telphone;
	
	@Column(length=20)
	private String realname;
	
	@Column(length=20)
	private String email;
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSalts() {
		return salts;
	}

	public void setSalts(String salts) {
		this.salts = salts;
	}

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public String getCredentialsSalt(){
		return username+salts;
	}

}
