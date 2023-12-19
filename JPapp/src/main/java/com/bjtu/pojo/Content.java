package com.bjtu.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

@Data
@Accessors(chain = true)
public class Content implements Serializable {

    private Integer contentID;
    private String cno;
    private String sno;
    private Integer homeworkID;
    private byte[] content;
    private String fileName;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp submitTime;

    private double score;
    private Integer contentID1;
    private Integer contentID2;
    private Integer contentID3;
    private Integer contentID4;
    private Integer contentID5;
    private Integer contentID6;

    public Integer[] getContents(){
        Integer[] contents = new Integer[6];
        contents[0] = contentID1;
        contents[1] = contentID2;
        contents[2] = contentID3;
        contents[3] = contentID4;
        contents[4] = contentID5;
        contents[5] = contentID6;
        return contents;
    }

//    学生姓名
    private String sname;

    private String comment;


    public Content(){

    }

    @Override
    public String toString() {
        return "Content{" +
                "contentID=" + contentID +
                ", cno='" + cno + '\'' +
                ", sno='" + sno + '\'' +
                ", homeworkID=" + homeworkID +
                ", content=" + Arrays.toString(content) +
                ", fileName='" + fileName + '\'' +
                ", submitTime='" + submitTime + '\'' +
                ", score=" + score +
                ", contentID1=" + contentID1 +
                ", contentID2=" + contentID2 +
                ", contentID3=" + contentID3 +
                ", contentID4=" + contentID4 +
                ", contentID5=" + contentID5 +
                ", contentID6=" + contentID6 +
                ", sname='" + sname + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
