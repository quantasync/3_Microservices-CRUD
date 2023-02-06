package com.m3.db.repository;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.m3.db.DBContext;
import com.m3.db.entity.User;
import com.m3.utils.JsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserRepository {
	@Value("${db.mongo.connection}")
	private String mongoUrl;
	private MongoCollection<Document> collection;

	@PostConstruct
    private void postConstruct() {
		collection = DBContext.fetchCollection(mongoUrl, "data", "Users", Document.class);
    }
	
	public ArrayList<String> getAllLastNames() throws Exception { 
		MongoCursor<Document> cursor = DBContext.fetchCollectionCursor(mongoUrl, "data", "Users", Document.class);
		var lastNames = new ArrayList<String>();
		try {
			while (cursor.hasNext()) {
				lastNames.add(JsonUtil.fromJsontoUser(cursor.next().toJson()).getLastName());
			}
		} finally {
			cursor.close();
		}
		return lastNames;
	}
	
	public String getLastName(String id) throws Exception {
		Document myDoc = collection.find(eq("_id", id)).first();
		return JsonUtil.fromJsontoUser(myDoc.toJson()).getLastName();
	}
	
	public void addLastName(User user) throws Exception {
		Document found = (Document) collection.find(new Document("_id", user.get_id())).first();

		if (found != null) {
			Bson updatevalue = new Document("lastName", user.getLastName());
			Bson updateoperation = new Document("$set", updatevalue);
			collection.updateOne(found, updateoperation);
		}
	}

	public void updateLastName(User user) throws Exception {
		MongoCollection<User> collection = DBContext.fetchCollection(mongoUrl, "data", "Users", User.class);
		collection.updateOne(new Document("_id", user.get_id()), Updates.set("lastName", user.getLastName()));
	}

	public void deleteLastName(String id) {
		DBObject update = new BasicDBObject();
		update.put("$unset", new BasicDBObject("lastName", ""));
		collection.updateOne(new BasicDBObject("_id", id), (Bson) update);
	}
}
