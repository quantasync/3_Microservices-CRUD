package com.m3.db.repository;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.m3.utils.JsonUtil;
import com.m3.db.DBContext;
import com.m3.db.entity.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

public class UserRepository {
	
	public static String getLastName(String id) throws Exception {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		Document myDoc = collection.find(eq("_id", id)).first();
		return JsonUtil.fromJsontoUser(myDoc.toJson()).getLastName();
	}
	
	public static void addLastName(User user, String id) throws Exception {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		Document found = (Document) collection.find(new Document("_id", id)).first();

		if (found != null) {
			Bson updatevalue = new Document("lastName", user.getLastName());
			Bson updateoperation = new Document("$set", updatevalue);
			collection.updateOne(found, updateoperation);
		}
	}

	public static void updateLastName(User user, String id) throws Exception {
		MongoCollection<User> collection = DBContext.fetchCollection("data", "Users", User.class);
		collection.updateOne(new Document("_id", id), Updates.set("lastName", user.getLastName()));
	}

	public static void deleteLastName(String id) {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		DBObject update = new BasicDBObject();
		update.put("$unset", new BasicDBObject("lastName", ""));
		collection.updateOne(new BasicDBObject("_id", id), (Bson) update);
	}
}
