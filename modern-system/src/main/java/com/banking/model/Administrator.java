package com.banking.model;

import jakarta.persistence.*;

@Entity
@Table(name= "administrator")
public class Administrator {

    @Id
    private String userid;

    @Column(unique = false, nullable = false)
    private String fname;

    @Column(unique = false, nullable = false)
    private String lname;

    @Column(unique = false, nullable = false)
    private String pword;

    public Administrator() {
    }

    public Administrator(String userid, String fname, String lname, String pword) {
        this.userid = userid;
        this.fname = fname;
        this.lname = lname;
        this.pword = pword;
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

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }
}
