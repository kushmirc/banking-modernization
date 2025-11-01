package com.banking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private String actno;

    @Column(unique = false, nullable = false)
    private String fname;

    @Column(unique = false, nullable = false)
    private String lname;

    @Column(unique = false, nullable = true)
    private String dob;

    @Column(unique = true, nullable = false)
    private String userid;

    @Column(unique = false, nullable = false)
    private String pword;

    @Column(unique = false, nullable = true)
    private String gender;

    @Column(unique = false, nullable = false)
    private double balance;

    @Column(unique = false, nullable = true)
    private String addressline1;

    @Column(unique = false, nullable = true)
    private String addressline2;

    @Column(unique = false, nullable = true)
    private String city;

    @Column(unique = false, nullable = true)
    private String state;

    @Column(unique = false, nullable = true)
    private int zip;

    public Customer() {
    }

    public Customer(String actno, String fname, String lname, String dob, String userid, String pword, String gender, double balance, String addressline1, String addressline2, String city, String state, int zip) {
        this.actno = actno;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.userid = userid;
        this.pword = pword;
        this.gender = gender;
        this.balance = balance;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
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
}
