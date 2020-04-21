package com.spring.cachesync.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.spring.cachesync.domain.User;

public class UserEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String eventType;
	private List<User> userDao;
	private List<String> serverList;
	
	public UserEvent(Object source,String eventType, List<User> userDao,List<String> serverList) {
		super(source);
		this.eventType = eventType;
		this.userDao = userDao;
		this.serverList = serverList;
	}

	public String getEventType() {
		return eventType;
	}

	public List<User> getUserDao() {
		return userDao;
	}
	public List<String> getServerList() {
		return serverList;
	}
}
