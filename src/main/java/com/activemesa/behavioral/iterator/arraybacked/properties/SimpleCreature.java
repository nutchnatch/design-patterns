package com.activemesa.behavioral.iterator.arraybacked.properties;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class SimpleCreature {
    private int strength, agility, intelligence;

    public int max() {
        return Math.max(strength, Math.max(agility, intelligence));
    }

    public int sum() {
        return strength + agility + intelligence;
    }

    public double average() {
        return sum() / 3.0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

}

/**
 * With this first implementation, if I had to add a new statistic, I would have to re-implement max(), sum(), avg(), etc
 * So, it is not robust
 * Instead of saving the fields individually, we can store them in an array
 * @return
 */
class Creature implements Iterable{

    /**
     * If a new property is required, then we can add it on this array
     */
    private int[] stats = new int[3];

    public int getStrength() {
        return stats[0];
    }

    public void setStrength(int value) {
        stats[0] = value;
    }

    public void setIntelligence(int value) {
        stats[2] = value;
    }

    public void setAgility(int value) {
        stats[1] = value;
    }


    public int sum() {
        return IntStream.of(stats).sum();
    }

    public int max() {
        return IntStream.of(stats).max().getAsInt();
    }

    public double average() {
        return IntStream.of(stats).average().getAsDouble();
    }
    @Override
    public Iterator iterator() {
        return IntStream.of(stats).iterator();
    }

    @Override
    public void forEach(Consumer action) {
        for(int item : stats) {
            action.accept(item);
        }
    }

    @Override
    public Spliterator spliterator() {
        return IntStream.of(stats).spliterator();
    }
}

class Demo {
    public static void main(String[] args) {
        final Creature creature = new Creature();
        creature.setAgility(12);
        creature.setIntelligence(13);
        creature.setStrength(17);

        System.out.println(
                "Creature has a maximum statistic of " + creature.max() +
                        ", total stats = " + creature.sum() +
                        " average stat = " + creature.average()
        );
    }
}