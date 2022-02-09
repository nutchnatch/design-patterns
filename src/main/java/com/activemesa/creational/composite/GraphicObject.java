package com.activemesa.creational.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class works like if it was a single object or as a group of them through its list of children.
 * In this case, it can be printed as a single object and all the children can also be printed as such.
 *
 */
public class GraphicObject {

    public GraphicObject(String name) {
        this.name = name;
    }

    //Weather it is a square, a circle or a rectangle, it has a name
    protected String name = "Group";
    public String color;
    public List<GraphicObject> children = new ArrayList<>();

    /**
     * This class is not abstract, and this constructor is to allow it to act as a group of different objects
     */
    public GraphicObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        print(sb, 0);
        return sb.toString();
    }

    private void print(StringBuilder stringBuilder,  int depth)
    {
        stringBuilder.append(String.join("", Collections.nCopies(depth, "*")))
                .append(depth > 0 ? " " : "")
                .append((color == null || color.isEmpty()) ? "" : color + " ")
                .append(getName())
                .append(System.lineSeparator());
        for (GraphicObject child : children)
            child.print(stringBuilder,  depth+1);
    }
}

class Circle extends GraphicObject {


    public Circle(String color) {
        this.name = "Color";
        this.color = color;
    }
}

class Square extends GraphicObject {


    public Square(String color) {
        this.name = "Square";
        this.color = color;
    }
}

class Demo {
    public static void main(String[] args) {
        final GraphicObject drawing = new GraphicObject();
        drawing.setName("My Drawing");
        drawing.children.add(new Square("red"));
        drawing.children.add(new Circle("Yellow"));

        GraphicObject group = new GraphicObject();
        group.children.add(new Circle("Blue"));
        group.children.add(new Square("Blue"));
        drawing.children.add(group);
        System.out.println(drawing);
    }
}