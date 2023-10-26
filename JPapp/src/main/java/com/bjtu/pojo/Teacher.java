package com.bjtu.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("teacher")
public class Teacher extends User {
    private String tno;
    private String tname;
    private String tpassword;
    private String tsex;
    private String tage;
    private String temail;

    public Teacher() {}

    public Teacher(String tno, String tname, String tpassword, String tsex, String tage, String temail) {
        super(tno, tname, tpassword);
        this.tno = tno;
        this.tname = tname;
        this.tpassword = tpassword;
        this.tsex = tsex;
        this.tage = tage;
        this.temail = temail;
    }

    public String getTno() {
        return tno;
    }

    public String getTname() {
        return tname;
    }

    public String getTpassword() {
        return tpassword;
    }

    public String getTsex() {
        return tsex;
    }

    public String getTage() {
        return tage;
    }

    public String getTemail() {
        return temail;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public void setTpassword(String tpassword) {
        this.tpassword = tpassword;
    }

    public void setTsex(String tsex) {
        this.tsex = tsex;
    }

    public void setTage(String tage) {
        this.tage = tage;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }
}
