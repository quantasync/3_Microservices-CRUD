package com.m2.db.entity;

import lombok.Data;

@Data
public class User {
	private String _id;
	private String firstName;
	private String middleName;
	private String lastName;
	
	public User() {
		
	}
	
	public User(String middleName, String lastName) {
		this.middleName = middleName;
		this.lastName = lastName;
		}
}
