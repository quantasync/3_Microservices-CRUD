package com.m2.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m2.db.entity.User;
import com.m2.db.repository.UserRepository;
import com.m2.webclient.WebClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private WebClientService webClientService;
	
	@Autowired
	private UserRepository userRepository;
	
	public ArrayList<User> getAllMiddleAndLastNames() {
		var middleAndLastNames = new ArrayList<User>();
		try {
			var middleNames = userRepository.getAllMiddleNames();
			var lastNames = webClientService.getAllLastNames();
			for(int i = 0; i < middleNames.size(); i++) {
				var user = new User(middleNames.get(i), lastNames.get(i));
				middleAndLastNames.add(user);
			}
		} catch (Exception e) {
			System.out.println("Error while sending GET_ALL_MIDDLE_NAMES in microservice 2");
			e.printStackTrace();
		}
		return middleAndLastNames;
	}
	
	public User get(String id) {
		User user = new User();
		try {
			user = webClientService.getLastName(id);
			user.setMiddleName(userRepository.getMiddleName(id)); 
		} catch (Exception e) {
			System.out.println("Error while sending GET in microservice 2");
			e.printStackTrace();
		}
		return user;
	}
	
	public void create(User user) {
		userRepository.addMiddleName(user);
		webClientService.createLastName(user);
	}

	public void update(User user) {
		try {
			userRepository.updateMiddleName(user);
		} catch (Exception e) {
			System.out.println("Error while sending PUT in microservice 2");
			e.printStackTrace();
		}
		webClientService.updateLastName(user);
	}

	public void delete(String id) {
		userRepository.deleteMiddleName(id);
		webClientService.deleteLastName(id);
	}
}
