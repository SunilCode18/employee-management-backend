package com.springboot.app.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogger {
	
	private static final Logger logger = LogManager.getLogger(MyLogger.class);
	
	public static void main(String args[]) {
		process();
	}
	
	public static void process() {
		
		logger.trace("From the trace method");
		logger.debug("From the debug method");
		logger.info("From the info method");
		logger.warn("From the warn method");
		logger.error("From the error method");
		logger.fatal("From the fatal method");
		
	}

}
