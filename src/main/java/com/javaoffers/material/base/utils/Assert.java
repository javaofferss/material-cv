package com.javaoffers.material.base.utils;

/**
 * @author mingJie
 */
public class Assert {

    public static void isTrue(boolean isTrue, String error){
        if(!isTrue){
            throw  new IllegalStateException(error);
        }
    }
}
