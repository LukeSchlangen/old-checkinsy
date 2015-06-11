package main.java.util;

import java.util.UUID;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class DAOUtils {
	
	private static final String ACCESS_KEY = "AKIAJ56S3WETIHC2WQSA";
	private static final String SECRET_KEY = "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";		
	private static final String END_POINT = "dynamodb.us-west-2.amazonaws.com";
	
	
	public static DynamoDBMapper setupDatabase() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(credentials);
		dynamoDB.setEndpoint(END_POINT);
		
		return new DynamoDBMapper(dynamoDB);
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

}
