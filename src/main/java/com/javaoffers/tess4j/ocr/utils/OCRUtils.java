package com.javaoffers.tess4j.ocr.utils;

import com.javaoffers.tess4j.ocr.tess4j.TesseractOCR;
import com.javaoffers.tess4j.ocr.vietocr.ImageHelper;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static com.javaoffers.tess4j.ocr.utils.ImageUtils.processImg;

public class OCRUtils {

    public static String wirtePath = null;

    public static String recognizeCharacterAndMagnify(BufferedImage bufferedImage, int magnify) throws IOException, TesseractException {
        // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
        BufferedImage bi = ImageHelper.convertImageToGrayscale(bufferedImage);
        // 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
        bi = ImageHelper.convertImageToBinary(bi);
        // 图片放大5倍,增强识别率(很多图片本身无法识别,放大15倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
        bi = ImageHelper.getScaledInstance(bi, bi.getWidth() * magnify, bi.getHeight() * magnify);
        Tesseract instance = TesseractOCR.getInstance();
        return instance.doOCR(bi);
    }

    public static String recognizeCharacterAndMagnify(BufferedImage bufferedImage, int magnifyWidth, int magnifyHeight) throws IOException, TesseractException {
        // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
        BufferedImage bi = ImageHelper.convertImageToGrayscale(bufferedImage);
        // 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
        bi = ImageHelper.convertImageToBinary(bi);
        // 图片放大5倍,增强识别率(很多图片本身无法识别,放大15倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
        bi = ImageHelper.getScaledInstance(bi, bi.getWidth() * magnifyWidth, bi.getHeight() * magnifyHeight);
        Tesseract instance = TesseractOCR.getInstance();
        return instance.doOCR(bi);
    }

    public static String recognizeCharacterAndMagnify(BufferedImage bufferedImage, int magnify,String saveImagePath) throws IOException, TesseractException {
        // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
        BufferedImage bi = ImageHelper.convertImageToGrayscale(bufferedImage);
        // 图片锐化,自己使用中影响识别率的主要因素是针式打印机字迹不连贯,所以锐化反而降低识别率
        bi = ImageHelper.convertImageToBinary(bi);
        // 图片放大5倍,增强识别率(很多图片本身无法识别,放大15倍时就可以轻易识,但是考滤到客户电脑配置低,针式打印机打印不连贯的问题,这里就放大5倍)
        bi = ImageHelper.getScaledInstance(bi, bi.getWidth() * magnify, bi.getHeight() * magnify);
        if (saveImagePath != null) {
            boolean write = ImageIO.write(bi, "png", new File(saveImagePath + System.nanoTime()));
        }
        Tesseract instance = TesseractOCR.getInstance();
        return instance.doOCR(bi);
    }

    public static String recognizeCharacterBrighten(BufferedImage bufferedImage, int brighten) throws IOException, TesseractException {
        // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
        BufferedImage bi = ImageHelper.convertImageToGrayscale(bufferedImage);
        // 图片亮度
        processImg(bi, brighten);
        Tesseract instance = TesseractOCR.getInstance();
        return instance.doOCR(bi);
    }

    public static String recognizeCharacterBrighten(BufferedImage bufferedImage, int brighten, String saveImagePath) throws IOException, TesseractException {
        // 这里对图片黑白处理,增强识别率.这里先通过截图,截取图片中需要识别的部分
        BufferedImage bi = ImageHelper.convertImageToGrayscale(bufferedImage);
        // 图片亮度
        processImg(bi, brighten);
        if (saveImagePath != null) {
            boolean write = ImageIO.write(bi, "png", new File(saveImagePath + System.nanoTime()));
        }
        Tesseract instance = TesseractOCR.getInstance();
        return instance.doOCR(bi);
    }


}
