package com.spring.cachesync.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User{
	/**
	 * 
	 */
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  	long id;
    
    @Column(name="userName")
    private String userName;
    
    @Column(name="role")
    private String role;
      
  	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
    public String toString() {
        return "User [user_name=" + userName + 
                ", role=" + role + "]";
    }
}