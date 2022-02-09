package com.activemesa.behavioral.state;

/**
 * This is s classical implementation
 * We are changing the state through its reference, but it is not a definite way to do it
 */
public class State {
    void on(LightSwitch ls) {
        System.out.println("Light already on");
    }

    void off(LightSwitch ls) {
        System.out.println("Light already off");
    }
}

class OffState extends State {
    public OffState() {
        System.out.println("Light turned off ");
    }

    @Override
    void on(LightSwitch ls) {
        System.out.println("Switching light on...");
        ls.setState(new OnState());
    }
}

class OnState extends State {

    public OnState() {
        System.out.println("Light turned on");
    }

    @Override
    void off(LightSwitch ls) {
        System.out.println("Switching light off...");
        ls.setState(new OffState());
    }
}

class LightSwitch {

    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public LightSwitch() {
        this.state = new OffState();
    }

    void on() {
        state.on(this);
    }

    void off() {
        state.off(this);
    }
}


class Demo {
    public static void main(String[] args) {
        final LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.on();
        lightSwitch.off();
        lightSwitch.off();
    }
}