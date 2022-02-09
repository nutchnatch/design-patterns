package com.activemesa.creational.chainofresponsability.methodchain;

public class Creature {
    public String name;
    public int attack, defense;

    public Creature(String name, int attack, int defense) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    @Override
    public String toString() {
        return "Creature{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}

/**
 * To perform a modification on a creature
 */
class CreatureModifier {
    /**
     * Creature we are modifying
     * Creature modifiers can be chained together, so several creature modifiers may be chained together, thus a single
     * several modifier might be applied to a single creature.
      */
    protected Creature creature;
    protected CreatureModifier next;

    public CreatureModifier(Creature creature) {
        this.creature = creature;
    }

    public void add(CreatureModifier cm) {
        if(next != null) {
            next.add(cm);
        } else {
            next = cm;
        }
    }

    /**
     * Apply this modification through the chain
     */
    public void handle() {
        if(next != null) {
            next.handle();
        }
    }
}

class DoubleAttackModifier extends CreatureModifier {

    public DoubleAttackModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("Doubling " + creature.name + "'s attack");
        creature.attack *= 2;
        super.handle();
    }
}

class IncreaseDefenseModifier extends CreatureModifier {

    public IncreaseDefenseModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("Increasing " + creature.name + "'s defense");
        creature.defense += 3;
        super.handle();
    }
}

/**
 * This modifier can be used to stop the handling of the chain
 * Since on handle, it is not calling next.handle()
 */
class NoBonusModifier extends CreatureModifier {

    public NoBonusModifier(Creature creature) {
        super(creature);
    }

    @Override
    public void handle() {
        System.out.println("No bonus for you");
    }
}

class Demo {
    public static void main(String[] args) {
        final Creature goblin = new Creature("Goblin", 2, 2);
        System.out.println(goblin);
        final CreatureModifier root = new CreatureModifier(goblin);

        //Adding this modifier will stop the chain and no one will be handled anymore
        root.add(new NoBonusModifier(goblin));

        System.out.println("Let's double goblin's attack...");
        root.add(new DoubleAttackModifier(goblin));

        System.out.println("Let's double goblin's defense...");
        root.add(new IncreaseDefenseModifier(goblin));

        root.handle();
    }
}