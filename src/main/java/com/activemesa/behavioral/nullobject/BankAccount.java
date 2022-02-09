package com.activemesa.behavioral.nullobject;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Proxy;
import java.time.LocalDate;

interface Log {
    void info(String msg);
    void warning(String msg);
}

public class BankAccount {
    public Log log;
    private int balance;

    public BankAccount(Log log) {
        this.log = log;
    }

    public void deposit(int amount) {
        balance += amount;

        // check for null everywhere?
        if (log != null)
        {
            log.info("Deposited " + amount
                    + ", balance is now " + balance);
        }
    }

    public void withdraw(int amount)
    {
        if (balance >= amount)
        {
            balance -= amount;
            if (log != null)
            {
                log.info("Withdrew " + amount
                        + ", we have " + balance + " left");
            }
        }
        else {
            if (log != null)
            {
                log.warning("Could not withdraw "
                        + amount + " because balance is only " + balance);
            }
        }
    }
}

class ConsoleLog implements Log {

    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warning(String msg) {
        System.out.println("WARNING: " + msg);
    }
}

final class NullObject implements Log {

    @Override
    public void info(String msg) {

    }

    @Override
    public void warning(String msg) {

    }
}

class Demo {

    /**
     * Instead of creating a NullObject for any object, we can use this dynamic proxy, where we create an object in
     * runtime.
     * Be careful on using this approach since it consumes a lot of resources. It is good to be used on unit tests.
     * @param itf
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T noOp(Class<T> itf) {
        return (T) Proxy.newProxyInstance(itf.getClassLoader(), new Class<?>[] { itf },
                (proxy, method, args) -> {
                    if(method.getReturnType().equals(Void.TYPE)) {
                        return null;
                    } else {
                        return method.getReturnType().getConstructor().newInstance();
                    }
                });
    }

    public static void main(String[] args) {
        final ConsoleLog log = new ConsoleLog();
        /**
         * Here we cannot pass log as null, because it will cause a null pointer exception
         * Anyway, we can do a preventive approach, verifying if log is null before using it
         * So, if we do not want to pass a log object instead of passing Log, we can use NullObject which implements Log
         * interface
         */
        final NullObject log2 = new NullObject();

        final Log dynamicLog = noOp(Log.class);

        final BankAccount account = new BankAccount(dynamicLog);
        account.deposit(100);
    }
}