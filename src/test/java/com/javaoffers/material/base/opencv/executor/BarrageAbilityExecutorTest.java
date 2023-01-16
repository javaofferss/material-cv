package com.javaoffers.material.base.opencv.executor;

import com.javaoffers.material.base.opencv.metasource.BarrageMetaSource;
import org.junit.Test;

import static org.junit.Assert.*;

public class BarrageAbilityExecutorTest {

    BarrageAbilityExecutor barrageAbilityExecutor = new BarrageAbilityExecutor();
    String src = "img/dog.png";

    @Test
    public void executor() {
        BarrageMetaSource barrageMetaSource = new BarrageMetaSource(src, "img/BarrageMetaSource.png", "hello java good thank, 努力奋斗");
        barrageAbilityExecutor.executor(barrageMetaSource);

    }

    @Test
    public void executor2() {

        BarrageMetaSource barrageMetaSource = new BarrageMetaSource(src, "img/BarrageMetaSource2.png", "真不错哈哈哈哈");
        barrageAbilityExecutor.executor(barrageMetaSource);
    }
}