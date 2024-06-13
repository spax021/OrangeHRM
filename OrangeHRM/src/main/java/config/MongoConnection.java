package config;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

	private String uri = PropertiesFile.getMongoUri();
	private ConnectionString connectionString = new ConnectionString(uri);
	private MongoClient clientURI = MongoClients.create(connectionString);
	private MongoDatabase dataBase = clientURI.getDatabase("Orange");
	private MongoCollection<Document> collection;
	
	public MongoCollection<Document> getCollection(String collect) {
		return collection = dataBase.getCollection(collect);
	}
	
}
