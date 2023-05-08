package com.iit.Group12.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iit.Group12.DbConn;
import com.iit.Group12.entity.Booking;

public class BookingService {

    private static BookingService _instance = new BookingService();

    private BookingService() {

    }

    public static BookingService getInstance() {
        return _instance;
    }

    public List<Booking> findBooking(String agentId, String renterId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            StringBuffer sql = new StringBuffer("select booking_id, renter_id, card_number, start_time, end_time, property_id, agent_id, state from booking where 1 = 1 ");
            
            // agent ID and renter ID cannot be null to be able to locate the booking info
            if (agentId != null) {
                sql.append(" and agent_id = ? ");
            }
            if (renterId != null) {
                sql.append(" and renter_id = ? ");
            }
            ps = conn.prepareStatement(sql.toString());
            int index = 1;
            if (agentId != null) {
                ps.setString(index++, agentId);
            }
            if (renterId != null) {
                ps.setString(index++, renterId);
            }
            rs = ps.executeQuery();
            List<Booking> result = new ArrayList<>();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setBooking_id(rs.getString("booking_id"));
                booking.setAgent_id(rs.getString("agent_id"));
                booking.setRenter_id(rs.getString("renter_id"));
                booking.setCard_number(rs.getString("card_number"));
                booking.setStart_time(rs.getInt("start_time"));
                booking.setEnd_time(rs.getInt("end_time"));
                booking.setProperty_id(rs.getString("property_id"));
                booking.setState(rs.getInt("state"));
                result.add(booking);
            }
            return result;
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

   // only select agent ID to update the booking, and the booking ID cannot be null 
    public void updateState(String bookingId, String agentId, int state) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConn.getConn();
            String updateSql = "UPDATE booking SET state=? WHERE booking_id = ? AND  agent_id=?";
            ps = conn.prepareStatement(updateSql);
            int index = 1;
            ps.setInt(index++, state);
            ps.setString(index++, bookingId);
            ps.setString(index++, agentId);
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    // only select agent ID to delete a booking
    public void delBooking(String bookingId, String agentId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String sql = "delete from booking where booking_id = ? and agent_id = ?";
            ps = conn.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, bookingId);
            ps.setString(index++, agentId);
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

    public String getBookingStateText(int bookingState) {
        switch (bookingState) {
        case 0:
            return "waiting";
        case 1:
            return "approved";
        case 2:
            return "refused";
        case 3:
            return "cancelled";
        case 4:
            return "done";
        default:
            return "unknow";
        }
    }

}
