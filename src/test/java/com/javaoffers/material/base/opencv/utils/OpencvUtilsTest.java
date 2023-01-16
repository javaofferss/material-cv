package com.javaoffers.material.base.opencv.utils;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.junit.Test;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.junit.Assert.*;

public class OpencvUtilsTest {

    Mat imread = imread("img/dog.png").clone();

    @Test
    public void cornersCircles() {
        Mat apply = OpencvUtils.cutMat(imread,0, 110, 100, 50);
        //OpencvUtils.cornersCircles(apply, 0.035);
        OpencvUtils.cornersCircles(apply);
    }

    /**
     * 写字,然后再变成圆形角
     */
    @Test
    public void writeText() {
        Mat apply = OpencvUtils.cutMat(imread,0, 110, 100, 50);
        //OpencvUtils.cornersCircles(apply); 放在上面和放在下面效果是不一样,放在上面字的透明度会生效
        OpencvUtils.writeText(apply, "hello java", 13,15,1, 2);
        OpencvUtils.cornersCircles(apply);
        imwrite("img/testCornerRadius.png", apply);
    }

    @Test
    public void fusion() {

        Mat apply = OpencvUtils.cutMat(imread,0, 110, 100, 50);
        Mat clone = apply.clone();
        //OpencvUtils.cornersCircles(apply); 放在上面和放在下面效果是不一样,放在上面字的透明度会生效
        OpencvUtils.writeText(apply, "hello java", 13,15,1, 2);
        //OpencvUtils.cornersCircles(apply);
        //通道相同,大小一样的才可以合并
        OpencvUtils.fusion(clone, apply, clone);
        imwrite("img/fusion.png", imread);
    }

    @Test
    public void backgroundColor() {
        Mat apply = OpencvUtils.cutMat(imread,0, 110, 100, 50);
        OpencvUtils.backgroundColor(apply, "#80aa27");
        OpencvUtils.writeText(apply, "hello java", 13,15,1, 2);
        imwrite("img/backgroundColor.png", apply);
    }

    @Test
    public void opacity() {
        Mat apply = OpencvUtils.cutMat(imread,0, 110, 100, 50);
        OpencvUtils.opacity(apply, 200);
        imwrite("img/opacity.png", apply);
    }

    @Test
    public void testWriteText() {
        Mat apply = OpencvUtils.cutMat(imread,0, 110, 100, 50);
        //这些值需要慢慢的去调试观察,找到合时的值
        OpencvUtils.writeChineseText(apply, "您好",13,35,30);

        imwrite("img/testWriteText.png", apply);
    }
}