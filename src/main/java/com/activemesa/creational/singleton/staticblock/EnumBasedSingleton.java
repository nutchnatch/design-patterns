package com.activemesa.creational.singleton.staticblock;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Enums are serializable by default, but it is not a serialization that preserves the state.
 * This class is not inheritable, and there is no state to be persisted.
 * An enum has a private default constructor. Anyway, it is not possible to make another instance of an Enum.
 *
 */
enum EnumBasedSingleton {
    INSTANCE;

    /**
     * This constructor has no private keyword because it is always private.
     * Since an Enum is Serializable by default, it cannot implement serializable so it cannot be serialized explicitly.
     * This is because the fields are not serialized. The only thing serializable is the name of the enum.
     */
    EnumBasedSingleton() {
        value = 42;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class Demo {
    static void saveToFile(EnumBasedSingleton singleton, String filename)
            throws Exception
    {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {
            out.writeObject(singleton);
        }
    }

    static EnumBasedSingleton readFromFile(String filename)
            throws Exception
    {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn) )
        {
            return (EnumBasedSingleton)in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        final String fileName = "myfile.bin";
        EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
//        singleton.setValue(111);
        saveToFile(singleton, fileName);

        EnumBasedSingleton singleton2 = readFromFile(fileName);
        System.out.println(singleton2.getValue());
    }
}
