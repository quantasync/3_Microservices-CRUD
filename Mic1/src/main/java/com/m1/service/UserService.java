package com.m1.service;

import java.util.ArrayList;
import java.util.List;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m1.db.entity.User;
import com.m1.db.repository.UserRepository;
import com.m1.feign.FeignService;
import com.m1.user.utils.UserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private FeignService feignService;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAll() { 
		var users = new ArrayList<User>();
		try {
			var IDs = userRepository.getAllIDs();
			var firstNames = userRepository.getAllFirstNames();
			var middleNames = feignService.getAllMiddleNames();
			var lastNames = feignService.getAllLastNames();
			for(int i = 0; i < firstNames.size(); i++) {
				var user = new User();
				user.set_id(IDs.get(i));
				user.setFirstName(firstNames.get(i));
				user.setMiddleName(middleNames.get(i));
				user.setLastName(lastNames.get(i));
				users.add(user);
			}
			
		} catch (Exception e) {
			System.out.println("Error while sending GETALL in microservice 1");
			e.printStackTrace();
		}
		return users;
	}
	
	public User getUser(String id) {
		User user = new User();
		try {
			user = feignService.getMiddleAndLastName(id);
			user.setFirstName(userRepository.getFirstName(id));
		} catch (Exception e) {
			System.out.println("Error while sending GET in microservice 1");
			e.printStackTrace();
		}
		return user;
	}
	
	public User createUser(User user) {
		String id = UserUtils.generateId();
		try {
			userRepository.addFirstName(user, id);
			feignService.createMiddleAndLastName(user, id);
		} catch (Exception e) {
			System.out.println("Error while sending POST in microservice 1");
			e.printStackTrace();
		}
		user.set_id(id);
		return user;
	}
	
	public User updateUser(User user, String id) {
		try {
			userRepository.updateFirstName(user, id);
			feignService.updateMiddleAndLastName(user, id);
		} catch (Exception e) {
			System.out.println("Error while sending PUT in microservice 1");
			e.printStackTrace();
		}
		user.set_id(id);
		return user;
	}
	
	public String deleteUser(String id){
		userRepository.deleteFirstName(id);
		feignService.deleteMiddleAndLastName(id);
		return "Deleted!";
	}
}
