package com.activemesa.creational.composite.exercises;

import java.util.*;
import java.util.function.Consumer;

interface ValueContainer extends Iterable<Integer> {}

public class SingleValue implements ValueContainer {

    public int value;

    // please leave this constructor as-is
    public SingleValue(int value)
    {
        this.value = value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singleton(value).iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        action.accept(value);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Collections.singleton(value).spliterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer {

}

class MyList extends ArrayList<ValueContainer>
{
    // please leave this constructor as-is
    public MyList(Collection<? extends ValueContainer> c)
    {
       super(c);
    }

    public int sum()
    {
        int result = 0;
        for(ValueContainer value : this) {
            for(int i : value) {
                result += + i;
            }
        }
        return result;
    }
}
