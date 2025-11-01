package com.banking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "banker")
public class Banker {

    @Id
    private String userid;

    @Column(unique = false, nullable = false)
    private String fname;

    @Column(unique = false, nullable = false)
    private String lname;

    @Column(unique = false, nullable = true)
    private String dob;

    @Column(unique = false, nullable = false)
    private String pword;

    @Column(unique = false, nullable = true)
    private String gender;

    public Banker() {
    }

    public Banker(String userid, String fname, String lname, String dob, String pword, String gender) {
        this.userid = userid;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.pword = pword;
        this.gender = gender;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
}
