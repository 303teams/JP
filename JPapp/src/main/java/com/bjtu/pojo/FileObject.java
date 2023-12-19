package com.bjtu.pojo;

import lombok.Data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Data
public class FileObject {

    private Map<String,Object> map;

    public FileObject(){
        this.map = new HashMap<>();
        map.put("result",0);
        map.put("content","");
    }

    public void setFail(){
        map.put("result",1);
    }

    public void setContent(String content){
        map.put("content",content);
    }

    public String getContent(){
        return map.get("content").toString();
    }

    public Integer getResult(){
        return Integer.parseInt(map.get("result").toString());
    }



}
