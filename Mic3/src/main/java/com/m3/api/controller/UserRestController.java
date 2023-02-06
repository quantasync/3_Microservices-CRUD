package com.m3.api.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m3.db.entity.User;
import com.m3.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
	
	private final UserService userService;
	
	@GetMapping("/getall")
	public ArrayList<String> getAllLastNames() {
		return userService.getAllLastNames();
	}
	
	@GetMapping("/get/{id}")
	public User get(@PathVariable String id) {
		return userService.get(id);
	}
	
	@PostMapping("/add/")
	public void create(@RequestBody User user) {
		userService.create(user);
	}

	@PutMapping("/update/")
	public void update(@RequestBody User user) {
		userService.update(user);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		userService.delete(id);
	}
}
