package main.java.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.dao.AuthenticationDAO;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Test log");
		authenticationDAO.addUser("Tom", "Password");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	//Test for Adit!
}
