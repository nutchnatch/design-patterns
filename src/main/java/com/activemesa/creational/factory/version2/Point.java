package com.activemesa.creational.factory.version2;

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
     * In order to keep Person constructor as private, we have to move PointFactory into Point class
      */
    public static class Factory {
        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double theta) {
            return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
        }
    }
}

class Demo {
    public static void main(String[] args) {
        Point point =  Point.Factory.newCartesianPoint(2, 3);
    }
}
