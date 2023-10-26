package com.bjtu.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("student")
public class Student extends User {
    private String sno;
    private String sname;
    private String spassword;
    private String ssex;
    private String sage;
    private String semail;

    public Student() {}

    public Student(String sno, String sname, String spassword, String ssex, String sage, String semail) {
        super(sno, sname, spassword);
        this.sno = sno;
        this.sname = sname;
        this.spassword = spassword;
        this.ssex = ssex;
        this.sage = sage;
        this.semail = semail;
    }

    public String getSno() {
        return sno;
    }

    public String getSname() {
        return sname;
    }

    public String getSpassword() {
        return spassword;
    }

    public String getSsex() {
        return ssex;
    }

    public String getSage() {
        return sage;
    }

    public String getSemail() {
        return semail;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex;
    }

    public void setSage(String sage) {
        this.sage = sage;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }
}


