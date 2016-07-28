package info.aenterprise.recipeTree.util;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Copyright (c) 2016, AEnterprise
 * http://www.aenterprise.info/
 */
public class Log {
	private static final Logger logger = LogManager.getLogger("recipetree");

	public static void info(String message) {
		logger.log(Level.INFO, message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void error(String message, Throwable t) {
		logger.error(message, t);
	}
}
