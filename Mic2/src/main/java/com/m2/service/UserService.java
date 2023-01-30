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
	
	public ArrayList<String> getAllMiddleNames() { 
		var middleNames = new ArrayList<String>();
		try {
			middleNames.addAll(userRepository.getAllMiddleNames());
		} catch (Exception e) {
			System.out.println("Error while sending GET_ALL_MIDDLE_NAMES in microservice 2");
			e.printStackTrace();
		}
		return middleNames;
	}
	
	public ArrayList<String> getAllLastNames() { 
		var lastNames = new ArrayList<String>();
		try {
			lastNames.addAll(webClientService.getAllLastNames());
		} catch (Exception e) {
			System.out.println("Error while sending GET_ALL_LAST_NAMES in microservice 2");
			e.printStackTrace();
		}
		return lastNames;
	}
	
	public User getUser(String id) {
		User user = new User();
		try {
			user = webClientService.getLastName(id);
			user.setMiddleName(UserRepository.getMiddleName(id)); 
		} catch (Exception e) {
			System.out.println("Error while sending GET in microservice 2");
			e.printStackTrace();
		}
		return user;
	}
	
	public void postUser(User user, String id) {
		UserRepository.addMiddleName(user, id);
		webClientService.createLastName(user, id);
	}

	public void putUser(User user, String id) {
		try {
			UserRepository.updateMiddleName(user, id);
		} catch (Exception e) {
			System.out.println("Error while sending PUT in microservice 2");
			e.printStackTrace();
		}
		webClientService.updateLastName(user, id);
	}

	public void deleteUser(String id) {
		UserRepository.deleteMiddleName(id);
		webClientService.deleteLastName(id);
	}
}
