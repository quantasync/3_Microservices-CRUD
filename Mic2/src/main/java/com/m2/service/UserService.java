package com.m2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.m2.db.entity.User;
import com.m2.db.repository.UserRepository;
import com.m2.webclient.WebClientService;

@Service
@Component
public class UserService {
	
	@Autowired
	private WebClientService webClientService;
	
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
