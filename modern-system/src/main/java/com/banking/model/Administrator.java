package com.banking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @Column(name = "userid")
    private String userId;

    @Column(name = "fname", unique = false, nullable = false)
    private String firstName;

    @Column(name = "lname", unique = false, nullable = false)
    private String lastName;

    @Column(name = "pword", unique = false, nullable = false)
    private String password;

    public Administrator() {
    }

    public Administrator(String userid, String fname, String lname, String pword) {
        this.userId = userid;
        this.firstName = fname;
        this.lastName = lname;
        this.password = pword;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String pword) {
        this.password = pword;
    }
}
