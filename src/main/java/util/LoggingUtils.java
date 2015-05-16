package main.java.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingUtils {
	
	private static final String LOG_NAME = "checkinsy.log";
	
	private static Logger instance;
	private static FileHandler fileHandler;
	
	
	public static Logger getInstance() throws IOException {
		if(instance == null) {
			setupLogger();
		}
		
		return instance;
	}
	
	private static void setupLogger() throws IOException {
		instance = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		instance.setLevel(Level.INFO);
		fileHandler = new FileHandler(LOG_NAME);
		instance.addHandler(fileHandler);
	}

}
