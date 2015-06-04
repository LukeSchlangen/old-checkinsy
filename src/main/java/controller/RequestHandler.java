package main.java.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
	
	private AuthenticationDAO authenticationDAO;

    public RequestHandler() throws IOException {
    	authenticationDAO = new AuthenticationDAO();
    	logger = LoggingUtils.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		
		String testUser = "Tom";
		JSONObject json = new JSONObject();
		try {
			json.put("key", "value");
			response.setStatus(200);
			response.setContentType("json");
			response.getWriter().write(json.toString());
		} catch (JSONException e) {
			logger.info("oops?");
		}
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
		
		
		
		
	}
}
