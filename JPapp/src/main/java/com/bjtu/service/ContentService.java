package com.bjtu.service;

import com.bjtu.pojo.Content;

public interface ContentService {

    Content findById(Integer id);

    void addContent(Content content);

}
