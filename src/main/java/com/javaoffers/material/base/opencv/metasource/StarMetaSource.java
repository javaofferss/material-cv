package com.javaoffers.material.base.opencv.metasource;

import com.javaoffers.material.base.opencv.MetaSource;

/**
 * @author mingJie
 */
public class StarMetaSource extends MetaSource {

    private int starCount; //接近这个数字

    private int startRange; //星星大小的范围.

    //private double flickerFactor;// 闪动因子. 0 < 1

    public StarMetaSource(String srcFilePath, String desFilePath, int starCount, int startRange) {
        super(srcFilePath, desFilePath);
        this.starCount = starCount;
        this.startRange = startRange;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public int getStartRange() {
        return startRange;
    }

    public void setStartRange(int startRange) {
        this.startRange = startRange;
    }
}
