package com.javaoffers.material.base.opencv;

/**
 * @author mingJie
 */
public interface AbilityExecutor<T extends MetaSource> {

    Ability getAbility();

    void executor(T source);

}
