package com.javaoffers.material.base.opencv.executor;

import com.javaoffers.material.base.opencv.Ability;
import com.javaoffers.material.base.opencv.AbilityExecutor;
import com.javaoffers.material.base.opencv.metasource.BarrageMetaSource;
import com.javaoffers.material.base.opencv.metasource.StarMetaSource;

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

    }
}
