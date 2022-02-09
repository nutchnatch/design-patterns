package com.activemesa.creational.singleton;

import java.io.*;

public class BasicSingleton implements Serializable {

    private BasicSingleton() {

    }

    private static final BasicSingleton INSTANCE = new BasicSingleton();
    public static BasicSingleton getInstance() {
        return INSTANCE;
    }

    private int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * This resolver gives to JVM a hint that whenever a serialization happens, it has to happen on the context of this
     * INSTANCE
     * @return
     */
    protected Object readResolve() {
        return INSTANCE;
    }
}

class Demo {

    static  void saveToFile(BasicSingleton singleton, String fileName) throws Exception {
        try(FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(singleton);
        }
    }

    static BasicSingleton readFromFile(String fileName) throws Exception{
        try(FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn)) {

            return (BasicSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {

        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(123);
//        System.out.println(singleton.getValue());

        //Anyway, there are problems associated to singleton:
        // 1 - Using reflection, constructor can be got and create a public access to it.
        // 2 - Serialization - when an object is serialized, JVM does not care if its constructor is private or not.

        String fileName = "singleton.bin";
        saveToFile(singleton, fileName);
        singleton.setValue(222);
        BasicSingleton singleton2 = readFromFile(fileName);
        System.out.println(singleton == singleton2);
        System.out.println(singleton.getValue());
        System.out.println(singleton2.getValue());

        // As we can see by the results, the objects are different, so we have two instances of the same singleton class.
        // A way to solve this is to provide a resolver to the singleton class.
    }
}