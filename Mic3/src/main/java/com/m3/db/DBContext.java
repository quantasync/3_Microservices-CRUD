package com.m3.db;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class DBContext {
	public static <T> MongoCollection<T> fetchCollection(String mongoUrl, String dbName, String collectionName,
			Class<T> collectionType) {
		return MongoClients.create(mongoUrl).getDatabase(dbName)
				.withCodecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
						CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())))
				.getCollection(collectionName, collectionType);
	}

	public static <T> MongoCursor<T> fetchCollectionCursor(String mongoUrl, String dbName, String collectionName,
			Class<T> collectionType) {
		return fetchCollection(mongoUrl, dbName, collectionName, collectionType).find().iterator();
	}
}
