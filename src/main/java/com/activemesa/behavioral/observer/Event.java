package com.activemesa.behavioral.observer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * We will be able to subscribe to this event to get notifications an also unsibsci
 * @param <TArgs>
 */
public class Event<TArgs> {
    private int count = 0;
    private Map<Integer, Consumer<TArgs>> handlers = new HashMap<>();


    /**
     * This will return a Subscription that is a Memento
     * @param handler
     * @return
     */
    public Subscription addHandler(Consumer<TArgs> handler) {
        int i = count;
        handlers.put(count ++, handler);
        return new Subscription(this, i);
    }

    public void fire(TArgs args) {
        for(Consumer<TArgs> handler : handlers.values()) {
            handler.accept(args);
        }
    }

    /**
     * It implements AutoCloseable because we want it ti late for a certain amount of time
     */
    public class Subscription implements AutoCloseable {

        private Event<TArgs> event;
        /**
         * This is is the key of the hashmap, so we can remove on close method
         */
        private int id;

        public Subscription(Event<TArgs> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() {
            event.handlers.remove(id);
        }
    }
}

class PropertyChangedEventArguments {
    public Object source;
    public String propertyName;

    public PropertyChangedEventArguments(Object source, String propertyName) {
        this.source = source;
        this.propertyName = propertyName;
    }
}

/**
 * Differently from the first approach, this will not be implementing any Observable interface
 */
class ThePerson {
    /**
     * This event can be triggered and if we want an event notification, from this event, we can subscribe to it
     */
    public Event<PropertyChangedEventArguments> propertyChanged = new Event<>();

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(this.age == age) {
            return;
        }
        this.age = age;
        propertyChanged.fire(new PropertyChangedEventArguments(this, "age"));
    }

    /**
     * In this kind of method, it is not possible to add change notification
     * One way to do it is to put this logic verification on setAge method.
     * It can work for this simple example, but it would get complicated if we have a massive tree of dependency between
     * attributes.
     * @return
     */
    public boolean canVote() {
        return age >= 18;
    }
}

class ThisDemo {
    public static void main(String[] args) {
        final ThePerson person = new ThePerson();
        final Event<PropertyChangedEventArguments>.Subscription sub = person.propertyChanged.addHandler(x -> {
            System.out.println("Person's " + x.propertyName + " has changed");
        });

        person.setAge(17);
        person.setAge(18);
        sub.close();
        person.setAge(19);
    }
}