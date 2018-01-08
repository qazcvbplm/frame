package sunwou.entity;


import com.fasterxml.jackson.annotation.JsonInclude;

import sunwou.mongo.util.MongoBaseEntity;


public class Test extends MongoBaseEntity{

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
