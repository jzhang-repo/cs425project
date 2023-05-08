package com.iit.Group12.service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iit.Group12.DbConn;
import com.iit.Group12.entity.Apartment;
import com.iit.Group12.entity.CommercialBuilding;
import com.iit.Group12.entity.House;
import com.iit.Group12.entity.Property;


public class PropertyService {

    public static final int property_type_house = 1;
    public static final int property_type_apartment = 2;
    public static final int property_type_commercial_building = 3;
    public static final int property_type_vacation_home = 4;

    private static PropertyService _instance = new PropertyService();

    private PropertyService() {

    }

    public static PropertyService getInstance() {
        return _instance;
    }

    public List<Property> getProperties(int propertyType) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String sql = "select * from property where property_type = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, String.valueOf(propertyType));
            rs = ps.executeQuery();
            List<Property> result = new ArrayList<>();
            while (rs.next()) {
                Property property = new Property();
                property.setProperty_id(rs.getString("property_id"));
                property.setProperty_type(rs.getString("property_type"));
                property.setPrice(rs.getDouble("price"));
                property.setEstate_agency(rs.getString("estate_agency"));
                property.setDescription(rs.getString("description"));
                property.setAvailability(rs.getString("availability"));
                property.setAgent_id(rs.getString("agent_id"));
                result.add(property);
            }
            return result;
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

    public void insertHouse(House house) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            insertProperty(house);
            // insert house table
            conn = DbConn.getConn();
            String insertSql = "INSERT INTO house(house_id, num_of_rooms, square_footage, property_id)VALUES(?,?,?,?)";
            ps = conn.prepareStatement(insertSql);
            int index = 1;
            ps.setString(index++, house.getHouse_id());
            ps.setInt(index++, house.getNum_of_rooms());
            ps.setDouble(index++, house.getSquare_footage());
            ps.setString(index++, house.getProperty_id());
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    public void insertApartment(Apartment apartment) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            insertProperty(apartment);
            // insert apartment table
            conn = DbConn.getConn();
            String insertSql = "INSERT INTO apartment(apartment_id, num_of_rooms, square_footage, building_type, property_id) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(insertSql);
            int index = 1;
            ps.setString(index++, apartment.getApartment_id());
            ps.setInt(index++, apartment.getNum_of_rooms());
            ps.setDouble(index++, apartment.getSquare_footage());
            ps.setString(index++, apartment.getBuilding_type());
            ps.setString(index++, apartment.getProperty_id());
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    public void insertCommercialBuilding(CommercialBuilding commercialBuilding) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            insertProperty(commercialBuilding);
            // insert commercial building table
            conn = DbConn.getConn();
            String insertSql = "INSERT INTO commercial_building (commercial_building_id, square_footage, type_of_business, property_id) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(insertSql);
            int index = 1;
            ps.setString(index++, commercialBuilding.getCommercial_building_id());
            ps.setDouble(index++, commercialBuilding.getSquare_footage());
            ps.setString(index++, commercialBuilding.getType_of_business());
            ps.setString(index++, commercialBuilding.getProperty_id());
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, null);
        }
    }


    // delete property according to pid
    public void delProperty(String property_id, String agentId) throws SQLException {
        System.out.println("del... id: " + property_id + ", agentId: " + agentId);
        Property properties = getPropertiesById(property_id);
        // check if properties exist
        if (properties == null || !properties.getAgent_id().equals(agentId)) {
            System.out.println("There is no such property in the database");
            return;
        }
        // check if has booking
        if (getCountByProperty(property_id) != 0) {
            System.out.println("The property is booked, cannot be deleted");
            return;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = null;
            switch (Integer.valueOf(properties.getProperty_type())) {
            case property_type_house:
                sql = "delete from house where property_id = ?";
                break;
            case property_type_apartment:
                sql = "delete from apartment where property_id = ?";
                break;
            case property_type_commercial_building:
                sql = "delete from commercial_building where property_id = ?";
                break;
            case property_type_vacation_home:
                sql = "delete from vacation_home where property_id = ?";
                break;
            default:
                System.out.println("unknow property type.");
                return;
            }
            conn = DbConn.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, property_id);
            ps.executeUpdate();
            deleteProperty(property_id);
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

    public void findPropertyBySelf(String propertyId, String agentId) throws SQLException {
        Property properties = findPropertyById(propertyId);
        if (properties == null || agentId.equals(properties.getAgent_id())) {
            System.out.println("The property " + propertyId + " does not exist");
        }
        System.out.println("The property info: ");
        System.out.println("property_id: " + properties.getProperty_id());
        System.out.println("property_type: " + properties.getProperty_type());
        System.out.println("price: " + properties.getPrice());
        System.out.println("estate_agency: " + properties.getEstate_agency());
        System.out.println("description: " + properties.getDescription());
        System.out.println("availability: " + properties.getAvailability());
    }

    public Property findPropertyById(String propertyId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "SELECT * FROM property WHERE property_id = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setString(1, propertyId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Property property = new Property();
                property.setProperty_id(rs.getString("property_id"));
                property.setProperty_type(rs.getString("property_type"));
                property.setPrice(rs.getDouble("price"));
                property.setEstate_agency(rs.getString("estate_agency"));
                property.setDescription(rs.getString("description"));
                property.setAvailability(rs.getString("availability")); 
                property.setProperty_id(rs.getString("property_id"));
                property.setAgent_id(rs.getString("agent_id"));
                return property;
            } else {
                System.out.println("not foud properties. " + propertyId);
                return null;
            }
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    public Property getPropertiesById(String propertyId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "SELECT * FROM property WHERE property_id = ? ";
            ps = conn.prepareStatement(selectSql);
            int index = 1;
            ps.setString(index++, propertyId);
            rs = ps.executeQuery();
            if (!rs.next())
                return null;
            Property properties = new Property();
            properties.setProperty_id(rs.getString("property_id"));
            properties.setProperty_type(rs.getString("property_type"));
            properties.setPrice(rs.getDouble("price"));
            properties.setEstate_agency(rs.getString("estate_agency"));
            properties.setDescription(rs.getString("description"));
            properties.setAvailability(rs.getString("availability"));
            properties.setAgent_id(rs.getString("agent_id"));
            return properties;
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    private void insertProperty(Property properties) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConn.getConn();
            String insertSql = "INSERT INTO property(property_id, agent_id, property_type, price, estate_agency, description, availability)VALUES(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(insertSql);
            int index = 1;
            ps.setString(index++, properties.getProperty_id());
            ps.setString(index++, properties.getAgent_id());
            ps.setString(index++, properties.getProperty_type());
            ps.setDouble(index++, properties.getPrice());
            ps.setString(index++, properties.getEstate_agency());
            ps.setString(index++, properties.getDescription());
            ps.setString(index++, properties.getAvailability());
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, null);
        }
    }

    private void deleteProperty(String propertyId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "delete from property where property_id = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setString(1, propertyId);
            ps.executeUpdate();
        } finally {
            DbConn.close(conn, ps, rs);
        }
    }

    private int getCountByProperty(String propertyId) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getConn();
            String selectSql = "select count(1) from booking where property_id = ?";
            ps = conn.prepareStatement(selectSql);
            ps.setString(1, propertyId);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } finally {
            DbConn.close(conn, ps, rs);
        }

    }
}
