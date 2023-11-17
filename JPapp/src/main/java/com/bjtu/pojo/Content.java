package com.bjtu.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Content {

    private int contentID;
    private String cno;
    private String cname;
    private String sno;
    private int homeworkID;
    private byte[] content;

    public Content(){

    }
}
