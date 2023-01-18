package com.javaoffers.material.base.opencv.utils;

import com.javaoffers.material.base.utils.Assert;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.tuple.Pair;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Pointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;

import static com.javaoffers.material.base.opencv.utils.Utils.parseRGB;
import static org.bytedeco.opencv.global.opencv_core.addWeighted;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2BGRA;
import static org.bytedeco.opencv.global.opencv_imgproc.FONT_HERSHEY_SIMPLEX;
import static org.bytedeco.opencv.global.opencv_imgproc.LINE_8;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.putText;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;
import org.bytedeco.opencv.opencv_videoio.VideoWriter;

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
    //fillMat 用于填充圆角
    public static void cornersCircles(Mat src, double radiusMultiple, int opacity, Mat fillMat) {
        //Getheight
        int height = src.arrayHeight();
        int width = src.arrayWidth();
        if(opacity >= 0){
            cvtColor(src, src, COLOR_BGR2BGRA);//转成4通道,可以设置透明度
        }

        Mat left = src.apply(new Rect(0, 0, height, height));
        Mat leftFill = null;
        if(fillMat != null){
            leftFill = fillMat.apply(new Rect(0, 0, height, height));
        }
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
                    if(leftFill != null){
                        BytePointer ptrFill = leftFill.ptr(i, j);
                        ptr.put(0, (byte) ptrFill.get(0));
                        ptr.put(1, (byte) ptrFill.get(1));
                        ptr.put(2, (byte) ptrFill.get(2));
                    }else{
                        ptr.put(0, (byte) 255);
                        ptr.put(1, (byte) 255);
                        ptr.put(2, (byte) 255);
                    }

                    if(opacity >= 0){
                        ptr.put(3, (byte) 100);
                    }
                }
            }
        }

        Mat right = src.apply(new Rect(width - height - 1, 0, height, height)); //这里多减去一个1效果会更好一点

        Mat rightFill = null;
        if(fillMat != null){
            rightFill = fillMat.apply(new Rect(width - height - 1, 0, height, height)); //这里多减去一个1效果会更好一点
        }

        int rightWidth = right.arrayWidth();
        int rw2 = rightWidth / 2;
        for (int i = 0; i < y; i++) {
            for (int j = rightWidth; j > rw2; j--) {
                //勾股定理计算到圆中心的距离, 这里的40是height的一半(也就是半径)
                double sqrt = (Math.sqrt(Math.pow((center - j), 2) + Math.pow((center - i), 2)));
                if (sqrt >= radius) {
                    BytePointer ptr = right.ptr(i, j);
                    if(rightFill != null){
                        BytePointer ptrFill = rightFill.ptr(i, j);
                        ptr.put(0, (byte) ptrFill.get(0));
                        ptr.put(1, (byte) ptrFill.get(1));
                        ptr.put(2, (byte) ptrFill.get(2));
                    }else{
                        ptr.put(0, (byte) 255);
                        ptr.put(1, (byte) 255);
                        ptr.put(2, (byte) 255);
                    }

                    if(opacity >= 0){
                        ptr.put(3, (byte) 100);
                    }
                }
            }
        }

    }

    //两边变圆
    public static void cornersCircles(Mat src) {
        cornersCircles(src, 0, -1, null);
    }

    //两边变圆
    public static void cornersCircles(Mat src, Mat fillMat) {
        cornersCircles(src, 0, -1, fillMat);
    }

    //两边变圆, 指明透明度,src 写出时要指定只此透明度的格式,比如png. (jgp不支持透明度)
    public static void cornersCircles(Mat src, int opacity) {
        cornersCircles(src, 0, opacity, null);
    }

    //两边变圆, 指明透明度,src 写出时要指定只此透明度的格式,比如png. (jgp不支持透明度)
    public static void cornersCircles(Mat src, int opacity, Mat fillMat) {
        cornersCircles(src, 0, opacity, fillMat);
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
        List<Double> rgb = parseRGB(backgroundColor);
        Scalar scalar = RGB(rgb.get(0), rgb.get(1), rgb.get(2));
        src.put(scalar);
    }

    //支持 #fbfcfc 这种格式
    public static void backgroundColor(Mat src, String backgroundColor, int decrease) {
        List<Double> rgb = parseRGB(backgroundColor);
        Scalar scalar = RGB(rgb.get(0) - decrease, rgb.get(1) - decrease, rgb.get(2) - decrease);
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

    //写中文汉字
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

    //写视频
    public static void writeVideoFromJpg(String desPath, List<Mat> mats, int fps){
        int fourcc = VideoWriter.fourcc("M".getBytes()[0], "J".getBytes()[0], "P".getBytes()[0], "G".getBytes()[0]);
        VideoWriter videoWriter = new VideoWriter(desPath, fourcc, fps, mats.get(0).size());
        for(Mat mat : mats){
            videoWriter.write(mat);
        }
        videoWriter.release();
    }

    //高斯模糊, range( 表示内核大小的Size对象。) , sigma 可以理解为权重, count. 高斯模糊处理几次
    public static void gaussianBlur(Mat src, int range, double sigma, int count){
        for(int i = 0 ; i< count; i++){
            GaussianBlur(src,src, new Size(range,range), sigma, sigma,1);
        }
    }

    /**
     * 画小星星
     * @param mat 原mar
     * @param xMat 坐标位置x
     * @param yMat 坐标位置y
     * @param drawSize 星星的大小
     * @param angleSize 角的大小
     * @param scalar 星星的颜色
     * @param gusiCount 星星的模糊度
     * @param restoreSimilarity  星星边缘模糊度. 越小越模糊, 越大边缘越清晰
     * @return matStar
     */
    public static Pair<Mat,Mat> drawStar(Mat mat, int xMat, int yMat, int drawSize, int angleSize, Scalar scalar, int gusiCount, int restoreSimilarity){
        Mat clone = mat;
        Mat cut = clone.apply(new Rect(xMat, yMat, drawSize, drawSize)); //  The size of the entire star
        Mat fillMat = cut.clone();
        //cut.put(Scalar.BLACK);
        int wd = angleSize;// Can control the size of the four corners
        int radius = wd / 2 ; //Radius

        int centerX = cut.arrayWidth() / 2;
        int centerY = cut.arrayHeight() / 2;

        Point leftPoint1 = new Point(centerX - radius, centerY - radius); //左上角
        Point leftPoint2 = new Point(centerX - radius, centerY + radius); //左下角
        Point rightPoint1 = new Point(centerX + radius, centerY - radius); //右上角
        Point rightPoint2 = new Point(centerX + radius, centerY + radius); //右下角

        //边上四个中点
        Point upPoint = new Point(centerX, 0);
        Point downPoint = new Point(centerX, cut.arrayHeight());
        Point leftPoint = new Point(0, centerY);
        Point rightPoint = new Point(cut.arrayWidth(), centerY);

        //绘制四个角
        line(cut, upPoint, leftPoint1, scalar);
        for(int i=0; i< radius * 2; i++){
            Point leftPoint1Go = new Point(centerX - radius + i, centerY - radius); //不断接近左上角
            line(cut, upPoint, leftPoint1Go,scalar );
        }
        line(cut, upPoint, rightPoint1, scalar );


        line(cut, downPoint, leftPoint2, scalar );
        for(int i=0; i< radius * 2; i++){
            Point leftPoint2GO = new Point(centerX - radius + i, centerY + radius); //不断接近右下角
            line(cut, downPoint, leftPoint2GO, scalar );
        }
        line(cut, downPoint, rightPoint2, scalar );


        line(cut, leftPoint, leftPoint1, scalar);
        for(int i=0; i< radius * 2; i++){
            Point leftPoint1Go = new Point(centerX - radius, centerY - radius + i); //不断接近左下角
            line(cut, leftPoint, leftPoint1Go, scalar );
        }
        line(cut, leftPoint, leftPoint2, scalar );


        line(cut, rightPoint, rightPoint1, scalar );
        for(int i=0; i< radius * 2; i++){
            Point rightPoint1Go = new Point(centerX + radius, centerY - radius + i); //不断接近右下角
            line(cut, rightPoint, rightPoint1Go, scalar );
        }
        line(cut, rightPoint, rightPoint2, scalar);

        wd = (int)Math.floor(wd * 1.1); //Make the circle bigger.
        radius = wd / 2 ; //radius, recalculate radius
        int pointX = centerX  - radius;
        int pointY = centerY  - radius;
        Mat radioCut = cut.apply(new Rect(pointX, pointY, wd, wd));
        int x = radioCut.cols();
        int y = radioCut.rows();
        int radio = (int) Math.floor(radioCut.arrayWidth() / 2.0);
        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                BytePointer ptr = radioCut.ptr(i, j);
                double distance = Math.sqrt(Math.pow((radio - i),2) + Math.pow((radio - j),2));
                if(distance < (radio)){
                    ptr.put(0, (byte)scalar.get(0));
                    ptr.put(1, (byte)scalar.get(1));
                    ptr.put(2, (byte)scalar.get(2));
                }
            }
        }
        //The number of times and the more blurred
        if(gusiCount > 0){
            OpencvUtils.gaussianBlur(cut, 3, 1, gusiCount); // count determines the degree of fuzziness
        }
        //Restore the part. The surroundings of the stars are blurred,
        // but not all of them. Note: You can try a circle cut here, and the effect may be better.
        mergeSimilar(restoreSimilarity, cut, fillMat);
        return Pair.of(cut,fillMat);
    }

    public static Mat readImg(String srcFilePath) {
        return imread(srcFilePath);
    }

    public static void cover2RGBA(Mat imread) {
        cvtColor(imread, imread, COLOR_BGR2BGRA);;
    }

    public static void mergeSimilar(int restoreSimilarity, Mat srcMat, Mat fillMat) {
        mergeSimilar( restoreSimilarity,  srcMat,  fillMat, null);
    }


    public static void mergeSimilar(int restoreSimilarity, Mat srcMat, Mat fillMat, Scalar ignoreColor) {
        Assert.isTrue(srcMat.arrayHeight() == fillMat.arrayHeight() && srcMat.arrayWidth() == fillMat.arrayWidth(),
                "Width and height must be the same");
        int fillX = fillMat.arrayWidth();
        int fillY = fillMat.arrayHeight();
        for(int i = 0; i < fillY; i++){
            for(int j = 0; j < fillX; j++){
                BytePointer cutPtr = srcMat.ptr(i, j);
                BytePointer fillPtr = fillMat.ptr(i, j);
                byte r = cutPtr.get(0);
                byte g = cutPtr.get(1);
                byte b = cutPtr.get(2);

                byte fr = fillPtr.get(0);
                byte fg = fillPtr.get(1);
                byte fb = fillPtr.get(2);
                if(ignoreColor != null){

                    byte ir = (byte) ignoreColor.get(0);
                    byte ig = (byte) ignoreColor.get(1);
                    byte ib = (byte) ignoreColor.get(2);
                    if(ir == fr && ig ==fg && ib == fb){
                        continue;
                    }
                }

                boolean br = Math.abs(fr - r) < restoreSimilarity;
                boolean bg = Math.abs(fg - g) < restoreSimilarity;
                boolean bb = Math.abs(fb - b) < restoreSimilarity;

                br = br && bg && bb; // Similar partial restoration

                if( !br){
                    continue;
                }

                cutPtr.put(0, fr);
                cutPtr.put(1, fg);
                cutPtr.put(2, fb);
            }
        }
    }
}
