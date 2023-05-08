package com.iit.Group12.entity;

public class Booking {

    private String booking_id;
    private String agent_id;
    private String renter_id;
    private String card_number;
    private int start_time;
    private int end_time;
    private String property_id;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getRenter_id() {
        return renter_id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public void setRenter_id(String renter_id) {
        this.renter_id = renter_id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public Booking() {

    }

    public void printdata() {
        System.out.println("booking_id:" + this.getBooking_id());
        System.out.println("renter_id:" + this.getRenter_id());
        System.out.println("card_number:" + this.getCard_number());
        System.out.println("start_time:" + this.getStart_time());
        System.out.println("end_time:" + this.getEnd_time());
        System.out.println("property_id:" + this.getProperty_id());
    }

}
