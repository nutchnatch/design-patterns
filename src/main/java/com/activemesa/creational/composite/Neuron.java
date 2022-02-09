package com.activemesa.creational.composite;

import java.util.*;
import java.util.function.Consumer;

/**
 * Represent a neuron network where one neuron is connected to others
 * This single element is treated a scalar and is masqueraded as a collection with Collections.singleton()
 */
public class Neuron implements SomeNeurons{

    public List<Neuron> in, out;

    @Override
    public Iterator<Neuron> iterator() {
        return Collections.singleton(this).iterator();
    }

    @Override
    public void forEach(Consumer<? super Neuron> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<Neuron> spliterator() {
        return Collections.singleton(this).spliterator();
    }

//    public void connectTo(Neuron other) {
//        //Outcoming ones must container other
//        out.add(other);
//        // other's income must contain this
//        other.in.add(this);
//    }
}

class NeuronLayer extends ArrayList<Neuron> implements SomeNeurons {


}

/**
 * The idea is to treat Neuron ans NeuronLayer equally - this is the core of composite design pattern
 * NeuronLayer is a collection of neurons as it extends a ArrayList<Neuron>.
 * Neuron can also be treated as a collection of neurons, but a singleton collection
 */
interface SomeNeurons extends Iterable<Neuron> {

    default void connectTo(SomeNeurons other) {
        if(this == other) {
            return;
        }

        for(Neuron from : this) {
            for(Neuron to : other) {
                from.out.add(to);
                to.in.add(from);
            }
        }
    }
}

class NeuronDemo {
    public static void main(String[] args) {
        final Neuron neuron = new Neuron();
        final Neuron neuron2 = new Neuron();
        final NeuronLayer layer = new NeuronLayer();
        final NeuronLayer layer2 = new NeuronLayer();

        neuron.connectTo(neuron2);
        neuron.connectTo(layer);
        layer.connectTo(neuron);
        layer.connectTo(layer2);
    }
}
