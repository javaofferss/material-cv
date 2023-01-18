package com.javaoffers.material.base.opencv.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mingJie
 */
public class Utils {

    //获取文本对应的mat 长度. 这里的值都是经过调试所得最佳值
    public static int getWidth(String text){
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


    public static List<Double> parseRGB(String backgroundColor) {
        String substring = backgroundColor.substring(1, backgroundColor.length());
        int c = substring.length() / 3;
        List<Double> rgb = new ArrayList<>(3);
        for (int i = 1; i <= 3; i++) {
            int start = (i - 1) * c;
            int end = start + c;
            double color = Integer.parseInt(substring.substring(start, end), 16);
            rgb.add(color);
        }
        return rgb;
    }
}
