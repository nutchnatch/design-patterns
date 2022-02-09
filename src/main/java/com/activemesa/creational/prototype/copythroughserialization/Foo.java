package com.activemesa.creational.prototype.copythroughserialization;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class Foo implements Serializable {

    public int stuff;
    public String whatever;

    public Foo(int stuff, String whatever) {
        this.stuff = stuff;
        this.whatever = whatever;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "stuff=" + stuff +
                ", whatever='" + whatever + '\'' +
                '}';
    }

}

class Demo {
    public static void main(String[] args) {
        Foo foo = new Foo(42, "life");
        // This option serializes and de-serializes the object resulting ina different new instance
        Foo foo2 = SerializationUtils.roundtrip(foo);
        foo2.whatever = "xyz";

        System.out.println(foo);
        System.out.println(foo2);
    }
}
