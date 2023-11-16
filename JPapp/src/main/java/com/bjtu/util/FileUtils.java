package com.bjtu.util;

import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileUtils {
    public boolean downloadFile(byte[] data, String fileName, HttpServletResponse response){
        try {
            File tempFile = File.createTempFile(fileName, ".temp");

            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                System.out.println("1");
                fileOutputStream.write(data);
            }

            System.out.println("2");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            try (ServletOutputStream outputStream = response.getOutputStream()){
                System.out.println("3");
                java.nio.file.Files.copy(tempFile.toPath(), outputStream);
                outputStream.flush();
            }

            tempFile.delete();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
