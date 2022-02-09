package com.activemesa.behavioral.mediator.reactiveextension;


import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Use Observable which allows to create an observable component that we can subscribe to
 * This is a way to use this extension in order to implement Mediator pattern
 */
public class EventBroker extends Observable<Integer> {

    private List<Observer<? super Integer>> observers = new ArrayList<>();

    @Override
    protected void subscribeActual(@NonNull Observer<? super Integer> observer) {
        observers.add(observer);
    }

    public void publish(int n) {
        for(Observer<? super Integer> o : observers) {
            /**
             * This is how we put something on the reactive extension pipeline
             * This is how we supply the value
             */
            o.onNext(n);
        }
    }
}

class FootballPlayer {
    private int goalScore = 0;
    public EventBroker broker;
    public String name;

    public FootballPlayer(EventBroker broker, String name) {
        this.broker = broker;
        this.name = name;
    }

    public void score() {
        /**
         * This is how we inform anybody subscribed that we've just scored
         */
        broker.publish(++goalScore);
    }
}

class FootballCoach {
    public EventBroker broker;

    public FootballCoach(EventBroker broker) {
        broker.subscribe(i -> {
            System.out.println("Hey, you score " + i + " goals!");
        });
    }
}

class Demo {
    public static void main(String[] args) {
        final EventBroker broker = new EventBroker();
        final FootballPlayer player = new FootballPlayer(broker,"player");
        final FootballCoach coa = new FootballCoach(broker);

        player.score();
        player.score();
        player.score();

    }
}