package com.activemesa.creational.bridge;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public interface Render {
    void renderCircle(float radius);
}

class VectorRender implements  Render {

    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing a circle of radius " +
                radius);
    }
}

class RasterRender implements Render {

    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing pixels form a circle of radius " + radius);
    }
}

abstract class Shape {
    protected Render render;

    public Shape(Render render) {
        this.render = render;
    }

    public abstract void draw();
    public abstract void resize(float factor);
}

class Circle extends Shape {

    public float radius;

    public Circle(Render render, float radius) {
        super(render);
        this.radius = radius;
    }

    /**
     * This is the constructor used when creating a Circle with injection
     * @param render
     */
    @Inject
    public Circle(Render render) {
        super(render);
    }

    @Override
    public void draw() {
        render.renderCircle(radius);
    }

    @Override
    public void resize(float factor) {
        radius *= factor;
    }
}

/**
 * Used to build a module which configure what happens when someone has a dependency on a renderer
 */
class ShapeModule extends AbstractModule {

    /**
     * Every time we want a Render.class to be injected, we create a new instance of VectorRender
     * This is a single point where we can specify which kind of renderer we want to use
     */
    @Override
    protected void configure() {
        bind(Render.class).to(VectorRender.class);
    }
}

/**
 * For this to continue to work, we have to create many renders for each new type
 * What if we have a complicated application and we want to supply a single render to every object being constructed
 * One way to reach this is to provide a properly dependency injection, for example google guice
 */
class Demo {
    public static void main(String[] args) {
//        final RasterRender raster = new RasterRender();
//        final VectorRender vendor = new VectorRender();
//        Circle circle = new Circle(vendor, 5);
//        circle.draw();
//        circle.resize(2);
//        circle.draw();

        final Injector injector = Guice.createInjector(new ShapeModule());
        Circle instance = injector.getInstance(Circle.class);
        instance.radius = 3;
        instance.draw();
        instance.resize(2);
        instance.draw();
    }
}
