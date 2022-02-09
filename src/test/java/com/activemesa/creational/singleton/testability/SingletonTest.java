package com.activemesa.creational.singleton.testability;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class SingletonTest {

    /**
     * This is not a unit test, but an integration test since we are using real data instead of dummy data.
     * Given that we are using real data, this test is not consistent, because if real data changes overtime,
     * these tests stop passing
     * So, we can use dependency injection in order to provide an abstraction instead of a a concrete class
     */
    //@Test
    public void singletonTotalPopulation() {
        final SingletonRecordFinder rf = new SingletonRecordFinder();
        List<String> names = Arrays.asList("Seoul", "Mexico City");
        int tp = rf.getTotalPopulation(names);
        assertEquals(1750000+1740000, tp);
    }

    /**
     * This is a unit test that tet functionality with dummy data without being dependent on real data
     */
    @Test
    public void dependentPopulationTest() {
        final DummyDatabase db = new DummyDatabase();
        final ConfigurableRecordFibder rf = new ConfigurableRecordFibder(db);
        assertEquals(4, rf.getTotalPopulation(Arrays.asList("alpha", "gamma")));
    }

    public static class SingletonTester {

        public static boolean isSingleton(Supplier<Object> func)
        {
            return func.get() == func.get();
        }
    }
}
