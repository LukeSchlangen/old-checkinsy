package main.java.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Member")
public class Member {
	
	@DynamoDBHashKey(attributeName="Id")
	private String id;
	@DynamoDBAttribute(attributeName="Name")
	private String name;
	@DynamoDBAttribute(attributeName="Group")
	private String group;
	
	public Member() {}
	
	public Member(String id, String name, String group) {
		this.id = id;
		this.name = name;
		this.group = group;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
}
