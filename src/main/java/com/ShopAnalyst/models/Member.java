package com.ShopAnalyst.models;

import com.ShopAnalyst.constants.Constants;
import com.mongodb.BasicDBObject;

public class Member {
	
	public int id;
	public String caption;
	public int ethnicity ;
	public int weight;
	public int height;
	public boolean is_veg;
	public boolean is_drink;
	public String dob;
	
	public String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public int getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(int ethnicity) {
		this.ethnicity = ethnicity;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isIs_veg() {
		return is_veg;
	}
	public void setIs_veg(boolean is_veg) {
		this.is_veg = is_veg;
	}
	public boolean isIs_drink() {
		return is_drink;
	}
	public void setIs_drink(boolean is_drink) {
		this.is_drink = is_drink;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public static Member getMemberFromMongo(final BasicDBObject dbo) {
		final Member member = new Member();
		member.setWeight(dbo.getInt(Constants.WEIGHT));
		member.setId(dbo.getInt(Constants.ID));
		member.setCaption(dbo.getString(Constants.CAPTION));
		member.setEthnicity(dbo.getInt(Constants.ETHNICITY));
		member.setHeight(dbo.getInt(Constants.HEIGHT));
		member.setDob(dbo.getString(Constants.DOB));
		member.setIs_veg(dbo.getBoolean(Constants.isVEG));
		member.setIs_drink(dbo.getBoolean(Constants.isDRINK));
		
		return member;
	}
}
