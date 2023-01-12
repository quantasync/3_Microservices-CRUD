package com.m3.utils;

import com.google.gson.Gson;
import com.m3.db.entity.User;

public class JsonUtil {
	public final static Gson gson = new Gson();

	public static User fromJsontoUser(String jsonstudent) throws Exception {
		return gson.fromJson(jsonstudent, User.class);
	}
}
