package com.iit.Group12.entity;

public class Apartment extends Property {
    private String apartment_id;
    private int num_of_rooms;
    private Double square_footage;
    private String building_type;

    public String getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(String apartment_id) {
        this.apartment_id = apartment_id;
    }

    public int getNum_of_rooms() {
        return num_of_rooms;
    }

    public void setNum_of_rooms(int num_of_rooms) {
        this.num_of_rooms = num_of_rooms;
    }

    public Double getSquare_footage() {
        return square_footage;
    }

    public void setSquare_footage(Double square_footage) {
        this.square_footage = square_footage;
    }

    public String getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(String building_type) {
        this.building_type = building_type;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public void printdata() {
        System.out.println("apartment_id:" + this.getApartment_id());
        System.out.println("num_of_rooms:" + this.getNum_of_rooms());
        System.out.println("square_footage:" + this.getSquare_footage());
        System.out.println("building_type:" + this.getBuilding_type());
        System.out.println("property_id:" + this.getProperty_id());
    }

}
