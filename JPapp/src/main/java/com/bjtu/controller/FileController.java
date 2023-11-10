package com.bjtu.controller;


import cn.hutool.core.io.FileUtil;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.RspObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/file")
public class FileController {
    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";

    @AuthAccess
    @PostMapping("/upload")
    public RspObject<Object> upload(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String mainName = FileUtil.mainName(originalFilename);
        String extName = FileUtil.extName(originalFilename);

        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath+"\\files"+"\\"+originalFilename;

        File saveFile = new File(filePath);
        if (!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();

        }
        if (saveFile.exists()){
            filePath = System.currentTimeMillis()+mainName+"_"+extName;
            saveFile = new File(filePath);
        }

        file.transferTo(saveFile);
        String url = "http://localhost:8081/file/download/"+originalFilename;

        return RspObject.success(url);//文件的下载地址
    }

@AuthAccess
@GetMapping("/download/{fileName}")
public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileFullName, "UTF-8"));  // 附件下载
    String filePath = ROOT_PATH  + File.separator + fileName;
    if (!FileUtil.exist(filePath)) {
        return;
    }
    byte[] bytes = FileUtil.readBytes(filePath);
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(bytes);  // 数组是一个字节数组，也就是文件的字节流数组
    outputStream.flush();
    outputStream.close();
}


}
