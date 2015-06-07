package main.java.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import main.java.dao.AuthenticationDAO;
import main.java.model.User;
import main.java.util.CryptoUtils;
import main.java.util.LoggingUtils;

@WebServlet("/handler")
public class RequestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger;
	
	private int counter = 0;
	
	private AuthenticationDAO authenticationDAO;

    public RequestHandler() throws IOException {
    	authenticationDAO = new AuthenticationDAO();
    	logger = LoggingUtils.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		
		String testUser = "Tom";
		try {
			authenticationDAO.addUser(testUser, "Password");
			User user = authenticationDAO.getUser(testUser);
			logger.info(String.format("Fetched user [%s] with encyrpted password [%s]", user.getUsername(), user.getPassword()));
			logger.info(String.format("Valid password decryption? [%s]", CryptoUtils.validatePassword("Password", user.getPassword())));
			authenticationDAO.deleteUser("Tom");
			
		} catch (Exception e) {
			logger.severe("Oops...something went wrong!");
			e.printStackTrace();
		} finally {
			logger.info("Success?!");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject json = parseJson(request);
			logger.info("Logging param1 passed from the client");
			logger.info("param1 => " + json.getString("param1"));
			
			/*
			 *DO SOME SERVER SIDE STUFF HERE
			 */
			
			json = new JSONObject();
			logger.info("Now passing it back to the client with some random response");
			json.put("count", counter++);
			response.setStatus(200);
			response.setContentType("json");
			response.getWriter().write(json.toString());
		} catch (JSONException e) {
			logger.info("oops?");
		} finally {
			/*
			 * CLEAN UP CODE HERE
			 */
		}			
	}
	
	private JSONObject parseJson(HttpServletRequest request) throws IOException, JSONException {
		BufferedReader jsonReader = request.getReader();
		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		
		while ((line = jsonReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		
		return new JSONObject(stringBuffer.toString());
	}
}
