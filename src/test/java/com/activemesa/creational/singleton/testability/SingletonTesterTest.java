package com.activemesa.creational.singleton.testability;
import org.junit.Test;
import static org.junit.Assert.*;

public class SingletonTesterTest {

    @Test
    public void testSingleton() {
        Object object = new Object();
        assertTrue(SingletonTester.isSingleton(() -> object));
        assertFalse(SingletonTester.isSingleton(Object::new));
    }
}
