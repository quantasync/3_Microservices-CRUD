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
		var usersWithIdAndFirstName = new ArrayList<User>();
		try {
		 	users.addAll(feignService.getAllMiddleAndLastNames());
			usersWithIdAndFirstName = userRepository.getAll();
			for(int i = 0; i < usersWithIdAndFirstName.size(); i++) {
				var user = new User(usersWithIdAndFirstName.get(i).get_id(), usersWithIdAndFirstName.get(i).getFirstName(), users.get(i).getMiddleName(),users.get(i).getLastName());
				users.set(i, user);
			}
			
		} catch (Exception e) {
			System.out.println("Error while sending GETALL in microservice 1");
			e.printStackTrace();
		}
		return users;
	}
	
	public User get(String id) {
		User user = new User();
		try {
			user = feignService.getMiddleAndLastName(id);
			user.setFirstName(userRepository.get(id));
		} catch (Exception e) {
			System.out.println("Error while sending GET in microservice 1");
			e.printStackTrace();
		}
		return user;
	}
	
	public User create(User user) {
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
	
	public User update(User user, String id) {
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
	
	public String delete(String id){
		userRepository.deleteFirstName(id);
		feignService.deleteMiddleAndLastName(id);
		return "Deleted!";
	}
}
