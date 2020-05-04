package com.spring.cachesync.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.cachesync.domain.User;
import com.spring.cachesync.event.UserEvent;
import com.spring.cachesync.repository.UserRepository;

@Component
@Configuration
@PropertySource(value = "classpath:application.properties")
public class UserService implements ApplicationEventPublisherAware {

	@Autowired
	UserRepository repository;
	@Value("#{'${server.list}'.split(',')}")
	private List<String> servers;

	public ConcurrentHashMap<String, List<User>> cache = new ConcurrentHashMap();
	private ApplicationEventPublisher publisher;

	public void initBean() {
		System.out.println("Init Bean for : EmployeeDAOImpl");
	}

	public List<User> getUsers() {
		Object hostObj = cache.get("host");
		Object guestObj = cache.get("guest");
		List<User> hostUserList = (List<User>) hostObj;
		List<User> guestUserList = (List<User>) guestObj;
		if (null != hostUserList && hostUserList.size() > 0) {
			System.err.println("data loaded from cache!!!");
			return hostUserList;
		} else if (null != guestUserList && guestUserList.size() > 0) {
			System.err.println("data loaded from cache!!!");
			publishEvent(guestUserList);
			return guestUserList;
		} else {
			System.err.println("data loaded from database!!!");
			List<User> userList = repository.findAll();
			try {
				cache.put("host", userList);
				publishEvent(userList);
			} catch (Exception e) {

			}
			return userList;
		}
	}

	public HashMap<String, String> createOrUpdateUser(User entity) {
		List<User> responseString = null;
		Optional<User> employee = repository.findById(entity.getId());
		HashMap<String, String> response = new HashMap<>();
		if (employee.isPresent()) {
			User newEntity = employee.get();
			newEntity.setUserName(entity.getUserName());
			newEntity.setRole(entity.getRole());
			try {
				newEntity = repository.save(newEntity);
				responseString = repository.findAll();
				cache.put("host", responseString);
				response.put("STATUS", "SUCCESS");
			} catch (Exception e) {
				response.put("STATUS", "FAILED");
			}
			publishEvent(responseString);
			return response;
		} else {
			try {
				entity = repository.save(entity);
				responseString = repository.findAll();
				cache.put("host", responseString);
				response.put("STATUS", "SUCCESS");
			} catch (Exception e) {
				System.err.println("Error : " + e.getMessage());
				response.put("STATUS", "FAILED");
			}
			publishEvent(responseString);
			return response;
		}
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;

	}

	public List<User> udpateCache(List<User> entity) {
		cache.put("guest", entity);
		return entity;
	}

	public String getLocalHost() {
		String url = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
		String localhost = null;
		try {
			URL myUrl = new URL(url);
			localhost = myUrl.getAuthority();
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		return localhost;
	}

	public List<String> serverList() {
		List<String> list = new ArrayList<String>();
		String host = getLocalHost();
		for (String guest : servers) {
			if (!host.equals(guest)) {
				list.add(guest);
			}
		}
		return list;
	}
	private void publishEvent(List<User> responseString) {
		try {
			publisher.publishEvent(new UserEvent(this, "add", responseString, serverList()));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}