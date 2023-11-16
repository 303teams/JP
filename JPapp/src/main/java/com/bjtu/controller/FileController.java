package com.bjtu.controller;


import cn.hutool.core.io.FileUtil;
import com.bjtu.config.AuthAccess;
import com.bjtu.pojo.Homework;
import com.bjtu.pojo.RspObject;
import com.bjtu.pojo.User;
import com.bjtu.service.HomeworkService;
import com.bjtu.service.impl.HomeworkServiceImpl;
import com.bjtu.util.FileUtils;
import com.bjtu.util.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


@RestController
@RequestMapping("/file")
public class FileController {
    private static final String ROOT_PATH =  System.getProperty("user.dir") + File.separator + "files";


//    @AuthAccess
//    @PostMapping("/up")
//    public RspObject<Object> up(File file) throws IOException{
//
//        //connection=connectionManager.getconn();//连接数据库的操作,这里自己连接自己的数据库
//
//        try {
//            //File file=new File("D:\\1.jpg");//要转换的文件
//            FileInputStream inputStream=new FileInputStream(file);content
//            String sql="insert into save_image(images) values(?)";//存入数据库的SQL语句在执行的时候一定要用prepareStatement
//            statement=connection.prepareStatement(sql);
//            statement.setBinaryStream(1, inputStream,(int)file.length());
//            statement.executeUpdate();
//
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
    @Resource
    FileUtils fileUtils;
    @Resource
    HomeworkService homeworkService;

    @AuthAccess
    @GetMapping("/tHomework/{thId}")
    public  RspObject<Object> downloadThWithThId(@PathVariable String thId, HttpServletResponse response){
        Homework homework = homeworkService.findHomeworkByThId(thId);
        if(fileUtils.downloadFile(homework.getContent(), homework.getName(), response))
            return RspObject.success("成功下载", homework);
        else return RspObject.fail("下载失败", homework);
    }

    @AuthAccess
    @PostMapping("/upload")
    public RspObject<Object> uploadThWithThIdCno(MultipartFile file, int Id,String name) throws IOException {
        Homework homework = new Homework();
        User user = TokenUtils.getCurrentUser();
        homework.setContent(file.getBytes())
                .setHomeworkID(Id)
                .setTno(user.getId())
                .setName(name);
        homeworkService.addHomework(homework);
        return RspObject.success("上传成功，当前thId：" + Id , homework);

//        String originalFilename = file.getOriginalFilename();
//        String mainName = FileUtil.mainName(originalFilename);
//        String extName = FileUtil.extName(originalFilename);
//
//        String projectPath = System.getProperty("user.dir");
//        String filePath = projectPath+"\\files"+"\\"+originalFilename;
//
//        File saveFile = new File(filePath);
//        if (!saveFile.getParentFile().exists()){
//            saveFile.getParentFile().mkdirs();
//
//        }
//        if (saveFile.exists()){
//            filePath = System.currentTimeMillis()+mainName+"_"+extName;
//            saveFile = new File(filePath);
//        }
//
//        file.transferTo(saveFile);
//        String url = "http://localhost:8081/file/download/"+originalFilename;
//
//        return RspObject.success(url);//文件的下载地址
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
