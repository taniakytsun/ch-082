package com.softserve.javaweb.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {

	private static Logger logger = Logger.getLogger(App.class.getName());
	private String url = "jdbc:postgresql://localhost/fileparsing";
	private String user = "postgres";
	private String pass = "postgres";

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pass);
			logger.log(Level.FINEST, "Connected to the PostgreSQL server successfully.");

		} catch (SQLException e) {
			logger.log(Level.WARNING, "Connection failure", e);
		}
		return conn;
	}
}
