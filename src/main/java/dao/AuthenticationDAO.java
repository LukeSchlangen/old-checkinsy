package main.java.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import main.java.util.LoggingUtils;

public class AuthenticationDAO {
	
	private static Logger logger;
	
	private static final String ACCESS_KEY = "AKIAJ56S3WETIHC2WQSA";
	private static final String SECRET_KEY = "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";	
	
	private static final String END_POINT = "dynamodb.us-west-2.amazonaws.com";
	private static final String TABLE_NAME = "Authentication";
	private static final String HASH_KEY = "Username";
	
	private AmazonDynamoDB database;	
	
	public AuthenticationDAO() throws IOException {
		logger = LoggingUtils.getInstance();
		database = setupDatabase();		
	}
	
	public void addUser(String username, String password) {
		
		Map<String, AttributeValue> key = new HashMap<>();
		key.put(HASH_KEY, new AttributeValue().withS(username));
		PutItemRequest putRequest = new PutItemRequest()
			.withTableName(TABLE_NAME)
			.withItem(key);
		database.putItem(putRequest);
		
		logger.info(String.format("Added user [%s] to the Authentication table", username));
	}
	
	private AmazonDynamoDB setupDatabase() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(credentials);
		dynamoDB.setEndpoint(END_POINT);
		
		return dynamoDB;
	}
}
