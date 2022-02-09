package com.activemesa.creational.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public interface Human  {

    void walk();
    void talk();
}

class Person implements Human {

    @Override
    public void walk() {
        System.out.println("I am walking.");
    }

    @Override
    public void talk() {
        System.out.println("I am talking.");
    }
}

/**
 * Let's create a dynamic proxy that calculates the number of methods inside Person
 * We will use InvocationHandler, an interface from reflection library
 */
class LoggingHandler implements InvocationHandler {

    /**
     * We need a reference to the object that we are building a dynamic proxy for (at runtime)
     */
    private final Object target;

    //We need a map to record the number of method calls
    private Map<String, Integer>  calls = new HashMap<>();

    public LoggingHandler(Object target) {
        this.target = target;
    }

    /**
     * Used to invoke method with the given args
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String name = method.getName();
        if(name.contains("toString")) {
            return calls.toString();
        }
        calls.merge(name, 1, Integer::sum);
        return method.invoke(target, args);
    }
}

class HumanDemo {
    /**
     * Dynamic proxy with logging on any kind of object
     * @param target - object for with the logging is required
     * @param <T>
     * @param itf - interface we want to return
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T withLogging(T target, Class<T> itf) {
        return (T) Proxy.newProxyInstance(itf.getClassLoader(), new Class<?>[] { itf }, new LoggingHandler(target));
    }

    /**
     * Here we create this handler to be used like an interceptor to intercept the calls on person object, so we can
     * do any kine of inspections, and this is what a proxy is used for.
     * @param args
     */
    public static void main(String[] args) {
        Person person = new Person();
        final Human logged = withLogging(person, Human.class);
        logged.talk();
        logged.walk();
        logged.walk();
        System.out.println(logged);
    }
}