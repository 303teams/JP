package com.bjtu.service;

import com.bjtu.pojo.Content;
import com.bjtu.pojo.RspObject;
import org.springframework.web.multipart.MultipartFile;

public interface ContentService {

    Content findById(Integer id);

    void addContent(Content content);

    Content findCTByHIDSno(Integer homeworkID,String sno);

    RspObject<Object> alterContent(Content content);
}
