package com.activemesa.creational.bridge.exercise;

public interface Renderer {

    String whatToRenderAs();
}

abstract class Shape
{
    private Renderer renderer;
    public String name;

    public Shape(Renderer renderer)
    {
        this.renderer = renderer;
    }

    @Override
    public String toString()
    {
        return String.format("Drawing %s as %s",
                name, renderer.whatToRenderAs());
    }
}

class RasterRenderer implements Renderer
{

    @Override
    public String whatToRenderAs()
    {
        return "pixels";
    }
}

class VectorRenderer implements Renderer
{
    @Override
    public String whatToRenderAs()
    {
        return "lines";
    }
}

class Triangle extends Shape
{
    public Triangle(Renderer renderer)
    {
        super(renderer);
        name = "Triangle";
    }
}

class Square extends Shape
{
    public Square(Renderer renderer)
    {
        super(renderer);
        name = "Square";
    }
}

class Demo {
    public static void main(String[] args) {
        System.out.println(new Square(new VectorRenderer()).toString());
    }
}
