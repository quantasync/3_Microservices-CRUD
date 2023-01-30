package com.m1.db.repository;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import com.m1.db.DBContext;
import com.m1.db.entity.User;
import com.m1.utils.JsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;

@Component
public class UserRepository {

	private MongoCollection<Document> collection = DBContext.fetchCollection("data", "Users", Document.class);

	public ArrayList<String> getAllIDs() throws Exception { 
		MongoCursor<Document> cursor = DBContext.fetchCollectionCursor("data", "Users", Document.class);
		var IDs = new ArrayList<String>();
		try {
			while (cursor.hasNext()) {
				IDs.add(JsonUtil.fromJsontoUser(cursor.next().toJson()).get_id());
			}
		} finally {
			cursor.close();
		}
		return IDs;
	}
	
	public ArrayList<String> getAllFirstNames() throws Exception { 
		MongoCursor<Document> cursor = DBContext.fetchCollectionCursor("data", "Users", Document.class);
		var firstNames = new ArrayList<String>();
		try {
			while (cursor.hasNext()) {
				firstNames.add(JsonUtil.fromJsontoUser(cursor.next().toJson()).getFirstName());
			}
		} finally {
			cursor.close();
		}
		return firstNames;
	}

	public String getFirstName(String id) throws Exception {
		Document myDoc = collection.find(eq("_id", id)).first();
		return JsonUtil.fromJsontoUser(myDoc.toJson()).getFirstName();
	}

	public void addFirstName(User user, String id) {
		var document = new Document("_id", id);
		document.put("firstName", user.getFirstName());
		collection.insertOne(document);
	}

	public void updateFirstName(User user, String id) throws Exception {
		MongoCollection<User> collection = DBContext.fetchCollection("data", "Users", User.class);
		collection.updateOne(new Document("_id", id), Updates.set("firstName", user.getFirstName()));
	}

	public void deleteFirstName(String id) {
		DBObject update = new BasicDBObject();
		update.put("$unset", new BasicDBObject("firstName", ""));
		collection.updateOne(new BasicDBObject("_id", id), (Bson) update);
	}
}