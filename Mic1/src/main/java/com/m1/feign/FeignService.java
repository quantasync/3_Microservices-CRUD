package com.m1.feign;

import java.util.ArrayList;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.m1.db.entity.User;

@FeignClient(name = "m1", url = "http://mic2:8092")
public interface FeignService {
	
	@GetMapping(value = "/user/getall")
	ArrayList<User> getAllMiddleAndLastNames();

	@GetMapping(value = "/user/get/{id}")
	User getMiddleAndLastName(@PathVariable String id);
	
	@PostMapping(value = "/user/add/{id}")
	User createMiddleAndLastName(@RequestBody User user, @PathVariable String id);

	@PutMapping(value = "/user/update/{id}")
	User updateMiddleAndLastName(@RequestBody User user, @PathVariable String id);

	@DeleteMapping(value = "/user/delete/{id}")
	String deleteMiddleAndLastName(@PathVariable String id);
}