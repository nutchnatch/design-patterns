package com.activemesa.creational.flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class User {

    private String fullName;

    public User(String fullName) {
        this.fullName = fullName;
    }
}

/**
 * Flyweight
 * This class will kep a cache for names
 * For each name, we split it and save their indices on the array
 */
class User2 {
    static List<String> strings = new ArrayList<>();
    private int[] names;

    public User2(String fullName) {
        Function<String, Integer> getOrAdd = (String s) -> {
            int idx = strings.indexOf(s);
            if(idx != -1) {
                return idx;
            } else {
                strings.add(s);
                return strings.size() - 1;
            }
        };
        names = Arrays.stream(fullName.split(" "))
                .mapToInt(getOrAdd::apply)
                .toArray();
    }
}

class Demo {
    public static void main(String[] args) {
        /**
         * Here we have two names with the same Word (Smith) and if we save all of them, we are wasting memory
         * Flyweight is a pattern that helps with this optimization
         */
        final User2 user = new User2("John Smith");
        final User2 user2 = new User2("Jane Smith");
    }
}