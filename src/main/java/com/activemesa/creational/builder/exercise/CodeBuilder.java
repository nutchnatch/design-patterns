package com.activemesa.creational.builder.exercise;

import java.util.ArrayList;
import java.util.List;

public class CodeBuilder {

    private Class theClass = new Class();
    public CodeBuilder(String className)
    {
        theClass.name = className;
    }

    public CodeBuilder addField(String name, String type) {
        Field field = new Field(name, type);
        theClass.fields.add(field);
        return this;
    }

    @Override
    public String toString() {
        return theClass.toString();
    }
}

class Field {
    public String type;
    public String name;

    public Field(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("public %s %s;", type, name);
    }
}

class Class {
    public String name;
    public List<Field> fields = new ArrayList<>();

    public Class() {}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String ln = System.lineSeparator();
        sb
            .append("public class " + name)
            .append(ln)
            .append("{")
            .append(ln);
        for(Field field : fields) {
            sb.append(" " + field).append(ln);
        }
        sb.append("}").append(ln);
        return sb.toString();
    }
}

class Demo {
    public static void main(String[] args) {
        CodeBuilder cb = new CodeBuilder("Person");
        cb
            .addField("name", "String")
            .addField("age", "int");
        System.out.println(cb.toString());

    }
}
