package com.javaoffers.material.base.opencv.executor;

import com.javaoffers.material.base.opencv.metasource.BarrageMetaSource;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class BarrageAbilityExecutorTest {

    BarrageAbilityExecutor barrageAbilityExecutor = new BarrageAbilityExecutor();
    String src = "img/dog.png";

    @Test
    public void executor() {
        LinkedList<String> text = new LinkedList<>();
        text.add("hello java good thank, 努力奋斗");
        text.add("opencv 真不错");
        text.add("opencv 太牛了 利害");
        text.add("hello java good thank, 努力奋斗");
        text.add("opencv 真不错");
        text.add("opencv 太牛了,good.");
        text.add("opencv 真不错");
        text.add("hello java good thank, 努力奋斗");
        text.add("opencv 太牛了");
        BarrageMetaSource barrageMetaSource = new BarrageMetaSource(src, "img/BarrageMetaSource5.avi", text);
        barrageAbilityExecutor.executor(barrageMetaSource);

    }
}