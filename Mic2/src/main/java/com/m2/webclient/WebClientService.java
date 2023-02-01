package com.m2.webclient;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.m2.db.entity.User;

import reactor.core.publisher.Mono;

@Service
public class WebClientService {
	WebClient webClient = WebClient.builder().baseUrl("http://localhost:8093/user")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
	
	public ArrayList<String> getAllLastNames() {
		return webClient.get()
				.uri("/getall")
				.retrieve()
				.bodyToMono(ArrayList.class).block();
	}

	public User getLastName(String id) {
		return webClient.get()
				.uri("/get/" + id)
				.retrieve()
				.bodyToMono(User.class).block();
	}
		
	public ClientResponse createLastName(User user) {
		return webClient.post().uri("/add/").body(Mono.just(user), User.class).retrieve()
				.bodyToMono(ClientResponse.class).block();
	}

	public ClientResponse updateLastName(User user) {
		return webClient.put().uri("/update/").body(Mono.just(user), User.class).retrieve()
				.bodyToMono(ClientResponse.class).block();
	}

	public void deleteLastName(String id) {
		webClient.delete().uri("/delete/" + id).retrieve().bodyToMono(String.class).block();
	}
}
