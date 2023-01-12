package com.m1.db.repository;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.m1.db.DBContext;
import com.m1.db.entity.User;
import com.m1.utils.JsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

public class UserRepository {
	
	public static String getFirstName(String id) throws Exception {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		Document myDoc = collection.find(eq("_id", id)).first();
		return JsonUtil.fromJsontoUser(myDoc.toJson()).getFirstName();
	}

	public static void addFirstName(User user, String id) {
		try {
			MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
			Document document = new Document("_id", id);
			document.put("firstName", user.getFirstName());
			collection.insertOne(document);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateFirstName(User user, String id) throws Exception {
		MongoCollection<User> collection = DBContext.fetchCollection("data", "Users", User.class);
		collection.updateOne(new Document("_id", id), Updates.set("firstName", user.getFirstName()));
	}

	public static void deleteFirstName(String id) {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		DBObject update = new BasicDBObject();
		update.put("$unset", new BasicDBObject("firstName", ""));
		collection.updateOne(new BasicDBObject("_id", id), (Bson) update);
	}
}