package com.javaoffers.material.base.opencv;

import java.io.File;

/**
 * 信息资源
 * @author mingJie
 */
public abstract class MetaSource {

    private String srcFilePath;

    private String desFilePath;

    public String getSrcFilePath() {
        return srcFilePath;
    }

    public void setSrcFilePath(String srcFilePath) {
        this.srcFilePath = srcFilePath;
    }

    public String getDesFilePath() {
        return desFilePath;
    }

    public void setDesFilePath(String desFilePath) {
        this.desFilePath = desFilePath;
    }

    public MetaSource(String srcFilePath, String desFilePath) {
        this.srcFilePath = srcFilePath;
        this.desFilePath = desFilePath;
    }
}
