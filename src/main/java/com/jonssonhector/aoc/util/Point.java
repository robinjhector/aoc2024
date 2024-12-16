package com.jonssonhector.aoc.util;

import java.util.Comparator;

public record Point(int x, int y) implements Comparable<Point> {

    public static Point ZERO = new Point(0, 0);

    public static Point fromCsv(String csv) {
        var parts = csv.split(",");
        return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    public static Point fromIntArr(Integer[] integers) {
        if (integers.length != 2) {
            throw new IllegalArgumentException("Array must have length 2");
        }

        return new Point(integers[0], integers[1]);
    }

    public Point wrapAround(Point max) {
        return new Point(
            wrapAround(x, max.x()),
            wrapAround(y, max.y())
        );
    }

    private static int wrapAround(int value, int max) {
        while (value < 0) {
            value += max;
        }
        while (value >= max) {
            value -= max;
        }
        return value;
    }

    public Point dx(int dx) {
        return new Point(x + dx, y);
    }

    public Point dy(int dy) {
        return new Point(x, y + dy);
    }

    public int distance(Point o) {
        return Math.abs(x - o.x()) + Math.abs(y - o.y());
    }

    public Point move(Direction direction) {
        return move(direction, 1);
    }

    public Point move(Direction direction, int num) {
        return switch (direction) {
            case N -> dy(-num);
            case NE -> dx(num).dy(-num);
            case E -> dx(num);
            case SE -> dx(num).dy(num);
            case S -> dy(num);
            case SW -> dx(-num).dy(num);
            case W -> dx(-num);
            case NW -> dx(-num).dy(-num);
        };
    }

    public Point[] adj3x3() {
        return new Point[] {
            move(Direction.NW), move(Direction.N), move(Direction.NE),
            move(Direction.W), this, move(Direction.E),
            move(Direction.SW), move(Direction.S), move(Direction.SE)
        };
    }

    public boolean isAdjacentVH(Point other) {
        if (x == other.x()) {
            return Math.abs(y - other.y()) == 1;
        } else if (y == other.y()) {
            return Math.abs(x - other.x()) == 1;
        }

        return false;
    }

    @Override
    public int compareTo(Point o) {
        return distance(o);
    }

    public static Comparator<Point> compareByYX() {
        return Comparator.comparing(Point::y).thenComparing(Point::x);
    }
}
