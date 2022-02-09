package com.activemesa.creational.factory.abstractfactory.exercise;

public class Person {

    public int id;
    public String name;

    public Person(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
}

class PersonFactory
{
    private int id = 0;
    public Person createPerson(String name)
    {
        return  new Person(id ++, name);
    }
}

class Demo {
    public static void main(String[] args) {
        Person po = new Person(1, "wilson");
        System.out.println("HH");
    }
}


