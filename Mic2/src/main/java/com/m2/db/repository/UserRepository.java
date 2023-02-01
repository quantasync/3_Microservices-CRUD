package com.m2.db.repository;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.m2.utils.JsonUtil;
import com.m2.db.DBContext;
import com.m2.db.entity.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;

@Repository
public class UserRepository {
	
	private static MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);
	
	public ArrayList<String> getAllMiddleNames() throws Exception { 
		MongoCursor<Document> cursor = DBContext.fetchCollectionCursor("data", "Users", Document.class);
		var middleNames = new ArrayList<String>();
		try {
			while (cursor.hasNext()) {
				middleNames.add(JsonUtil.fromJsontoUser(cursor.next().toJson()).getMiddleName());
			}
		} finally {
			cursor.close();
		}
		return middleNames;
	}
	
	public static String getMiddleName(String id) throws Exception {
		Document myDoc = collection.find(eq("_id", id)).first();
		return JsonUtil.fromJsontoUser(myDoc.toJson()).getMiddleName();
	}
	
	public static void addMiddleName(User user) {
		Document found = (Document) collection.find(new Document("_id", user.get_id())).first();

		if (found != null) {
			Bson updatevalue = new Document("middleName", user.getMiddleName());
			Bson updateoperation = new Document("$set", updatevalue);
			collection.updateOne(found, updateoperation);
		}
	}

	public static void updateMiddleName(User user) throws Exception  {
		MongoCollection<User> collection = DBContext.fetchCollection("data", "Users", User.class);
		collection.updateOne(new Document("_id", user.get_id()),
				Updates.set("middleName", user.getMiddleName()));
	}

	public static void deleteMiddleName(String id) {
		DBObject update = new BasicDBObject();
		update.put("$unset", new BasicDBObject("middleName", ""));
		collection.updateOne(new BasicDBObject("_id", id), (Bson) update);
	}
}
