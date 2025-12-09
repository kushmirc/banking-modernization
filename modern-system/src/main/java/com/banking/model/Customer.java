package com.banking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "actno")
    private String accountNumber;

    @Column(name = "fname", unique = false, nullable = false)
    private String firstName;

    @Column(name = "lname", unique = false, nullable = false)
    private String lastName;

    @Column(name = "dob", unique = false, nullable = true)
    private String dateOfBirth;

    @Column(name = "userid", unique = true, nullable = false)
    private String userId;

    @Column(name = "pword", unique = false, nullable = false)
    private String password;

    @Column(unique = false, nullable = true)
    private String gender;

    @Column(unique = false, nullable = false)
    private double balance;

    @Column(name = "addressline1", unique = false, nullable = true)
    private String addressLine1;

    @Column(name = "addressline2", unique = false, nullable = true)
    private String addressLine2;

    @Column(unique = false, nullable = true)
    private String city;

    @Column(unique = false, nullable = true)
    private String state;

    @Column(unique = false, nullable = true)
    private int zip;

    public Customer() {
    }

    public Customer(String actno, String fname, String lname, String dob, String userid, String pword, String gender, double balance, String addressline1, String addressline2, String city, String state, int zip) {
        this.accountNumber = actno;
        this.firstName = fname;
        this.lastName = lname;
        this.dateOfBirth = dob;
        this.userId = userid;
        this.password = pword;
        this.gender = gender;
        this.balance = balance;
        this.addressLine1 = addressline1;
        this.addressLine2 = addressline2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String actno) {
        this.accountNumber = actno;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dob) {
        this.dateOfBirth = dob;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userid) {
        this.userId = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pword) {
        this.password = pword;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressline1) {
        this.addressLine1 = addressline1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressline2) {
        this.addressLine2 = addressline2;
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
