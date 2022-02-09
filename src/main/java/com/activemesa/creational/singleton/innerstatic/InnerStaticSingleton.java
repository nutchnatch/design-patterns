package com.activemesa.creational.singleton.innerstatic;

/**
 * This is a way to create a lazy and synchronized singleton without synchronized keyword
 */
public class InnerStaticSingleton {

    private InnerStaticSingleton() {

    }

    /**
     * There is no need to take care of thread safety
     */
    private static class Impl {
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();

    }

    public InnerStaticSingleton getInstance() {
        return Impl.INSTANCE;
    }
}
