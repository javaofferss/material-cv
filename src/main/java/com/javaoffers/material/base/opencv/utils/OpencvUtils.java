package com.javaoffers.material.base.opencv.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;

import static org.bytedeco.opencv.global.opencv_core.addWeighted;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2BGRA;
import static org.bytedeco.opencv.global.opencv_imgproc.FONT_HERSHEY_SIMPLEX;
import static org.bytedeco.opencv.global.opencv_imgproc.LINE_8;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.putText;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

/**
 * @author mingJie
 */
public class OpencvUtils {

    //Turn the four corners of the picture into circles
    public static void cornersCircles(Mat src, double radiusMultiple) {
        //Getheight
        int height = src.arrayHeight();
        int width = src.arrayWidth();
        cvtColor(src, src, COLOR_BGR2BGRA);//转成4通道,可以设置透明度
        Mat left = src.apply(new Rect(0, 0, height, height));
        int rows = left.rows();
        int cols = left.cols() / 2;
        int y = rows;
        int x = cols;
        int radius = (int) Math.ceil(height / 2 * (radiusMultiple + 1.0D));
        int center = height / 2;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                //勾股定理计算到圆中心的距离, 这里的40是height的一半(也就是半径)
                double sqrt = (Math.sqrt(Math.pow((center - j), 2) + Math.pow((center - i), 2)));
                if (sqrt >= radius) { //转成int效果会更好一点
                    BytePointer ptr = left.ptr(i, j);
                    ptr.put(0, (byte) 255);
                    ptr.put(1, (byte) 255);
                    ptr.put(2, (byte) 255);
                    ptr.put(3, (byte) 100);
                }
            }
        }

        Mat right = src.apply(new Rect(width - height - 1, 0, height, height)); //这里多减去一个1效果会更好一点
        int rightWidth = right.arrayWidth();
        int rw2 = rightWidth / 2;
        for (int i = 0; i < y; i++) {
            for (int j = rightWidth; j > rw2; j--) {
                //勾股定理计算到圆中心的距离, 这里的40是height的一半(也就是半径)
                double sqrt = (Math.sqrt(Math.pow((center - j), 2) + Math.pow((center - i), 2)));
                if (sqrt >= radius) {
                    BytePointer ptr = right.ptr(i, j);
                    ptr.put(0, (byte) 255);
                    ptr.put(1, (byte) 255);
                    ptr.put(2, (byte) 255);
                    ptr.put(3, (byte) 100);
                }
            }
        }

    }

    public static void cornersCircles(Mat src) {
        cornersCircles(src, 0);
    }

    //fontScale: 缩放倍数, thickness: 线条粗细
    public static void writeText(Mat src, String text, int paddingLeft, int paddingBottom, double fontScale, int thickness) {
        Point point = new Point(paddingLeft, src.arrayHeight() - paddingBottom);//距离底部几个像素
        putText(src, text, point, FONT_HERSHEY_SIMPLEX, fontScale, Scalar.WHITE, thickness, LINE_8, false);
    }

    public static Mat cutMat(Mat src, int x, int y, int width, int height) {
        Mat cutMat = src.apply(new Rect(x, y, width, height));
        return cutMat;
    }

    //融合
    public static void fusion(Mat src, double alpha, Mat src2, double beta, double gamma, Mat des) {
        addWeighted(src, alpha, src2, beta, gamma, des);
    }

    public static void fusion(Mat src, Mat src2, Mat des) {
        fusion(src, 0.95, src2, 0.2, 0.2, des);
    }

    //支持 #fbfcfc 这种格式
    public static void backgroundColor(Mat src, String backgroundColor) {
        String substring = backgroundColor.substring(1, backgroundColor.length());
        int c = substring.length() / 3;
        List<Double> rgb = new ArrayList<>(3);
        for (int i = 1; i <= 3; i++) {
            int start = (i - 1) * c;
            int end = start + c;
            double color = Integer.parseInt(substring.substring(start, end), 16);
            rgb.add(color);
        }
        Scalar scalar = RGB(rgb.get(0), rgb.get(1), rgb.get(2));
        src.put(scalar);
    }

    //指定透明度. 值越大,透明度越高
    public static void opacity(Mat src, int opacity) {
        Mat mat = new Mat();
        //转换成4通道, 第四个通道为透明通道, 下标是3
        cvtColor(src, mat, COLOR_BGR2BGRA);
        int rows = mat.rows();
        int cols = mat.cols();
        for (int i = 0; i < rows; i++) {
            for (int c = 0; c < cols; c++) {
                BytePointer ptr1 = mat.ptr(i, c);
                ptr1.put(3, (byte) opacity);
            }
        }
        mat.copyTo(src);
    }

    public static void writeImg(String des, Mat mat) {
        imwrite(des, mat);
    }

    public static boolean writeChineseText(Mat mat, String markContent, int paddingLeft, int paddingTop, int fontSize) {
        File file = new File("./temp");
        if (!file.exists()) {
            file.mkdir();
        }
        String srcPath = "./temp/" + System.nanoTime() + ".png";
        String outPath = srcPath;
        writeImg(srcPath, mat);
        ImageIcon imgIcon = new ImageIcon(srcPath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
        int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);

        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //将一副图片加载到内存中
        Graphics2D g = bimage.createGraphics(); //创建一个指定 BufferedImage 的 Graphics2D 对象

        Color mycolor = Color.white;
        g.setColor(mycolor);
        g.setBackground(Color.white);
        g.drawImage(theImg, 0, 0, null);
        g.setFont(new Font("宋体", Font.PLAIN, fontSize)); //字体、字型、字号
        g.drawString(markContent, paddingLeft, paddingTop); //画文字
        g.dispose();
        try {
            FileOutputStream out = new FileOutputStream(outPath);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(100, true);
            encoder.encode(bimage, param);
            out.close();
            imread(outPath).copyTo(mat);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
