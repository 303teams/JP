package com.bjtu.service;

import com.bjtu.pojo.Content;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;

import java.util.List;

public interface ContentService {

    Content findById(String id);

    void addContent(Content content);

}
