package com.m1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.m1.db.entity.User;

@FeignClient(name = "m1", url = "http://localhost:8092")
public interface FeignService {
	
	@GetMapping(value = "/user/get/{id}")
	User GetMidWithLastName(@PathVariable String id);
	
	@PostMapping(value = "/user/add/{id}")
	String PostMidName(@RequestBody User user, @PathVariable String id);

	@PutMapping(value = "/user/update/{id}")
	String PutMidName(@RequestBody User user, @PathVariable String id);

	@DeleteMapping(value = "/user/delete/{id}")
	String DeleteMidName(@PathVariable String id);
}