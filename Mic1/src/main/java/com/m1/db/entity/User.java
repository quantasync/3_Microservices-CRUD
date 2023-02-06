package com.m1.db.entity;

import lombok.Data;

@Data
public class User {
	private String _id;
	private String firstName;
	private String middleName;
	private String lastName;
	
	public User(String _id, String firstName, String middleName, String lastName) {
	this._id = _id;
	this.firstName = firstName;
	this.middleName = middleName;
	this.lastName = lastName;
	}
	
	public User() {
	}
}