package com.iit.Group12.entity;

public class CommercialBuilding extends Property {
    private String commercial_building_id;
    private Double square_footage;
    private String type_of_business;

    public String getCommercial_building_id() {
        return commercial_building_id;
    }

    public void setCommercial_building_id(String commercial_building_id) {
        this.commercial_building_id = commercial_building_id;
    }

    public Double getSquare_footage() {
        return square_footage;
    }

    public void setSquare_footage(Double square_footage) {
        this.square_footage = square_footage;
    }

    public String getType_of_business() {
        return type_of_business;
    }

    public void setType_of_business(String type_of_business) {
        this.type_of_business = type_of_business;
    }

    public void printdata() {
        System.out.println("commercial_building_id:" + this.getCommercial_building_id());
        System.out.println("square_footage:" + this.getSquare_footage());
        System.out.println("type_of_business:" + this.getType_of_business());
        System.out.println("property_id:" + this.getProperty_id());
    }
}
