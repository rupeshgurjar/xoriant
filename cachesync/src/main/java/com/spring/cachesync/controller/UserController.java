package com.spring.cachesync.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.cachesync.domain.User;
import com.spring.cachesync.exception.RecordNotFoundException;
import com.spring.cachesync.service.UserService;

@RestController
@RequestMapping("/cache-synch")
public class UserController {
	@Autowired
	UserService service;

	@GetMapping
	public ResponseEntity<List<User>> getUsers() {
		List<User> list = service.getUsers();

		return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<HashMap<String, String>> createOrUpdateUser(@RequestBody User user)  {
		
		HashMap<String, String> result = service.createOrUpdateUser(user);
		return new ResponseEntity<HashMap<String, String>>(result, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/cache")
	public ResponseEntity<List<User>> updateCache(@RequestBody List<User> user)  {
		List<User> updated = service.udpateCache(user);
		return new ResponseEntity<List<User>>(updated, new HttpHeaders(), HttpStatus.OK);
	}
}