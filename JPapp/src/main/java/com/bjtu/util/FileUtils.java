package com.bjtu.util;

import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.FileObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.hslf.extractor.PowerPointExtractor;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileUtils {

    /**
     * 读取.txt类型文件
     * @param filePath
     * @return 文件内容
     */
    public FileObject readTextFile(String filePath) {
        FileObject fileObject = new FileObject();

        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath));
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }

            fileObject.setContent(contentBuilder.toString());
        } catch (IOException e) {
            fileObject.setFail();
            throw new RuntimeException(e.getMessage());
        }

        return fileObject;
    }

    /**
     * 读取.txt类型文件
     * @param fileBytes
     * @return 文件内容
     */
    private FileObject readTextFileByte(byte[] fileBytes) {
        FileObject fileObject = new FileObject();

        try {
            String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
            fileObject.setContent(fileContent);
        } catch (Exception e) {
            fileObject.setFail();
            throw new RuntimeException(e.getMessage());
        }

        return fileObject;
    }

    /**
     * 读取.docx类型文件
     * @param fileBytes
     * @return
     */
    private FileObject getTextFromDocx(byte[] fileBytes) {
        FileObject fileObject = new FileObject();
        try{
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            XWPFDocument doc = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            String text = extractor.getText();
            fileObject.setContent(text);
            inputStream.close();
        }catch (Exception e){
            fileObject.setFail();
            throw new RuntimeException(e.getMessage());
        }

        return fileObject;
    }


    /**
     * Read .doc file
     *
     * @param fileBytes
     * @return
     */
    private FileObject getTextFromDoc(byte[] fileBytes) {
        FileObject fileObject = new FileObject();
        StringBuilder content = new StringBuilder();
        try{
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            WordExtractor extractor = new WordExtractor(inputStream);  // 2003版本 仅doc格式文件可处理，docx文件不可处理
            String[] paragraphText = extractor.getParagraphText();   // 获取段落，段落缩进无法获取，可以在前添加空格填充
            if (paragraphText != null && paragraphText.length > 0) {
                for (String paragraph : paragraphText) {
                    if (!paragraph.startsWith("    ")) {
                        content.append(paragraph.trim()).append("\r\n");
                    } else {
                        content.append(paragraph);
                    }
                }
            }
            fileObject.setContent(content.toString());
            inputStream.close();
        }catch (Exception e){
            fileObject.setFail();
            throw new RuntimeException(e.getMessage());
        }
        return fileObject;
    }

    /**
     * Read .pdf file
     *
     * @param fileBytes
     * @return
     */
    private FileObject getTextFromPDF(byte[] fileBytes) {
        FileObject fileObject = new FileObject();
        try{
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            PDDocument pd = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pd);
            fileObject.setContent(text);
            inputStream.close();
        }catch (Exception e){
            fileObject.setFail();
            throw new RuntimeException(e.getMessage());
        }
        return fileObject;
    }

    /**
     * Read .ppt file
     *
     * @param fileBytes
     * @return
     */
    private FileObject getTextFromPPT(byte[] fileBytes) {
        FileObject fileObject = new FileObject();
        try{
            InputStream inputStream = new ByteArrayInputStream(fileBytes);
            PowerPointExtractor extractor = new PowerPointExtractor(inputStream);
            String text = extractor.getText();
            extractor.close();
            fileObject.setContent(text);
        }catch (Exception e){
            fileObject.setFail();
            throw new RuntimeException(e.getMessage());
        }
        return fileObject;
    }

    /**
     * Read .pptx file
     *
     * @param fileBytes
     * @return
     */
    private FileObject getTextFromPPTX(byte[] fileBytes) {
        FileObject fileObject = new FileObject();
        InputStream inputStream = new ByteArrayInputStream(fileBytes);
        try {
            XMLSlideShow xmlSlideShow = new XMLSlideShow(inputStream);
            List<XSLFSlide> slides = xmlSlideShow.getSlides();
            StringBuilder sb = new StringBuilder();
            for (XSLFSlide slide : slides) {
                CTSlide rawSlide = slide.getXmlObject();
                CTGroupShape gs = rawSlide.getCSld().getSpTree();
                CTShape[] shapes = gs.getSpArray();
                for(CTShape shape:shapes){
                    CTTextBody tb = shape.getTxBody();
                    if(null==tb){
                        continue;
                    }
                    CTTextParagraph[] paras = tb.getPArray();
                    for(CTTextParagraph textParagraph:paras){
                        CTRegularTextRun[] textRuns = textParagraph.getRArray();
                        for(CTRegularTextRun textRun:textRuns){
                            sb.append(textRun.getT());
                        }
                    }
                }
            }
            fileObject.setContent(sb.toString());
            xmlSlideShow.close();
        } catch (Exception e) {
            fileObject.setFail();
            throw new RuntimeException(e.getMessage());
        }
        return fileObject;
    }

/**
     * Read all file types
     *
     * @param fileBytes fileName
     * @return String
     */
    public String transToString(byte[] fileBytes,String fileName){
        String fileContent = "";
        try {
            if (fileName.endsWith(".txt")) {
                fileContent = readTextFileByte(fileBytes).getContent();
            } else if (fileName.endsWith(".doc")) {
                fileContent = getTextFromDoc(fileBytes).getContent();
            } else if (fileName.endsWith(".docx")) {
                fileContent = getTextFromDocx(fileBytes).getContent();
            } else if (fileName.endsWith(".pdf")) {
                fileContent = getTextFromPDF(fileBytes).getContent();
            } else if (fileName.endsWith(".ppt")) {
                fileContent = getTextFromPPT(fileBytes).getContent();
            } else if (fileName.endsWith(".pptx")) {
                fileContent = getTextFromPPTX(fileBytes).getContent();
            }
        }catch (Exception e){
                throw new RuntimeException(e.getMessage());
        }
        return fileContent;
    }

}
