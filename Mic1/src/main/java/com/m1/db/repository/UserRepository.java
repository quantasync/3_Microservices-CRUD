package com.m1.db.repository;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.m1.db.DBContext;
import com.m1.db.entity.User;
import com.m1.utils.JsonUtil;
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
	
	public ArrayList<User> getAll() throws Exception { 
		MongoCursor<Document> cursor = DBContext.fetchCollectionCursor(mongoUrl, "data", "Users", Document.class);
		var usersWithIdAndFirstName = new ArrayList<User>();
		try {
			while (cursor.hasNext()) {
				User body = new User();
				User user = new User();
				body = JsonUtil.fromJsontoUser(cursor.next().toJson());
				user.set_id(body.get_id());
				user.setFirstName(body.getFirstName());
				usersWithIdAndFirstName.add(user);
			}
		} finally {
			cursor.close();
		}
		return usersWithIdAndFirstName;
	}

	public String get(String id) throws Exception {
		Document myDoc = collection.find(eq("_id", id)).first();
		return JsonUtil.fromJsontoUser(myDoc.toJson()).getFirstName();
	}

	public void addFirstName(User user, String id) {
		var document = new Document("_id", id);
		document.put("firstName", user.getFirstName());
		collection.insertOne(document);
	}

	public void updateFirstName(User user, String id) throws Exception {
		MongoCollection<User> collection = DBContext.fetchCollection(mongoUrl, "data", "Users", User.class);
		collection.updateOne(new Document("_id", id), Updates.set("firstName", user.getFirstName()));
	}

	public void deleteFirstName(String id) {
		DBObject update = new BasicDBObject();
		update.put("$unset", new BasicDBObject("firstName", ""));
		collection.updateOne(new BasicDBObject("_id", id), (Bson) update);
	}
}