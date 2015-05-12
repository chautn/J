package org.exoplatform.samples.mongodbcrud;

import java.net.UnknownHostException;	
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.WriteResult;


public class DocumentService {
	//
	public static MongoClient client;
	public static DB db;
	public static DBCollection collection;
	
	public DocumentService() throws UnknownHostException {
		//
		client = new MongoClient("localhost", 27017);
		db = client.getDB("cucumber");
		collection = db.getCollection("test");
	}
	
	public void save(Document doc) {
		//
		collection.insert(buildObject(doc));
	}
	
	public DBObject buildObject(Document doc) {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start();
		builder.append("_name", doc.getName());
		builder.append("_description", doc.getDescription());
		return builder.get();
	}
	
	public void close() {
		client.close();
	}
}
