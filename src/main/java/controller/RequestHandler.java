package main.java.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;

import main.java.dao.AuthenticationDAO;
import main.java.dao.MemberDAO;
import main.java.util.LoggingUtils;

@WebServlet("/handler")
public class RequestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger;
	
	private int counter = 0;
	
	private AuthenticationDAO authenticationDAO;
	
	private MemberDAO memberDAO;

    public RequestHandler() throws IOException {
    	authenticationDAO = new AuthenticationDAO();
    	memberDAO = new MemberDAO();
    	logger = LoggingUtils.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject json = parseJson(request);
			String requestType = json.getString("type");
			logger.info("Received request of type " + requestType);
			
			switch(requestType) {
				case "TEST":
					test(json, response);
					break;
				case "CREATE_ACCOUNT":
					createAccount(json, response);
					break;
				case "CREATE_MEMBER":
					createMember(json, response);
					break;
				case "GET_USERS":
					getUsers(json, response);
					break;
			}

		} catch (Exception e) {
			logger.severe(e.toString());
		} finally {
			/*
			 * CLEAN UP CODE HERE
			 */
		}			
	}
	
	private void test(JSONObject json, HttpServletResponse response) throws Exception {
		setResponse(response, "TEST_SUCCESS_" + counter++);
	}
	
	private void createAccount(JSONObject json, HttpServletResponse response) throws Exception {
		String username = json.getString("username");
		String password = json.getString("password");
		
		if(authenticationDAO.getUser(username) == null) {
			logger.info(String.format("Creating user [%s]", username));
			authenticationDAO.addUser(username, password);
			setResponse(response, "CREATE_SUCCESS");
		} else {
			logger.info(String.format("User [%s] exists in the system", username));
			setResponse(response, "USER_EXISTS");
		}
	}
	
	private void createMember(JSONObject json, HttpServletResponse response) throws Exception {
		String name = json.getString("name");
		String group = json.getString("group");
		memberDAO.createMember(name, group);
		setResponse(response, "CREATE_SUCCESS");
	}
	
	private void getUsers(JSONObject json, HttpServletResponse response) throws Exception {
		String group = json.getString("group");		
		JSONObject returnJson = new JSONObject();
		JSONArray members = memberDAO.getUsersForGroup(group);
		returnJson.put("group", group);
		returnJson.put("members", members);
		setResponse(response, returnJson.toString());		
	}
	
	private void setResponse(HttpServletResponse response, String responseValue) throws Exception {
		JSONObject json = new JSONObject();
		json.put("response", responseValue);
		response.setStatus(200);
		response.setContentType("json");
		response.getWriter().write(json.toString());
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
