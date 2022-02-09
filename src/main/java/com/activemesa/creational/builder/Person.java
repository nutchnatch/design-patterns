package com.activemesa.creational.builder;

/**
 * This is the case where we want to create just on single builder.
 */
public class Person {
    public String name;
    public String position;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                "position='" + position + '\'' +
                "}";
    }

}

/**
 * Here we use recursive generics in order to have a type argument that must be an inheritor of PersonBuilder.
 * In this case, EmployeeBuilder fits because it is an inheritor of PersonBuilder.
 * This is useful to preserve the fluent interface using those two classes: PersonBuilder and EmployeeBuilder.
 * @param <SELF>
 */
class PersonBuilder<SELF extends PersonBuilder<SELF>> {
    protected Person person = new Person();

    public SELF withName(String name) {
        person.name = name;
        return self();
    }

    public Person build() {
        return person;
    }

    /**
     * This method is useful because it can be override after by other builders.
     * @return
     */
    protected SELF self() {
        return (SELF) this;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {

    public EmployeeBuilder worksAt(String position) {
        person.position = position;
        return this;
    }

    @Override
    protected EmployeeBuilder self() {
        return this;
    }
}

class PersonDemo {
    public static void main(String[] args) {
        EmployeeBuilder pb = new EmployeeBuilder();
        Person person = pb
                .withName("kev")
                .worksAt("Developer")
                .build();

        System.out.println(person);
    }
}
