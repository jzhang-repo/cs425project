package com.iit.Group12.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iit.Group12.DbConn;
import com.iit.Group12.entity.Payment;

public class PaymentService {

    private static PaymentService _instance = new PaymentService();

    private PaymentService() {

    }

    public static PaymentService getInstance() {
        return _instance;
    }

    public List<Payment> getMyPayments(String renterId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String sql = "select card_number, expiration_date, cvv, renter_id, id, street, city, state, zip from payment where renter_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, renterId);
            rs = ps.executeQuery();
            List<Payment> result = new ArrayList<>();
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setCard_number(rs.getString("card_number"));
                payment.setExpiration_date(rs.getInt("expiration_date"));
                payment.setCvv(rs.getString("cvv"));
                payment.setRenter_id(rs.getString("renter_id"));
                payment.setStreet(rs.getString("street"));
                payment.setCity(rs.getString("city"));
                payment.setState(rs.getString("state"));
                result.add(payment);
            }
            return result;
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

    public void addPayment(Payment payment) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String sql = "insert into payment( card_number, expiration_date, cvv, renter_id, street, city, state, zip) values(?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, payment.getCard_number());
            ps.setInt(index++, payment.getExpiration_date());
            ps.setString(index++, payment.getCvv());
            ps.setString(index++, payment.getRenter_id());
            ps.setString(index++, payment.getStreet());
            ps.setString(index++, payment.getCity());
            ps.setString(index++, payment.getState());
            ps.setInt(index++, payment.getZip());
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

    public void delPayment(String renterId, String paymentId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String sql = "delete from payment where id = ? and renter_id = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, paymentId);
            ps.setString(2, renterId);
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }
}
