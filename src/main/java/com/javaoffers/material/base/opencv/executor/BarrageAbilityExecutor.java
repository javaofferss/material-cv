package com.javaoffers.material.base.opencv.executor;

import com.javaoffers.material.base.opencv.Ability;
import com.javaoffers.material.base.opencv.AbilityExecutor;
import com.javaoffers.material.base.opencv.MetaSource;
import com.javaoffers.material.base.opencv.metasource.BarrageMetaSource;
import com.javaoffers.material.base.opencv.utils.OpencvUtils;
import com.javaoffers.material.base.opencv.utils.Utils;
import com.javaoffers.material.base.utils.Assert;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;

import java.util.LinkedList;
import java.util.List;

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
        List<Mat> mats = new LinkedList<>();
        int fps = 80;
        int lastH = 0;
        for (int ti = 0; ti < source.getText().size(); ti++) {
            String text = source.getText().get(ti);
            int paddingLeft = 10;
            //左下方弹框
            int textWidth = Utils.getWidth(text) + paddingLeft * 2;
            Assert.isTrue(textWidth <= width, "too much text");

            for (int i = lastH; i < 120; i++) {
                Mat nMat = null;

                nMat = parseMat(clone, height, text, paddingLeft, i,"#4c3c2c");
                //When it reaches 80, the next text appears. (Two texts appear at the same time)
                int showSecond = 80;
                if (i > showSecond && ti < source.getText().size() - 1) {
                    if(i > 100){
                        int t = 20 -(120 -i);
                        double d = t * 0.02; // 0.02 = 0.4 / 20. (Because the last 20 mats of 20 need to achieve alpha: 0.4, 0.6)
                        OpencvUtils.fusion(nMat, 0.5 - d, clone, 0.5 + d, 0, nMat);
                    }
                    int h = i - showSecond; // the height of the second occurrence
                    Mat nMat2 = parseMat(clone, height, source.getText().get(ti + 1), paddingLeft, h,"#4c3c2c");
                    OpencvUtils.fusion(nMat, 0.5, nMat2, 0.5, 0, nMat);
                    lastH = h;

                } else {
                    //Transparency can be changed with fusion
                    OpencvUtils.fusion(nMat, 0.5, clone, 0.5, 0, nMat);
                }
                mats.add(nMat);
            }
        }
        //write video
        if (mats.size() > 0) {
            OpencvUtils.writeVideoFromJpg(source.getDesFilePath(), mats, fps);
        }
    }

    private Mat parseMat(Mat clone, int height, String text, int paddingLeft, int i ,String backgroundColor,  int decrease ) {
        int textWidth = Utils.getWidth(text) + paddingLeft * 2;
        textWidth = textWidth > clone.arrayWidth() ? clone.arrayWidth() : textWidth;
        Mat src = clone.clone();
        int y = height - 50 - i;
        y = y < 0 ? 0 : y;

        Mat cutMat = OpencvUtils.cutMat(src, 10, y, textWidth, 40);
        Mat cloneFill = cutMat.clone();
        OpencvUtils.backgroundColor(cutMat, backgroundColor, decrease);
        OpencvUtils.writeChineseText(cutMat, text, paddingLeft, 31, 29);
        OpencvUtils.cornersCircles(cutMat, cloneFill);
        return src;
    }

    private Mat parseMat(Mat clone, int height, String text, int paddingLeft, int i ,String backgroundColor ) {
       return parseMat( clone,  height,  text,  paddingLeft,  i , backgroundColor, 0 );
    }
}
