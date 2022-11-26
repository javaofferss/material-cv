package com.javaoffers.tess4f.ocr.sample;

import com.javaoffers.tess4j.ocr.os.TesseractOS;
import com.javaoffers.tess4j.ocr.tess4j.TesseractOCR;
import com.javaoffers.tess4j.ocr.utils.OCRUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Tess4jOCRSample {

    public static void main(String[] args) throws Exception {
//      String userdir = System.getProperty("user.dir");
//      File tempFile = new File("d:", "temp.png");
        TesseractOCR.language = "t1+merge1";
        TesseractOCR.tesseractOS = TesseractOS.WIN;
        File storeFile = new File("C:\\Users\\cmj\\IdeaProjects\\mh-doc\\note-doc\\doc\\框架笔记\\tesseract\\img\\pdf2875809419433.png");
        //storeFile = new File("/home/cmj/桌面/mst/aa/1645028560694.png");
        BufferedImage tempImg = ImageIO.read(storeFile);
//            TestOCR.wirtePath = "/home/cmj/nohup/pdf";
        String character = OCRUtils.recognizeCharacterBrighten(tempImg,16);
        System.out.println(character );

    }

    public  void recognizeCharacterAndMagnify( ) throws Exception{
        for(int i=0; i<100; i++){
            File storeFile = new File("/home/cmj/桌面/bbbbb_2.png");
            TesseractOCR.language = "chi_sim";
            BufferedImage tempImg = ImageIO.read(storeFile);
            String character = OCRUtils.recognizeCharacterAndMagnify(tempImg,i+2,i+1);
            System.out.println(character +"  :  "+i);
        }
    }
}
