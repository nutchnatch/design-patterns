package com.activemesa.creational.singleton.multiton;

import java.util.HashMap;

/**
 * In this case, we are using an enum to specify the type of instance we want to create.
 */
enum Subsystem {
    PRIMARY,
    AUXILIARY,
    FALLBACK
}

/**
 * Multiton is a way to create a finate set of instance of a class
 */
class Printer {

    private static int instanceCount = 0;
    private Printer () {
        instanceCount ++;
        System.out.println("A total of " + instanceCount + " instances created so far.");
    }

    // To create a key-value pair of lazyloaded classes
    private static HashMap<Subsystem, Printer> instances = new HashMap<>();
    public static Printer get(Subsystem ss) {
        if(instances.containsKey(ss)) {
            return instances.get(ss);
        }

        Printer instance = new Printer();
        instances.put(ss, instance);
        return instance;
    }
}

public class Multiton {
    public static void main(String[] args) {
        Printer main = Printer.get(Subsystem.PRIMARY);
        Printer aux = Printer.get(Subsystem.AUXILIARY);
        Printer fallback = Printer.get(Subsystem.FALLBACK);
    }
}
