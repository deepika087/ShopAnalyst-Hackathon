package com.ShopAnalyst.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.ShopAnalyst.constants.Constants;
import com.google.gson.stream.JsonReader;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class Jsonreader {
	DBCollection collection;

	public Jsonreader(DBCollection collection) {
		this.collection = collection;
	}
	
	public int loadDataInMongo() {
		
		int count = 0;
		try {
			JsonReader reader =  new JsonReader(new FileReader(Constants.JSON_FILE_LOCATION));
			reader.beginArray();
			
			while (reader.hasNext()) {
				BasicDBObject dbo = readmessage(reader);
				this.collection.insert(dbo);
				count = count + 1;
			}
			reader.endArray();
			return count;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	private BasicDBObject readmessage(JsonReader reader) throws IOException {
		BasicDBObject dbo = new BasicDBObject();
		reader.beginObject();
		
		while(reader.hasNext()) {
			String name = reader.nextName();
			if (name == "" || name.length() == 0) {
				break;
			}
			if (name.equals(Constants.ID)) {
				dbo.append(Constants.ID, new Integer(reader.nextString()));
			} else if (name.equals(Constants.DOB)) {
				dbo.append(Constants.DOB, reader.nextString());
			} else if (name.equals(Constants.CAPTION)) {
				dbo.append(Constants.CAPTION, reader.nextString());
			} else if (name.equals(Constants.ETHNICITY)) {
				String ethnicity = reader.nextString();
				if (ethnicity.length() > 1) {
					dbo.append(Constants.ETHNICITY, new Integer(-1));
				} else {
					dbo.append(Constants.ETHNICITY, new Integer(ethnicity));
				}
				
			} else if (name.equals(Constants.WEIGHT)) {
				dbo.append(Constants.WEIGHT, new Integer(reader.nextString())/Constants.GRAMS_TO_KGS);
			} else if (name.equals(Constants.HEIGHT)) {
				dbo.append(Constants.HEIGHT, new Integer(reader.nextString()));
			} else if (name.equals(Constants.isVEG)) {
				dbo.append(Constants.isVEG, new Boolean(reader.nextString()));
			} else if (name.equals(Constants.isDRINK)) {
				dbo.append(Constants.isDRINK,  new Boolean(reader.nextString()));
			}
		}
		reader.endObject();
		return dbo;
	}
}
