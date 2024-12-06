package com.jonssonhector.aoc.util;

public record Point(int x, int y) implements Comparable<Point> {

    public static Point ZERO = new Point(0, 0);

    public static Point fromIntArr(Integer[] integers) {
        if (integers.length != 2) {
            throw new IllegalArgumentException("Array must have length 2");
        }

        return new Point(integers[0], integers[1]);
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

    @Override
    public int compareTo(Point o) {
        return distance(o);
    }
}
