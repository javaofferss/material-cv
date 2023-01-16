package com.javaoffers.material.base.opencv.metasource;

import com.javaoffers.material.base.opencv.MetaSource;

/**
 * @author mingJie
 */
public class BarrageMetaSource extends MetaSource {

    private String text;

    public BarrageMetaSource(String srcFilePath, String desFilePath, String text) {
        super(srcFilePath, desFilePath);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
