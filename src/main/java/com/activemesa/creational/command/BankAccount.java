package com.activemesa.creational.command;

import com.sun.tools.javac.util.List;

import java.util.Collection;
import java.util.Collections;

public class BankAccount {
    private int  balance;
    private int overdraftLimit = -500;

    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ", balance is now " + balance);
    }

    public boolean withdraw(int amount) {
        if(balance - amount >= overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrew " + amount + ", balance is now " + balance);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}

interface Command {
    void call();
    void undo();
}

class BankAccountCommand implements Command{
    private BankAccount account;
    private boolean succeeded;

    @Override
    public void call() {
        switch(action) {
            case DEPOSIT:
                succeeded = true;
                account.deposit(amount);
                break;
            case WITHDRAW:
                succeeded = account.withdraw(amount);
                break;
        }
    }

    @Override
    public void undo() {
        if(!succeeded) {
            return;
        }
        switch(action) {
            case DEPOSIT:
                account.withdraw(amount);
                break;
            case WITHDRAW:
                account.deposit(amount);
                break;
        }
    }

    public enum Action {
        DEPOSIT, WITHDRAW
    }

    private Action action;
    private int amount;

    public BankAccountCommand(BankAccount account, Action action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }
}

class Demo {
    public static void main(String[] args) {
        final BankAccount ba = new BankAccount();
        System.out.println(ba);
        final List<BankAccountCommand> commands = List.of(
                new BankAccountCommand(ba, BankAccountCommand.Action.DEPOSIT, 100),
                new BankAccountCommand(ba, BankAccountCommand.Action.WITHDRAW, 1000)
        );

        for(Command command: commands) {
            command.call();
            System.out.println(ba);
        }

        for(Command command: commands) {
            command.undo();
            System.out.println(ba);
        }
    }
}