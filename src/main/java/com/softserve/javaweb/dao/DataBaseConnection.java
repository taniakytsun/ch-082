package com.softserve.javaweb.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnection {

	private static Logger logger = Logger.getLogger(DataBaseConnection.class.getName());
	private static DataBaseConnection instance;
	private Connection connection;
	private String url = "jdbc:postgresql://localhost:5432/fileparsing";
	private String username = "postgres";
	private String password = "postgres";

	private DataBaseConnection()  {
		try {
			Class.forName("org.postgresql.Driver");
			this.connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException ex) {
			logger.log(Level.ALL,"Database Connection Creation Failed : " + ex.getMessage());
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public static DataBaseConnection getInstance()  {
		if (instance == null) {
			instance = new DataBaseConnection();
		} else {
			try {
				if (instance.getConnection().isClosed()) {
					instance = new DataBaseConnection();
				}
			} catch (SQLException e) {
				logger.log(Level.ALL, e.getMessage());
			}
		}

		return instance;
	}
}
