package com.iit.Group12.entity;

public class House extends Property {
    private String house_id;
    private int num_of_rooms;
    private Double square_footage;

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
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

    public void printdata() {
        System.out.println("house_id:" + this.getHouse_id());
        System.out.println("property_id:" + this.getProperty_id());
        System.out.println("num_of_rooms:" + this.getNum_of_rooms());
        System.out.println("square_footage:" + this.getSquare_footage());
    }
}
