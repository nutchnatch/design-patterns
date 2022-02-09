package com.activemesa.solid.lsp;

import org.w3c.dom.css.Rect;

public class Rectangle {

    protected int width, height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return height * width;
    }

    @Override
    public String toString() {
        return "Rectangle " +
                "width=" + width +
                ", height" + height +
                '}';
    }

    public boolean isSquare() {
        return width == height;
    }
}

class RectangleFactory {
    public static Rectangle newRectangle(int width, int hieght) {
        Rectangle newRect = new Rectangle();
        newRect.setWidth(width);
        newRect.setHeight(hieght);
        return newRect;
    }

    public static Rectangle newSquare(int size) {
        Rectangle newRect = new Rectangle();
        newRect.setWidth(size);
        newRect.setHeight(size);
        return newRect;
    }
}

class Square extends Rectangle{
    public Square() {}
    public Square(int size) {
        width = height = size;
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }
}

class Demo {

    static void useIt(Rectangle r) {
        int width = r.getWidth();
        r.setHeight(10);
        //area = width * 10;
        System.out.println(
                "Expect area of " + width * 10 +
                        ", got " + r.getArea()
        );
    }

    public static void main(String[] args) {
        Rectangle rc = new Rectangle();
        rc.setHeight(2);
        rc.setWidth(3);
        useIt(rc);

        Rectangle sq = new Square();
        sq.setWidth(5);
        useIt(sq);
    }
}
