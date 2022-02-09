package com.activemesa.creational.chainofresponsability.brokerchain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This class will be used on the observer pattern to build an event broker and at the same time the idea of a chain of
 * responsibility.
 * Here objects can go in and out of the system, they don't need to have reference one of another like in the standard
 * chain with the next variable.
 * This is the benefit of using the Mediator design pattern that is represented by event broker Game.
 * A event class will notify on queries. So thi will be used on context of CQS
 * We will have the ability to subscribe on an event, unsubscribe from it and also fire it.
 * Here we want to specify the consumers of the event.
 * @param <Args>
 */
public class Event<Args> {
    private int index = 0;
    /**
     * Subscribers which handles whenever an event fires up
     */
    private Map<Integer, Consumer<Args>> handlers = new HashMap<>();

    public int subscribe(Consumer<Args> handler) {
        int i = index;
        handlers.put(index ++, handler);
        return i;
    }

    public void unsubscribe(int key) {
        handlers.remove(key);
    }

    public void fire(Args args) {
        for(Consumer<Args> handler: handlers.values()) {
            handler.accept(args);
        }
    }
}

class Query {
    public String creatureName;
    enum Argument {
        ATTACK, DEFENSE
    }
    public Argument argument;

    /**
     * Attributes that handler can modify
     */
    public int result;

    public Query(String creatureName, Argument argument, int result) {
        this.creatureName = creatureName;
        this.argument = argument;
        this.result = result;
    }
}

/**
 * This class will be used on the mediator pattern.
 * We want a mediator, because we want a central location where the query event is actually kept.
 * Here any modifier can subscribe itself to queries on the creature and modify the creature's attack or defense values
 */
class Game {
    public Event<Query> queries = new Event<>();

}

class Creature {
    public Game game;
    public String name;
    public int baseAttack, baseDefense;

    public Creature(Game game, String name, int baseAttack, int baseDefense) {
        this.game = game;
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
    }

    /**
     * Will be the base value plus each modifier which has a reference to the event that can modify that value
     * @return
     */
    public int getAttack() {
        Query q = new Query(name, Query.Argument.ATTACK, baseAttack);
        /**
         * Fires the event and let any modifier which subscribed to the event, handle it and modify the query result.
         */
        game.queries.fire(q);
        return q.result;
    }

    public int getDefense() {
        Query q = new Query(name, Query.Argument.DEFENSE, baseDefense);
        /**
         * Fires the event and let any modifier which subscribed to the event, handle it and modify the query result.
         */
        game.queries.fire(q);
        return q.result;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "game=" + game +
                ", name='" + name + '\'' +
                ", attack=" + getAttack() +
                ", defense=" + getDefense() +
                '}';
    }
}

/**
 * Build modifiers.
 * Modifiers also reference how event broker, in this case it is the game
 * Here we specify
 */
class CreatureModifiers {
    protected Game game;
    private Creature creature;

    public CreatureModifiers(Game game, Creature creature) {
        this.game = game;
        this.creature = creature;
    }
}

/**
 * This is a modifier.
 * When the modifiers is closed, we want to get rid of the subscription and no longer subscribe into those events.
 * For this, we implements AutoCloseable
 */
class DoubleAttackModifier extends CreatureModifiers implements AutoCloseable{

    final int token;

    public DoubleAttackModifier(Game game, Creature creature) {
        super(game, creature);

        /**
         * Takes the mediator and subscribe to any query that queries the attack value
         */
        token = game.queries.subscribe(q -> {
            if (q.creatureName.equals(creature.name) && q.argument == Query.Argument.ATTACK) {
                q.result *= 2;
            }
        });
    }

    /**
     * Unsubscribe for handling any kind of changes to the underlying creature.
     */
    @Override
    public void close() {
        game.queries.unsubscribe(token);
    }
}

class IncreaseDefenceModifier extends CreatureModifiers {

    public IncreaseDefenceModifier(Game game, Creature creature) {
        super(game, creature);
        game.queries.subscribe(q -> {
            if (q.creatureName.equals(creature.name) && q.argument == Query.Argument.DEFENSE) {
                q.result += 3;
            }
        });
    }
}

class Demo {
    public static void main(String[] args) {

        /**
         * Creating the event broker
         */
        Game game = new Game();
        final Creature goblin = new Creature(game,"Strong Goblin", 2, 2);
        System.out.println(goblin);

        /**
         * The modifier is automatically applied and when we query for getAttack or getDefense, we will get the modifier
         * taking part, because it is subscribed to the event, inside the event broker.
         */
        final IncreaseDefenceModifier icm = new IncreaseDefenceModifier(game, goblin);
        // After this execution, it will be closed, because DoubleAttackModifier implements Closeable
        try(final DoubleAttackModifier dam = new DoubleAttackModifier(game, goblin)) {
            System.out.println(goblin);
        }

        System.out.println(goblin);
    }
}