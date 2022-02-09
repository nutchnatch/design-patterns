package com.activemesa.behavioral.mediator.chatroom;

import java.util.ArrayList;
import java.util.List;

/**
 * With this pattern, persons doesn't have a direct reference to other person
 * Nut they have way of referencing to other people using their name
 */
public class Person {

    public String name;
    public ChatRoom room;
    private List<String> chatLog = new ArrayList<>();

    public Person(String name)
    {
        this.name = name;
    }

    public void receive(String sender, String message)
    {
        String s = sender + ": '" + message + "'";
        System.out.println("[" + name + "'s chat session] " + s);
        chatLog.add(s);
    }

    public void say(String message)
    {
        room.broadcast(name, message);
    }

    public void privateMessage(String who, String message)
    {
        room.message(name, who, message);
    }
}

class ChatRoom {

    private List<Person> people = new ArrayList<>();

    public void broadcast(String source, String message)
    {
        for (Person person : people)
            if (!person.name.equals(source))
                person.receive(source, message);
    }

    public void join(Person p)
    {
        String joinMsg = p.name + " joins the chat";
        broadcast("room", joinMsg);

        p.room = this;
        people.add(p);
    }

    public void message(String source, String destination, String message)
    {
        people.stream()
                .filter(p -> p.name.equals(destination))
                .findFirst()
                .ifPresent(person -> person.receive(source, message));
    }
}

class Demo {
    public static void main(String[] args) {
        final ChatRoom room = new ChatRoom();
        final Person john = new Person("Jhon");
        final Person jane = new Person("Jane");
        room.join(john);
        room.join(jane);
        john.say("hy room");
        jane.say("oh, hey john");

        final Person simon = new Person("Simon");
        room.join(simon);
        simon.say("hi everyone!");

        john.privateMessage("Simon", "glad you can join us");
    }
}
