package com.activemesa.creational.builder;

public class FluentBuilder {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        // This is a fluent interface - allow to write very long chains, which are useful to build thins up.
        sb.append("foo").append("bar");
    }
}
