package com.spring.cachesync;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.cachesync.domain.User;
import com.spring.cachesync.service.UserService;
@RunWith(SpringRunner.class)
@SpringBootTest
class CacheSyncApplicationTests {
	@Autowired
	UserService service;
	@Test
	public void createUser() {
		/*
		 * User user = new User(); user.setId(1); user.setUserName("un");
		 * user.setRole("role"); Assert.assertEquals("SUCCESS", actual);
		 */}
	@Test
	void contextLoads() {
	}

}
