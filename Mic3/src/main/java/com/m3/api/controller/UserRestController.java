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
	
	@GetMapping("/getall/lastnames")
	public ArrayList<String> getAllLastNames() {
		return userService.getAllLastNames();
	}
	
	@GetMapping("/get/{id}")
	public User getUser(@PathVariable String id) {
		return userService.getUser(id);
	}
	
	@PostMapping("/add/{id}")
	public void postUser(@RequestBody User user, @PathVariable String id) {
		userService.postUser(user, id);
	}

	@PutMapping("/update/{id}")
	public void putUser(@RequestBody User user, @PathVariable String id) {
		userService.putUser(user, id);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
}
