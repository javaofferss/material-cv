package src.test.java.com.javaoffers.material.sample;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.indexer.IntIndexer;
import org.bytedeco.javacpp.indexer.UByteIndexer;


import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_imgproc.*;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

/**
 * @description: opencv mat
 * @author: create by cmj on 2022/11/26 18:29
 */
public class MatSample {

    static Logger logger = LoggerFactory.getLogger("MatSample");

    /**
     * 加载图像
     */
    @Test
    public void tesImread() throws Exception{
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = imread("img/dog.png");
        logger.info("witdh:{}, height:{}, type: {}", mat.arrayWidth(), mat.arrayHeight(), mat.type());

        Mat mat2 = imread("img/dog.png", IMREAD_GRAYSCALE);
        boolean imwrite = imwrite("img/dogGrayscale.png", mat2);
        logger.info("imwrite success");
        // 第一个range是高 （_end - _start）， 第二个range是宽 （_end - _start ）
        // _start: 从高/宽 的开始位置，  _end: 到 宽/高 的结束位置
        Mat submat = mat2.apply(new Range(150, 200), new Range(200, 400));
        logger.info("submat :{}", submat);
        boolean imwrite1 = imwrite("img/submatDog.png", submat);

        //先指定 x,y 的一个坐标位置在图中，然后指定宽和高,这个比较常用
        Mat rect = mat2.apply(new Rect(150, 150, 200, 200));
        boolean imwrite2 = imwrite("img/rectDog.png", rect);

        //把rect设置为模糊, 第一个rect为输入，第二个rect为输出， Size为像素大小
        blur(rect,rect, new Size(25,25));
        //注意子mat像素被修改后也会影响父mat中对应子mat的区域
        imwrite("img/blurDog.png", mat2);

        //CV_8UC3 可以理解 每个像素点在内存空间所占的空间大小8bite。使用3通道
        // r g b . 可以利用RGB去创建 scalar,
        Scalar scalar = RGB(255,   0,   0);
        Mat newMat = new Mat(200, 300, CV_8UC3, RGB(0, 0, 0));
        Mat subMat = newMat.apply(new Rect(0, 0, 150, 200));
        Mat scalarMat = new Mat(200, 100, CV_8UC3, scalar);
        //这里用setTo会出现异常
        scalarMat.copyTo(subMat);
        logger.info(newMat.toString());
        imwrite("img/setTo.jpg", newMat);

        //将dog填充到两个矩阵中
        Mat dogLeft = mat.clone();
        Mat leftMat = newMat.apply(new Rect(0, 0, 150, 200));
        //重置大小
        resize(dogLeft,dogLeft, leftMat.size());
        Mat dogRight = mat.clone();
        Mat rightMat = newMat.apply(new Rect(150, 0, 150, 200));
        //重置大小
        resize(dogRight, dogRight, rightMat.size());
        dogLeft.copyTo(leftMat);
        dogRight.copyTo(rightMat);
        imwrite("img/leftRightDog.jpg", newMat);



    }





}
