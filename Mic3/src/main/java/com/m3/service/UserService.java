package com.m3.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m3.db.entity.User;
import com.m3.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public ArrayList<String> getAllLastNames() { 
		var lastNames = new ArrayList<String>();
		try {
			lastNames.addAll(userRepository.getAllLastNames());
		} catch (Exception e) {
			System.out.println("Error while sending GET_ALL_LAST_NAMES in microservice 3");
			e.printStackTrace();
		}
		return lastNames;
	}
	
	public User get(String id) {
		User user = new User();
		try {
			user.set_id(id);
			user.setLastName(userRepository.getLastName(id));
		} catch (Exception e) {
			System.out.println("Error while sending GET in microservice 3");
			e.printStackTrace();
		}
		return user;
	}

	public void create(User user) {
		try {
			userRepository.addLastName(user);
		} catch (Exception e) {
			System.out.println("Error while sending POST in microservice 3");
			e.printStackTrace();
		}
	}

	public void update(User user) {
		try {
			userRepository.updateLastName(user);
		} catch (Exception e) {
			System.out.println("Error while sending PUT in microservice 3");
			e.printStackTrace();
		}
	}

	public void delete(String id) {
		userRepository.deleteLastName(id);
	}
}
