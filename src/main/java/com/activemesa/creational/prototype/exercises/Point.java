package com.activemesa.creational.prototype.exercises;

public class Point {

    public int x, y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this(other.x, other.y);
    }
}

class Line
{
    public Point start, end;

    public Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    public Line deepCopy()
    {
        Point newStart = new Point(start.x, start.y);
        Point newEnd = new Point(end.x, end.y);
        return new Line(newStart, newEnd);
    }
}

