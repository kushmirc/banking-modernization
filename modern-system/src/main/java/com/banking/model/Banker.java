package com.banking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "banker")
public class Banker {

    @Id
    @Column(name = "userid")
    private String userId;

    @Column(name = "fname", unique = false, nullable = false)
    private String firstName;

    @Column(name = "lname", unique = false, nullable = false)
    private String lastName;

    @Column(name = "dob", unique = false, nullable = true)
    private String dateOfBirth;

    @Column(name = "pword", unique = false, nullable = false)
    private String password;

    @Column(unique = false, nullable = true)
    private String gender;

    public Banker() {
    }

    public Banker(String userid, String fname, String lname, String dob, String pword, String gender) {
        this.userId = userid;
        this.firstName = fname;
        this.lastName = lname;
        this.dateOfBirth = dob;
        this.password = pword;
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userid) {
        this.userId = userid;
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
}
