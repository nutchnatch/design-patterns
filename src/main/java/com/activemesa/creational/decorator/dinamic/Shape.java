package com.activemesa.creational.decorator.dinamic;

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
 * We don't have to change Circle and Square if we want to add color or transparency to shape
 * This is consistent with OCP. In this case, if we don't want to change those classes, we have to create a decorators.
 */
class ColoredShape implements Shape {

    /**
     * Specifies which shape it is to decorate
     */
    private Shape shape;
    private String color;

    public ColoredShape(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " has the color " + color;
    }
}

/**
 * Transparency decorator
 * Anyway, we are not allowed to directly call the method of circle through this decorator
 */
class TransparentShape implements Shape {

    private Shape shape;
    private int transparency;

    public TransparentShape(Shape shape, int transparency) {
        this.shape = shape;
        this.transparency = transparency;
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
        final Circle circle = new Circle(10);
        System.out.println(circle.info());

        final ColoredShape blueSquare = new ColoredShape(new Square(20), "blue");
        System.out.println(blueSquare.info());

        final TransparentShape myCircle = new TransparentShape(new ColoredShape(new Circle(5), "green"), 50);
        System.out.println(myCircle.info());
    }
}