package com.activemesa.creational.singleton.staticblock;

import java.io.File;
import java.io.IOException;

/**
 * This technique can be used in case we might have exceptions on singleton constructor
 * In this way, we can handle the exception.
 */
public class StaticBlockSingleton {

    private StaticBlockSingleton() throws IOException {
        System.out.println("Singleton is initializing.");
        File.createTempFile(".", "");
    }

    private static StaticBlockSingleton instance;

    static {
        try {
            instance = new StaticBlockSingleton();
        } catch(Exception e) {
            System.err.println("Failed to create singleton.");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}
