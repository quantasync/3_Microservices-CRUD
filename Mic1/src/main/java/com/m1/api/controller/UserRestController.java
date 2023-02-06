package com.m1.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m1.db.entity.User;
import com.m1.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
	
	private final UserService userService;
	
	@GetMapping("/getall")
	public List<User> getAllUsers() {
		return userService.getAll();
	}
	
	@GetMapping("/get/{id}")
	public User getUser(@PathVariable String id) {
		return userService.get(id);
	}
	
	@PostMapping("/add")
	public User createUser(@RequestBody User user) {
		return userService.create(user);
	}

	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable String id, @RequestBody User user) {
		return userService.update(user, id);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id) {
		return userService.delete(id);
	}
}