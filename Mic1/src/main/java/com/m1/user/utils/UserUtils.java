package com.m1.user.utils;

import java.util.UUID;

public class UserUtils {
	public static String generateId() {
		return UUID.randomUUID().toString();
	}
}