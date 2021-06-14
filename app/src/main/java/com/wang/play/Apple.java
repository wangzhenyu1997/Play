package com.wang.play;

public class Apple {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");

    }
}
