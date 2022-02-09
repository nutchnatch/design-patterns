package com.activemesa.creational.adapter;

import java.util.*;
import java.util.function.Consumer;

public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Point point = (Point) obj;
        if(x != point.x) {
            return false;
        }
        return y == point.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Line line = (Line) obj;
        if(!Objects.equals(start, line.start)) {
            return false;
        }
        return Objects.equals(end, line.end);
    }

    @Override
    public int hashCode() {
        int result = start != null? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}

class VectorObject extends ArrayList<Line> {

}

class VectorRectangle extends VectorObject {
    public VectorRectangle(int x, int y, int width, int height) {
        add(new Line(new Point(x, y), new Point(x + width, y)));
        add(new Line(new Point(x + width, y), new Point(x + width, y + height)));
        add(new Line(new Point(x, y), new Point(x, y + height)));
        add(new Line(new Point(x, y + height), new Point(x + width, y + height)));
    }
}

/**
 * This is an adapter that tries to adapt line to point
 * A cache was created since the same points are generated multiple times
 * So, equals and hashcode were implemented on Point and Line in order to identify the same points
 * When adapters are used, usually repeated objects are created, so in that cases, we create a cache
 *
 */
class LineToPointAdapter implements Iterable<Point> {

    private static int count = 0;
    private  static Map<Integer, List<Point>> cache = new HashMap<>();
    private int hash;
    public LineToPointAdapter(Line line) {
        hash = line.hashCode();
        if(cache.containsKey(hash)) {
            return;
        }
        System.out.println(String.format("%d: Generating points for line [%d,%d]-[%d,%d] (with caching)",
                ++ count, line.start.x, line.start.y, line.end.x, line.end.y));

        List<Point> points = new ArrayList<>();
        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int top = Math.min(line.start.y, line.end.y);
        int bottom = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = line.end.y - line.start.y;

        if (dx == 0)
        {
            for (int y = top; y <= bottom; ++y)
            {
                points.add(new Point(left, y));
            }
        }
        else if (dy == 0)
        {
            for (int x = left; x <= right; ++x)
            {
                points.add(new Point(x, top));
            }
        }

        cache.put(hash, points);
    }

    @Override
    public Iterator<Point> iterator() {
        return cache.get(hash).iterator();
    }

    @Override
    public void forEach(Consumer<? super Point> action) {
        cache.get(hash).forEach(action);
    }

    @Override
    public Spliterator<Point> spliterator() {
        return cache.get(hash).spliterator();
    }
}

class Demo {

    private static final List<VectorObject> vectorObjects = new ArrayList<>(Arrays.asList(
            new VectorRectangle(1, 1, 10, 10),
            new VectorRectangle(3, 3, 6, 6)
    ));

    public static void drawPoint(Point point) {
        System.out.println(".");
    }

    public static void draw() {
        for(VectorObject vo : vectorObjects) {
            for(Line line : vo) {
                final LineToPointAdapter adapter = new LineToPointAdapter(line);
                adapter.forEach(Demo::drawPoint);
            }
        }
    }

    public static void main(String[] args) {
        draw();
    }
}