package com.activemesa.behavioral.memento;

import com.google.inject.internal.cglib.core.$ClassNameReader;
import com.google.inject.internal.cglib.core.$MethodInfo;

public class BankAccount {

    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    /**
     * Instead of being void, this method can return a Memento, so we can rollback the deposit operation
     * @param amount
     */
    public Memento deposit(int amount) {
        balance += amount;
        return new Memento(balance);
    }

    public void restore(Memento memento) {
        balance = memento.balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

/**
 * Will keep data that might be used to rollback to an old state
 * This would be a final class, because we don't want to change the memento
 * Other thing we have to keep in mind is related to the memory that we will use
 * It typically does not have functions of its own
 * It is not required to expose the state to each it reverts to
 * Can be used to redo undo
 */
class Memento {
    public int balance;

    public Memento(int balance) {
        this.balance = balance;
    }


}

class Demo {
    public static void main(String[] args) {
        final BankAccount ba = new BankAccount(100);
        Memento m1 = ba.deposit(50);
        Memento m2 = ba.deposit(25);
        System.out.println(ba);

        // restore
        ba.restore(m1);
        System.out.println(ba);

        ba.restore(m2);
        System.out.println(ba);
    }
}
