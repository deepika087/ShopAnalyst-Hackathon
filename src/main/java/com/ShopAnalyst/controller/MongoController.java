package com.ShopAnalyst.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ShopAnalyst.constants.Constants;
import com.ShopAnalyst.models.Member;
import com.ShopAnalyst.util.Jsonreader;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@RestController
public class MongoController {

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	public String getPingResponse() {
		return "Deepika";
	}

	@RequestMapping(value = "/mongoDbNames", method = RequestMethod.GET)
	public String getMongoStatus()  {

		MongoClient client = null;
		try {
			client = new MongoClient("localhost", 27017);
			List<String> dbs = client.getDatabaseNames();

			String result = " ";
			for(String db : dbs) {
				result = result + db.toUpperCase() + "(" + db + ")" + " : ";

				DB dbPointer = client.getDB(db);
				Set<String> tables = dbPointer.getCollectionNames();
				String collNames = "";
				for (String coll : tables) {
					collNames += coll + " " ;
				}
				result = result + collNames + ";"+ "\n";

			}
			return result;
		} catch (UnknownHostException e) {
			return "exception occured";
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	@RequestMapping(value = "/insertData", method = RequestMethod.GET)
	public String insertData() {
		
		MongoClient client = null;
		try {
			client = new MongoClient("localhost", 27017);
			DB db = client.getDB(Constants.DB_NAME);
			
			//1. Always drop the existing collection if present
			DBCollection collection = db.getCollection(Constants.COLL_NAME);
			collection.drop();
			
			//2. Now insert the data
			Jsonreader reader = new Jsonreader(collection);
			int count = reader.loadDataInMongo();
			return "Inserted " + count + "records in mongo db" + "\nDB_NAME = " + Constants.DB_NAME + "\nCOLLECTION = " + Constants.COLL_NAME ;
		} catch (UnknownHostException e) {
			return "exception occured";
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
	
	@RequestMapping(value = "/displayMongoData", method = RequestMethod.GET)
	public String displayMongoData() {
		MongoClient client = null;
		List<Member> memberData = new ArrayList<Member>();
		try {
			client = new MongoClient("localhost", 27017);
			DB db = client.getDB(Constants.DB_NAME);
			DBCursor cursor = db.getCollection(Constants.COLL_NAME).find();
			
			while(cursor.hasNext()) {
				final BasicDBObject dbo = (BasicDBObject)cursor.next();
				final Member member = Member.getMemberFromMongo(dbo);
				memberData.add(member);				
			}
		} catch (UnknownHostException e) {
			final Member member = new Member();
			member.setErrorMessage("exception occured");
			memberData.add(member);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		
		if (memberData.size() == 0) {
			final Member member = new Member();
			member.setErrorMessage("No element found. Empty Collection");
			memberData.add(member);
		}
		Gson gson = new Gson();
		return gson.toJson(memberData);
	}

}
