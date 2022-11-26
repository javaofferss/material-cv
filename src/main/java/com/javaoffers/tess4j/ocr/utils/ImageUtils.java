package com.javaoffers.tess4j.ocr.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * create by cmj
 */
public class ImageUtils {

    /**
     * 设置图片的亮度
     * @param img
     * @param changeValue
     */
    public static void processImg(BufferedImage img, int changeValue) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {

                // 获取到rgb的组合值

                int rgb = img.getRGB(x, y);

                Color color = new Color(rgb);

                int r = color.getRed() + changeValue;

                int g = color.getGreen() + changeValue;

                int b = color.getBlue() + changeValue;

                if (r > 255) {

                    r = 255;

                } else if (r < 0) {

                    r = 0;

                }

                if (g > 255) {

                    g = 255;

                } else if (g < 0) {

                    g = 0;

                }

                if (b > 255) {

                    b = 255;

                } else if (b < 0) {

                    b = 0;

                }

                color = new Color(r, g, b);

                img.setRGB(x, y, color.getRGB());

            }
        }
    }

}
