package com.javaoffers.material.base.opencv.metasource;

import com.javaoffers.material.base.opencv.MetaSource;

import java.util.List;

/**
 * @author mingJie
 */
public class BarrageMetaSource extends MetaSource {

    private List<String> text;

    public BarrageMetaSource(String srcFilePath, String desFilePath, List<String> text) {
        super(srcFilePath, desFilePath);
        this.text = text;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}
