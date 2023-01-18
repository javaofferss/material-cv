package com.javaoffers.material.base.opencv.executor;

import com.javaoffers.material.base.opencv.Ability;
import com.javaoffers.material.base.opencv.AbilityExecutor;
import com.javaoffers.material.base.opencv.metasource.BarrageMetaSource;
import com.javaoffers.material.base.opencv.metasource.StarMetaSource;
import com.javaoffers.material.base.opencv.utils.OpencvUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Scalar;

import java.util.LinkedList;
import java.util.List;

/**
 * @author mingJie
 */
public class StarAbilityExecutor implements AbilityExecutor<StarMetaSource> {

    @Override
    public Ability getAbility() {
        return Ability.STAR;
    }

    @Override
    public void executor(StarMetaSource source) {
        Mat imread = OpencvUtils.readImg(source.getSrcFilePath()).clone();
        //OpencvUtils.cover2RGBA(imread);
        List<Star> stars = new LinkedList<>();
        for(int i = 0 ; i < source.getStarCount(); i++){
            double x = Math.random() * (imread.arrayWidth() * 1.0);
            double y = Math.random() * (imread.arrayHeight() * 1.0);
            int size = (int)(Math.random() * source.getStartRange());
            size = size == 0 ? source.getStartRange() : size;
            size = size > imread.arrayWidth()?imread.arrayWidth(): size;
            size = size > imread.arrayHeight()?imread.arrayHeight() : size;
            try {
                //这些参数都是最终调试所得的最佳值.
                Pair<Mat,Mat> mat = OpencvUtils.drawStar(imread, (int) x, (int) y, size, 2, Scalar.WHITE,  2, 15);
                //先将小星星部分隐藏
                double alpha = oneFloat(Math.random());
                boolean isUp = alpha < 0.2;
                double beta = 1.0 - alpha;
                stars.add(new Star(mat.getLeft().clone(),  (int)x, (int)y, size, mat.getRight(),alpha, beta, isUp ));

            }catch (Exception e){
                //e.printStackTrace();
                i--;
            }
        }

        for(Star star : stars){
            Mat starMat = star.getStarMat();
            Mat fillMat = OpencvUtils.cutMat(imread, star.starX, star.starY, star.starSize, star.starSize);
            OpencvUtils.fusion(starMat, star.alpha, fillMat, star.beta, 0, starMat);
        }


        List<Mat> mats = new LinkedList<>();
        double starCount = stars.size();
        int fps =  25; //默认25. 最后输出的时候可能会变.
        int second = 5;
        int scont = fps * second * 10;
        fps = (int) (scont / second);
        for(int i = 0; i < scont ;  i++){
            double floor = Math.floor(Math.random() * starCount);
            floor = floor > starCount ? starCount - 1 : floor < 0 ? 0 : floor;
            Star star = stars.get((int) floor);
            double load = 0.18;
            if(star.isUp){
                star.setAlpha(star.alpha + load);
                star.setBeta(star.beta - load);
                if(star.alpha >= 1.0){

                    star.setUp(false);
                }
            }else {
                star.setAlpha(star.alpha - load);
                star.setBeta(star.beta + load);
                if(star.alpha <= 0.0){
                    star.setUp(true);
                }
            }

            Mat fillMat = OpencvUtils.cutMat(imread, star.starX, star.starY, star.starSize, star.starSize);

            OpencvUtils.fusion(star.getStarMat(), star.alpha, star.getFillMat(), star.beta, 0, fillMat);
            OpencvUtils.mergeSimilar(10, fillMat, star.getFillMat());
            mats.add(imread.clone());
        }

        //OpencvUtils.writeImg(source.getDesFilePath(), imread);
        //输出5秒
        OpencvUtils.writeVideoFromJpg(source.getDesFilePath(), mats, fps);
    }

    //保留一位小数
    public double oneFloat(double d){
        d = d * 10.0;
        int i = (int)d;
        return i * 1.0 / 10;
    }

    static class Star{
        Mat starMat;
        int starX;
        int starY;
        int starSize;
        Mat fillMat;
        double alpha;
        double beta;
        boolean isUp;

        public Star(Mat starMat, int starX, int starY, int starSize, Mat fillMat, double alpha, double beta, boolean isUp) {
            this.starMat = starMat;
            this.starX = starX;
            this.starY = starY;
            this.starSize = starSize;
            this.fillMat = fillMat;
            this.alpha = alpha;
            this.beta = beta;
            this.isUp = isUp;
        }

        public Mat getStarMat() {
            return starMat;
        }

        public void setStarMat(Mat starMat) {
            this.starMat = starMat;
        }

        public Mat getFillMat() {
            return fillMat;
        }

        public void setFillMat(Mat fillMat) {
            this.fillMat = fillMat;
        }

        public double getAlpha() {
            return alpha;
        }

        public void setAlpha(double alpha) {
            this.alpha = alpha;
        }

        public double getBeta() {
            return beta;
        }

        public void setBeta(double beta) {
            this.beta = beta;
        }

        public boolean isUp() {
            return isUp;
        }

        public void setUp(boolean up) {
            isUp = up;
        }
    }
}
