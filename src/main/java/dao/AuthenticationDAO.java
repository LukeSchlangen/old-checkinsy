package main.java.dao;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import main.java.model.User;
import main.java.util.CryptoUtils;
import main.java.util.DAOUtils;
import main.java.util.LoggingUtils;

public class AuthenticationDAO {
	
	private static Logger logger;

	private DynamoDBMapper mapper;
	
	public AuthenticationDAO() throws IOException {
		logger = LoggingUtils.getInstance();
		mapper = DAOUtils.setupDatabase();		
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
}
