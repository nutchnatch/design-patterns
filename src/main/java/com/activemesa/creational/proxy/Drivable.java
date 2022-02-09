package com.activemesa.creational.proxy;

public interface Drivable {
    void drive();
}

class Car implements Drivable {

    protected Driver driver;

    public Car(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void drive() {
        System.out.println("Car being driven.");
    }
}

class Driver {
    public int age;

    public Driver(int age) {
        this.age = age;
    }
}

class CarProxy extends Car {

    public CarProxy(Driver driver) {
        super(driver);
    }

    @Override
    public void drive() {
        if(driver.age >= 16) {
            super.drive();
        } else {
            System.out.println("Driver too long!");
        }
    }
}


class Demo {
    public static void main(String[] args) {
        /**
         * Instead of calling the proxy directly, we can configure dependency injection to call CallProxy when Car
         * instance is created.
         */
        Car car = new CarProxy(new Driver(12));
        car.drive();
    }
}