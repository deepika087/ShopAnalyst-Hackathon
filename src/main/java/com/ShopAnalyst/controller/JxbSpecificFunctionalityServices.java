package com.ShopAnalyst.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ShopAnalyst.constants.Constants;
import com.ShopAnalyst.models.Member;
import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RestController
@RequestMapping("/services")
public class JxbSpecificFunctionalityServices {

	@RequestMapping(value = "/search_caption", method = RequestMethod.GET)
	public String fulltextSearch(@RequestParam(value="query", required=true) String query, 
			@RequestParam(value="page", required=false, defaultValue="0") String pageNumber) {
		
		MongoClient client = null;
		final List<Member> memberData = new ArrayList<Member>();
		try {
			client = new MongoClient("localhost", 27017);
			DB db = client.getDB(Constants.DB_NAME);
			DBCollection collection = db.getCollection(Constants.COLL_NAME);
			
			BasicDBObject regexQuery = new BasicDBObject();
			regexQuery.put(Constants.CAPTION, 
				new BasicDBObject("$regex", query)
				.append("$options", "i"));
			
			DBCursor cursor = null;
			if (new Integer(pageNumber) > 0) {
				final int endIndex = new Integer(pageNumber) * Constants.PAGE_SIZE;
				final int startIndex = endIndex - Constants.PAGE_SIZE;
				cursor = collection.find(regexQuery).limit(Constants.PAGE_SIZE).skip(startIndex);
			} else {
				cursor = collection.find(regexQuery);
			}
			
			if (cursor != null) {
				while (cursor.hasNext()) {
					
					final BasicDBObject dbo = (BasicDBObject)cursor.next();
					final Member member = Member.getMemberFromMongo(dbo);
					memberData.add(member);				
				}
			}
		} catch(UnknownHostException ex) {
			ex.printStackTrace();
		}
		if (memberData.size() == 0) {
			final Member member = new Member();
			member.setErrorMessage("No element found. Empty Collection");
			memberData.add(member);
		}
		Gson gson = new Gson();
		return gson.toJson(memberData);
	}
	
	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public String count() {

		MongoClient client = null;
		int count = -1;
		try {
			client = new MongoClient("localhost", 27017);
			DB db = client.getDB(Constants.DB_NAME);
			DBCursor cursor = db.getCollection(Constants.COLL_NAME).find();
			
			while(cursor.hasNext()) {
				count = cursor.size();	
				break;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return "Total number of Records = " + count + " \nFor full detail use the link /displayMongoData";
	}
	
	@RequestMapping(value = "/{param}/averages", method = RequestMethod.GET)
	public String averages(
			@PathVariable(value = "param") String ethnicity) {
		final int equivalentEthnicity = Constants.getEthinicityCode(ethnicity);
		if ( 1 <=equivalentEthnicity && equivalentEthnicity <= 9) {
			
			MongoClient client = null;
			try {
				List<Member> memberData = new ArrayList<Member>();
				client = new MongoClient("localhost", 27017);
				DB db = client.getDB(Constants.DB_NAME);
				DBCollection collection = db.getCollection(Constants.COLL_NAME);
				DBObject ethnicityQuery = new BasicDBObject();
				ethnicityQuery.put(Constants.ETHNICITY, new BasicDBObject("$eq", equivalentEthnicity));
				
				DBCursor cursor = collection.find(ethnicityQuery);
				while(cursor.hasNext()) {
					BasicDBObject dbo = (BasicDBObject) cursor.next();
					Member member = Member.getMemberFromMongo(dbo);
					memberData.add(member);
				}
				
				/**
				 * Now get the average from the filtered data of particular ethnicity
				 */
				int denom = memberData.size();
				String result = " ";
				if (denom > 0) {
					int heightsum = memberData.stream().mapToInt(o -> o.height).sum();
					result += "Average height : " + heightsum/denom;
					
					int weightSum = memberData.stream().mapToInt(o -> o.weight).sum();
					result += "\n Average weight : " + weightSum/denom;
					
					return result;
				}
			} catch (UnknownHostException ex) {
				ex.printStackTrace();
			} finally {
				if (client != null) {
					client.close();
				}
			}
			return "No daat found matching to required ethnicity";
		} else {
			return "Invalid Ethnicity code";
		}
	}
	
	@RequestMapping(value = "/social_habits", method = RequestMethod.GET)
	public String socialHabits(
			@RequestParam(value="vegetarian") int vegetarian,
			@RequestParam(value="drink") int drink) {
		MongoClient client = null;
		try {
			
			List<Member> memberData = new ArrayList<Member>();
			client = new MongoClient("localhost", 27017);
			DB db = client.getDB(Constants.DB_NAME);
			DBCollection collection = db.getCollection(Constants.COLL_NAME);
			
			DBObject query1 = new BasicDBObject(Constants.isVEG, vegetarian == 1);  
	        DBObject query2 = new BasicDBObject(Constants.isDRINK, drink == 1);  
	        BasicDBList condtionalOperator = new BasicDBList();
	        condtionalOperator.add(query1);
	        condtionalOperator.add(query2);
	        DBObject query = new BasicDBObject("$and", condtionalOperator);  
	        DBCursor cursor = collection.find(query);
	        
			while(cursor.hasNext()) {
				BasicDBObject dbo = (BasicDBObject) cursor.next();
				Member member = Member.getMemberFromMongo(dbo);
				memberData.add(member);
			}
			Gson gson = new Gson();
			return gson.toJson(memberData);
			
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}
}
