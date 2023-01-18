package com.javaoffers.material.base.opencv.executor;

import com.javaoffers.material.base.opencv.metasource.StarMetaSource;
import org.junit.Test;

import static org.junit.Assert.*;

public class StarAbilityExecutorTest {

    StarAbilityExecutor starAbilityExecutor = new StarAbilityExecutor();
    @Test
    public void executor() {
        //星星尽量不要太大.会影响效果
        StarMetaSource starMetaSource = new StarMetaSource("img/dog.png", "img/StarAbilityExecutorTest5.avi", 15, 35);
        starAbilityExecutor.executor(starMetaSource);

    }
}