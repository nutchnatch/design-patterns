package com.activemesa.creational.composite.exercises;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Evaluate {

    @Test
    public void test() {
        SingleValue singleValue = new SingleValue(11);
        ManyValues otherValues = new ManyValues();
        otherValues.add(22);
        otherValues.add(33);
        assertEquals(66, new MyList(Arrays.asList(singleValue, otherValues)).sum());
    }
}
