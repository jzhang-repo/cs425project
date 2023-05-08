package com.iit.Group12.entity;

public class Payment {

    private Integer id;
    private String card_number;
    private int expiration_date;
    private String cvv;
    private String renter_id;
    private String street;
    private String city;
    private String state;
    private int zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public int getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(int expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getRenter_id() {
        return renter_id;
    }

    public void setRenter_id(String renter_id) {
        this.renter_id = renter_id;
    }

    public void printdata() {
        System.out.println("card_number:" + this.getCard_number());
        System.out.println("expiration_date:" + this.getExpiration_date());
        System.out.println("cvv:" + this.getCvv());
        System.out.println("renter_id:" + this.getRenter_id());

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
