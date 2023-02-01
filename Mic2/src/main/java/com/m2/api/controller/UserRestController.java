package com.m2.api.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.m2.db.entity.User;
import com.m2.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
	
	private final UserService userService;
	
	@GetMapping("/getall")
	public ArrayList<User> getAllMiddleNames() {
		return userService.getAllMiddleAndLastNames();
	}
	
	@GetMapping("/get/{id}")
	public User get(@PathVariable String id) {
		return userService.get(id);
	}
	
	@PostMapping("/add/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody User user, @PathVariable String id) {
		user.set_id(id);
		userService.create(user);
	}

	@PutMapping("/update/{id}")
	public void update(@RequestBody User user, @PathVariable String id) {
		user.set_id(id);
		userService.update(user);
	}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable String id) {
		userService.delete(id);
	}
}