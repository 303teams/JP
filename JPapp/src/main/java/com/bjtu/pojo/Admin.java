package com.bjtu.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    private String mno;
    private String mpassword;
    private String memail;

    public Admin() {}

    public Admin(String mno, String mpassword, String memail) {
        super(mno, mpassword);
        this.mno = mno;
        this.mpassword = mpassword;
        this.memail = memail;
    }

    public String getMno() {
        return mno;
    }

    public String getMpassword() {
        return mpassword;
    }

    public String getMemail() {
        return memail;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public void setMpassword(String mpassword) {
        this.mpassword = mpassword;
    }

    public void setMemail(String memail) {
        this.memail = memail;
    }
}
