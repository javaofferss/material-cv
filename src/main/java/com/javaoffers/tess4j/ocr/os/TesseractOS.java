package com.javaoffers.tess4j.ocr.os;

/**
 * 系统平台
 * create by cmj
 */
public enum TesseractOS {
    WIN(0,"C:\\Program Files (x86)\\Tesseract-OCR\\tessdata"),
    UBUNTU(1,"/usr/share/tesseract-ocr/4.00/tessdata")
    ;

    int code;
    String path;
    TesseractOS(int c,String path){
        this.code = c;
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
