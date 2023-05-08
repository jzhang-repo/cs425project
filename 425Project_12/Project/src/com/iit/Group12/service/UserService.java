package com.iit.Group12.service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iit.Group12.DbConn;
import com.iit.Group12.dto.Result;
import com.iit.Group12.entity.AllUser;

public class UserService {

	
	public Result<AllUser> login(String email, String password) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DbConn.getConn();
			String selectSql = "select * from all_user where email = ? and password = ? ";
			ps = conn.prepareStatement(selectSql);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				AllUser user = new AllUser();
				user.setUser_id(rs.getString("user_id"));
				user.setFirst_name(rs.getString("first_name"));
				user.setLast_name(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
				user.setUser_type(rs.getString("user_type"));
				return new Result<>(true, user);
			} else {
				return new Result<>(false, null);
			}
		} finally {
			DbConn.close(conn, ps, rs);
		}

	}

	public void registerAgent(String agent_id, String job_title, String estate_agency, String contact_information, String user_id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbConn.getConn();
			String insertSql = "INSERT INTO agent(agent_id,job_title,estate_agency,contact_information,user_id)VALUES(?,?,?,?,?)";
			ps = conn.prepareStatement(insertSql);
			int index = 1;
			ps.setString(index++, agent_id);
			ps.setString(index++, job_title);
			ps.setString(index++, estate_agency);
			ps.setString(index++, contact_information);
			ps.setString(index++, user_id);
			ps.executeUpdate();
		} finally {
			DbConn.close(conn, ps, null);
		}
	}

	public void registerRenter(String renter_id, int desired_move_in_date, String preferred_location, double budget, String user_id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbConn.getConn();
			String insertSql = "INSERT INTO renter(renter_id, desired_move_in_date, preferred_location, budget, user_id)VALUES(?,?,?,?,?)";
			ps = conn.prepareStatement(insertSql);
			int index = 1;
			ps.setString(index++, renter_id);
			ps.setInt(index++, desired_move_in_date);
			ps.setString(index++, preferred_location);
			ps.setDouble(index++, budget);
			ps.setString(index++, user_id);
			ps.executeUpdate();
		} finally {
			DbConn.close(conn, ps, null);
		}
	}

	public void insertAllUser(String user_id, String password, String userType, String first_name, String last_name, String email) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DbConn.getConn();
			String insertSql = "INSERT INTO all_user(user_id,password,user_type, first_name,last_name,email)VALUES(?,?,?,?,?,?)";
			ps = conn.prepareStatement(insertSql);
			int index = 1;
			ps.setString(index++, user_id);
			ps.setString(index++, password);
			ps.setString(index++, userType);
			ps.setString(index++, first_name);
			ps.setString(index++, last_name);
			ps.setString(index++, email);
			ps.executeUpdate();
		} finally {
			DbConn.close(conn, ps, null);
		}
	}

}
