package com.javaoffers.material.base.opencv.executor;

import com.javaoffers.material.base.opencv.Ability;
import com.javaoffers.material.base.opencv.AbilityExecutor;
import com.javaoffers.material.base.opencv.MetaSource;
import com.javaoffers.material.base.opencv.metasource.BarrageMetaSource;
import com.javaoffers.material.base.opencv.utils.OpencvUtils;
import com.javaoffers.material.base.utils.Assert;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.FONT_HERSHEY_SIMPLEX;
import static org.bytedeco.opencv.global.opencv_imgproc.LINE_8;
import static org.bytedeco.opencv.global.opencv_imgproc.putText;

/**
 * @author mingJie
 */
public class BarrageAbilityExecutor implements AbilityExecutor<BarrageMetaSource> {

    @Override
    public Ability getAbility() {
        return Ability.BARRAGE;
    }

    @Override
    public void executor(BarrageMetaSource source) {
        String srcFilePath = source.getSrcFilePath();
        Assert.isTrue(srcFilePath != null, "src file path is null");
        Mat clone = imread(srcFilePath).clone();
        int height = clone.arrayHeight();
        int width = clone.arrayWidth();

        String text = source.getText();
        int paddingLeft = 10;
        //左下方弹框
        int textWidth = getWidth(text) + paddingLeft * 2;
        Assert.isTrue(textWidth <= width, "too much text");
        Mat cutMat = OpencvUtils.cutMat(clone, 10, height - 100, textWidth, 40);

        OpencvUtils.backgroundColor(cutMat, "#4c3c2c");
        OpencvUtils.writeChineseText(cutMat, text, paddingLeft,31,29);

        OpencvUtils.writeImg(source.getDesFilePath(), clone);
    }

    public int getWidth(String text){
        String[] split = text.split("");
        int wd = 0;
        for(String str : split){
            byte[] bytes = str.getBytes();
            if(bytes.length>2){
                wd = wd + 29;//Chinese according to 19
            }else{
                wd = wd + 15;//English in accordance with 15
            }
        }
        return wd;
    }
}
