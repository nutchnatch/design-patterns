package com.activemesa.creational.decorator.staticcomposition;

import java.util.function.Supplier;

/**
 * Used to get some information from shape
 */
public interface Shape {

    String info();
}

class Circle implements Shape {

    private float radius;

    public Circle() {

    }

    void resize(float factor) {
        radius *= factor;
    }

    public Circle(float radius) {
        this.radius = radius;
    }

    @Override
    public String info() {
        return "A circle of radius " + radius;
    }
}

class Square implements Shape {

    private float size;

    public Square() {

    }

    public Square(float size) {
        this.size = size;
    }

    @Override
    public String info() {
        return "A square of size " + size;
    }
}

/**
 * What if we could bake all shapes in a single type
 * Let's construct it in a static way.
 * We will not construct this kind of class at compile time but rather on runtime
 * @param <T>
 */
class ColorShape<T extends Shape> implements Shape {

    private Shape shape;
    private String color;

    public ColorShape(Supplier<? extends T> ctor, String color) {
        this.color = color;
        this.shape = ctor.get();
    }

    @Override
    public String info() {
        return shape.info() + " has the color " + color;
    }
}

class TransparentShape<T extends Shape> implements Shape {
    private Shape shape;
    private int transparency;


    public TransparentShape(Supplier<? extends  T> ctor, int transparency) {
        this.transparency = transparency;
        this.shape = ctor.get();
    }

    @Override
    public String info() {
        return shape.info() + " has " + transparency + "% transparency";
    }
}

/**
 * Apply those different kind of decorators to the different shapes
 */
class ShapeDemo {
    public static void main(String[] args) {
        final ColorShape<Square> blueSquare = new ColorShape<>(() -> new Square(20), "blue");
        System.out.println(blueSquare.info());

        final TransparentShape<ColorShape<Circle>> myCircle = new TransparentShape<>(() -> new ColorShape<>(() -> new Circle(5), "green"), 50);
        System.out.println(myCircle.info());
    }
}