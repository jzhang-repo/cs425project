package com.iit.Group12.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iit.Group12.DbConn;
import com.iit.Group12.entity.House;

public class HouseService {

    private static HouseService _instance = new HouseService();

    private HouseService() {

    }

    public static HouseService getInstance() {
        return _instance;
    }

    public List<House> getList() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "select * from house";
            ps = conn.prepareStatement(selectSql);
            rs = ps.executeQuery();
            List<House> result = new ArrayList<>();
            while (rs.next()) {
                House house = new House();
                house.setHouse_id(rs.getString("house_id"));
                house.setNum_of_rooms(rs.getInt("num_of_rooms"));
                house.setSquare_footage(rs.getDouble("square_footage"));
                house.setProperty_id(rs.getString("property_id"));
                result.add(house);
            }
            return result;
        } finally {
            DbConn.close(conn, ps, rs);
        }

    }
}
