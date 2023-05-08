package com.iit.Group12;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConn {

	private static JDBCConfig config;

	static {
		String url = "jdbc:postgresql://localhost:5432/cs425db";
		String username = "postgres";
		String password = "1010";
		config = new JDBCConfig(url, username, password);
	}

	/*
	 * get database connection
	 */
	public static Connection getConn() throws SQLException {
		return DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
	}

	/**
	 * close conn
	 * 
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (ps != null)
				ps.close();
		} catch (SQLException e) {
			System.out.println("ps close error.");
		}
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			System.out.println("rs close error.");
		}
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			System.out.println("conn close error.");
		}
	}

}
