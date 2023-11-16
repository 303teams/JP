package com.bjtu.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Content {

    private String contntID;
    private String cno;
    private String sno;
    private String homeworkID;
    private byte[] content;

    public Content(){

    }
}
