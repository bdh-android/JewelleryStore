package com.example.e_commerce_store.profile.ui.profile;

public class Profile {
    String name;
    String email;
    String address1;
    String address2;
    String phone;
    String zip;
    String country;
    String state;
    String city;

    public Profile() {
    }

    public Profile(String name, String email, String address1, String address2, String phone, String zip, String country, String state, String city) {
        this.name = name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.phone = phone;
        this.zip = zip;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
