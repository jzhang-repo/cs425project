package com.iit.Group12.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iit.Group12.DbConn;
import com.iit.Group12.entity.CommercialBuilding;

public class CommercialBuildingService {

    private static CommercialBuildingService _instance = new CommercialBuildingService();

    private CommercialBuildingService() {

    }

    public static CommercialBuildingService getInstance() {
        return _instance;
    }

    public List<CommercialBuilding> getList() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "select * from commercial_building";
            ps = conn.prepareStatement(selectSql);
            rs = ps.executeQuery();
            List<CommercialBuilding> result = new ArrayList<>();
            while (rs.next()) {
                CommercialBuilding item = new CommercialBuilding();
                item.setCommercial_building_id(rs.getString("commercial_building_id"));
                item.setSquare_footage(rs.getDouble("square_footage"));
                item.setType_of_business(rs.getString("type_of_business"));
                item.setProperty_id(rs.getString("property_id"));
                result.add(item);
            }
            return result;
        } finally {
            DbConn.close(conn, ps, rs);
        }

    }
}
