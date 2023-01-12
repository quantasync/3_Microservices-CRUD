package com.m2.db.repository;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.m2.utils.JsonUtil;
import com.m2.db.DBContext;
import com.m2.db.entity.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

@Repository
public class UserRepository {
	
	public static String getMiddleName(String id) throws Exception {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		Document myDoc = collection.find(eq("_id", id)).first();
		return JsonUtil.fromJsontoUser(myDoc.toJson()).getMiddleName();
	}
	
	public static void addMiddleName(User user, String id) {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		Document found = (Document) collection.find(new Document("_id", id)).first();

		if (found != null) {
			Bson updatevalue = new Document("middleName", user.getMiddleName());
			Bson updateoperation = new Document("$set", updatevalue);
			collection.updateOne(found, updateoperation);
		}
	}

	public static void updateMiddleName(User user, String id) throws Exception  {
		MongoCollection<User> collection = DBContext.fetchCollection("data", "Users", User.class);
		collection.updateOne(new Document("_id", id),
				Updates.set("middleName", user.getMiddleName()));
	}

	public static void deleteMiddleName(String id) {
		MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
		DBObject update = new BasicDBObject();
		update.put("$unset", new BasicDBObject("middleName", ""));
		collection.updateOne(new BasicDBObject("_id", id), (Bson) update);
	}
}
