package main.java.dao;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import main.java.model.User;
import main.java.util.CryptoUtils;
import main.java.util.LoggingUtils;

public class AuthenticationDAO {
	
	private static Logger logger;
	
	private static final String ACCESS_KEY = "AKIAJ56S3WETIHC2WQSA";
	private static final String SECRET_KEY = "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";	
	
	private static final String END_POINT = "dynamodb.us-west-2.amazonaws.com";

	private DynamoDBMapper mapper;
	
	public AuthenticationDAO() throws IOException {
		logger = LoggingUtils.getInstance();
		setupDatabase();		
	}
	
	public void addUser(String username, String password) throws Exception {
		String encryptedPassword = CryptoUtils.createHash(password);
		User user = new User(username, encryptedPassword);
		mapper.save(user);
		
		logger.info(String.format("Added user [%s] to the Authentication table", username));
	}
	
	public void deleteUser(String username) {
		User user = new User(username);
		mapper.delete(user);
		
		logger.info(String.format("Deleted user [%s] from the Authentication table", username));
	}
	
	public User getUser(String username) {
		User user = new User(username);
		DynamoDBQueryExpression<User> query = new DynamoDBQueryExpression<User>().withHashKeyValues(user);
		List<User> result = mapper.query(User.class, query);
		
		if(result.isEmpty()) {
			logger.info(String.format("Failed to find user [%s]", username));
			return null;
		} else {
			logger.info(String.format("Found [%d] users for username [%s]. Returning only one.", result.size(), username));
			return result.get(0);
		}
	}
	
	private void setupDatabase() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(credentials);
		dynamoDB.setEndpoint(END_POINT);
		
		mapper = new DynamoDBMapper(dynamoDB);
	}
}
