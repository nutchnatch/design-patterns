package com.activemesa.creational.proxy.property;

/**
 * How to create a property proxy in order to realize an operation over property, for example logging property assignment
 */
public class Creature {
    private  Property<Integer> agility = new Property<>(10);
    public void setAgility(int value) {
        agility.setValue(value);
    }

    public int getAgility() {
        return agility.getValue();
    }
}

class Property<T> {
    //value we want to store
    private T value;

    public Property(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        //Here we can perform a logging operation for example.
        //We can have here a globally logging configuration with dependency injection
        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property<?> property = (Property<?>) o;

        return value != null ? value.equals(property.value) : property.value == null;
    }

    @Override
    public int hashCode()
    {
        return value != null ? value.hashCode() : 0;
    }
}

class DCreatureDemo {
    public static void main(String[] args) {
        Creature creature = new Creature();
        creature.setAgility(12);
    }
}
