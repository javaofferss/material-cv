package com.javaoffers.material.base.opencv.metasource;

import com.javaoffers.material.base.opencv.MetaSource;

/**
 * @author mingJie
 */
public class StarMetaSource extends MetaSource {

    private int starCount;

    public StarMetaSource(String srcFilePath, String desFilePath, int starCount) {
        super(srcFilePath, desFilePath);
        this.starCount = starCount;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }
}
