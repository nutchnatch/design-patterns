package com.activemesa.creational.factory;

enum CoordinateSystem {
    CARTESIAN,
    POLAR
}
public class Point {
    private double x, y;

    /**
     * a is x if Cartesian or rho if polar - - so this is a very bad idea
     * @param a
     * @param b
     * @param cs
     */
//    public Point(double a, double b, CoordinateSystem cs) {
//        switch (cs) {
//            case CARTESIAN:
//                this.x = a;
//                this.y = b;
//                break;
//            case POLAR:
//                x = a * Math.cos(b);
//                y = a * Math.sin(b);
//                break;
//        }
//
//    }

    /**
     * Constructor is private to force the use of the static methods in order to create a Point
     * @param x
     * @param y
     */
    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Factory methods can be used by creating static method for the specific cases when creating an instance of Point
     * @param x
     * @param y
     * @return
     */
    public static Point newCartesianPoint(double x, double y) {
        return new Point(x, y);
    }

    public static Point newPolarPoint(double rho, double theta) {
        return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
    }
}

class Demo {
    public static void main(String[] args) {
        Point point =  Point.newPolarPoint(2, 3);
    }
}
