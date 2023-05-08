package com.iit.Group12.service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.iit.Group12.DbConn;
import com.iit.Group12.entity.AllUser;
import com.iit.Group12.entity.Booking;

public class AgentService {

    public static final int user_type_agent = 1;
    public static final int user_type_renter = 2;

    private static AgentService _instance = new AgentService();

    private AgentService() {

    }

    public static AgentService getInstance() {
        return _instance;
    }

    public String getAgentId(String userId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "select * from agent where user_id = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String agent_id = rs.getString("agent_id");
                System.out.println("get agent id " + agent_id);
                return agent_id;
            } else {
                System.out.println("not found agent_id: " + userId);
                return null;
            }
        } finally {
           DbConn.close(conn, ps, rs);
        }

    }

    public String getRenterId(String userId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "select * from renter where user_id = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String renter_id = rs.getString("renter_id");
                System.out.println("get renter id " + renter_id);
                return renter_id;
            } else {
                System.out.println("not found renter_id: " + userId);
                return null;
            }
        } finally {
            DbConn.close(conn, ps, rs);
        }

    }

    public void delBooking(String booking_id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String delSql = "select * from booking where booking_id = ?";
            ps = conn.prepareStatement(delSql);
            ps.setString(1, booking_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                String system = rs.getString("booking_id");
                if (!system.equals(booking_id)) {
                    System.out.println("del property error. booking_id id not match");
                } else {
                    ps.close();
                    String delsql = "delete from booking where booking_id = ?";
                    ps = conn.prepareStatement(delsql);
                    int index = 1;
                    ps.setString(index++, booking_id);
                    ps.executeUpdate();
                }
            } else {
                System.out.println("del property error. not found record: " + booking_id);
            }
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

    public void updateBooking(String booking_id, String renter_id, String card_number, int start_time, int end_time, String property_id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConn.getConn();
            String insertSql = "UPDATE booking SET renter_id=? , card_number=? , start_time=?, end_time=?, property_id=? WHERE booking_id = ?";
            ps = conn.prepareStatement(insertSql);
            int index = 1;
            ps.setString(index++, renter_id);
            ps.setString(index++, card_number);
            ps.setInt(index++, end_time);
            ps.setString(index++, property_id);
            ps.setString(index++, booking_id);
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    public void insertBooking(Booking booking) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConn.getConn();
            String insertSql = "INSERT INTO booking(booking_id, renter_id, agent_id, card_number, start_time, end_time, property_id)VALUES(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insertSql);
            int index = 1;
            ps.setString(index++, booking.getBooking_id());
            ps.setString(index++, booking.getRenter_id());
            ps.setString(index++, booking.getAgent_id());
            ps.setString(index++, booking.getCard_number());
            ps.setInt(index++, booking.getStart_time());
            ps.setInt(index++, booking.getEnd_time());
            ps.setString(index++, booking.getProperty_id());

            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    public void selectBooking(String booking_id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "SELECT * FROM booking WHERE booking_id = ? ";
            ps = conn.prepareStatement(selectSql);
            int index = 1;
            ps.setString(index++, booking_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Booking booker = new Booking();
                booker.setBooking_id(rs.getString("booking_id"));
                booker.setRenter_id(rs.getString("renter_id"));
                booker.setCard_number(rs.getString("card_number"));
                booker.setStart_time(rs.getInt("start_time"));
                booker.setEnd_time(rs.getInt("end_time"));
                booker.setProperty_id(rs.getString("property_id"));
                booker.printdata();

            } else {
                System.out.println("no data found");
            }

        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    public void selectAllUser(String user_id) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "SELECT * FROM all_user WHERE user_id = ? ";
            ps = conn.prepareStatement(selectSql);
            int index = 1;
            ps.setString(index++, user_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                AllUser printUser = new AllUser();
                printUser.setUser_id(rs.getString("user_id"));
                printUser.setUser_type(rs.getString("user_type"));
                printUser.setFirst_name(rs.getString("first_name"));
                printUser.setLast_name(rs.getString("last_name"));
                printUser.setEmail(rs.getString("email"));
                printUser.printdata();

            } else {
                System.out.println("no data found");
            }

        } finally {
            DbConn.close(conn, ps, null);
        }
    }

}
