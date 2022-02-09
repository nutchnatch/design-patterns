package com.activemesa.creational.builder.facets;

/**
 * This is the case where we want to create multiple builders since the complexity of the context.
 */
public class Person {

    /**
     * Address
     */
    public String streetAddress, postCode, city;

    /**
     * Employment
     */
    public String companyName, position;
    public int annualIncome;

    @Override
    public String toString() {
        return "Person{" +
                "streetAddress='" + streetAddress + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}

/**
 * Builder Facade
 */
class PersonBuilder {
    protected Person person = new Person();

    public Person build() {
        return person;
    }

    public PersonAddressBuilder lives() {
        return new PersonAddressBuilder(person);
    }

    public PersonJobBuilder works() {
        return new PersonJobBuilder(person);
    }
}

/**
 * Builder for address information
 * Extending PersonaçBuilder is critical to have facade Builder
 * This reason for this extension is to switch from one builder to another sub-builder in a single fluent API call.
 */
class PersonAddressBuilder  extends PersonBuilder{
    /**
     * Takes a reference to person being created
     * We need a reference of person inside any builder
     * @param person
     */
    public PersonAddressBuilder(Person person) {
        this.person = person;
    }

    public PersonAddressBuilder at(String streetAddress) {
        person.streetAddress = streetAddress;
        return this;
    }

    public PersonAddressBuilder withPostCode(String postCode) {
        person.postCode = postCode;
        return this;
    }

    public PersonAddressBuilder in(String city) {
        person.city = city;
        return this;
    }
}

class PersonJobBuilder  extends PersonBuilder {
    /**
     * Takes a reference to person being created
     * We need a reference of person inside any builder
     *
     * @param person
     */
    public PersonJobBuilder(Person person) {
        this.person = person;
    }

    public PersonJobBuilder at(String companyName) {
        person.companyName = companyName;
        return this;
    }

    public PersonJobBuilder earning(int annualIncome) {
        person.annualIncome = annualIncome;
        return this;
    }

    public PersonJobBuilder asA(String position) {
        person.position = position;
        return this;
    }
}

class Demo {
    public static void main(String[] args) {
        PersonBuilder pb = new PersonBuilder();
        Person person = pb
                .lives()
                    .at("123 London Road")
                    .in("London")
                    .withPostCode("SW12BC")
                .works()
                    .at("Fabrikam")
                    .asA("Engineer")
                    .earning(123000)
                .build();
        System.out.println(person);
    }
}