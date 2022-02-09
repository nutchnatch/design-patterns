package com.activemesa.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will contain information about changes to a particular property on objects of type T
 * @param <T>
 */
class PropertyChangedEventArgs<T> {
    public T source;
    public String propertyName;
    public Object newValue;

    public PropertyChangedEventArgs(T source, String propertyName, Object newValue) {
        this.source = source;
        this.propertyName = propertyName;
        this.newValue = newValue;
    }
}

/**
 * If we want to observe an object of type T, we implement this interface
 * Observer is looking to some changes
 * This interface is limited because it has no logic around the T generic
 * @param <T>
 */
interface Observer<T> {
    void handle(PropertyChangedEventArgs<T> args);
}

/**
 * Class which we can look at
 * @param <T>
 */
class Observable<T> {
    /**
     * Has a list os all subscribers that are looking for something
     */
    private List<Observer<T>> observers = new ArrayList<>();

    public void subscribe(Observer<T> observer) {
        observers.add(observer);
    }

    /**
     * Here for any change, an event is fired to notify all subscribers
     */
    protected void propertyChanged(T source, String propertyName, Object newValue) {
        for(Observer<T> o : observers) {
            /**
             * Call handle on every observer
             */
            o.handle(new PropertyChangedEventArgs<>(source, propertyName, newValue));
        }
    }
}

/**
 * A limitation for this class is that it has to implement Observable
 * This is an intrusive approach, an Observable must provide  an event to subscribe to.
 * Special care must be taken to prevent issues in multithreaded scenario
 */
public class Person extends Observable<Person>{
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(this.age == age) {
            return;
        }
        this.age = age;
        propertyChanged(this, "age", age);
    }
}

class Demo implements Observer<Person>{
    public static void main(String[] args) {
        new Demo();
    }

    public Demo() {
        final Person person = new Person();
        person.subscribe(this);
        for(int i = 20; i < 24; i ++) {
            person.setAge(i);
        }
    }

    @Override
    public void handle(PropertyChangedEventArgs<Person> args) {
        System.out.println("Peron's " + args.propertyName + " has changed to " + args.newValue);
    }
}
