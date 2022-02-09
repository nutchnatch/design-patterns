package com.activemesa.creational.singleton.testability;

import java.util.function.Supplier;

public class SingletonTester {
    public static boolean isSingleton(Supplier<Object> func)
    {
        return func.get() == func.get();
    }
}
