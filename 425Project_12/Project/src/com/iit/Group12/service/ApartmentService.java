package com.iit.Group12.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iit.Group12.DbConn;
import com.iit.Group12.entity.Apartment;

public class ApartmentService {

    private static ApartmentService _instance = new ApartmentService();

    private ApartmentService() {

    }

    public static ApartmentService getInstance() {
        return _instance;
    }

    public List<Apartment> getList() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "select * from apartment";
            ps = conn.prepareStatement(selectSql);
            rs = ps.executeQuery();
            List<Apartment> result = new ArrayList<>();
            while (rs.next()) {
                Apartment item = new Apartment();
                item.setApartment_id(rs.getString("apartment_id"));
                item.setNum_of_rooms(rs.getInt("num_of_rooms"));
                item.setSquare_footage(rs.getDouble("square_footage"));
                item.setBuilding_type(rs.getString("building_type"));
                item.setProperty_id(rs.getString("property_id"));
                result.add(item);
            }
            return result;
        } finally {
            DbConn.close(conn, ps, rs);
        }

    }
}
