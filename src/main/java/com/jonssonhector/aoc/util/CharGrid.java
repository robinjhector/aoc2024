package com.jonssonhector.aoc.util;

import java.util.stream.Stream;

public record CharGrid(char[][] data) implements Grid {

    public CharGrid {
        if (data.length == 0) {
            throw new IllegalArgumentException("Empty grid");
        }

        var width = data[0].length;
        if (width == 0) {
            throw new IllegalArgumentException("Empty grid");
        }

        for (var row : data) {
            if (row.length != width) {
                throw new IllegalArgumentException("Rows have different lengths");
            }
        }
    }

    public static CharGrid parse(String input) {
        return new CharGrid(input.lines()
            .map(String::toCharArray)
            .toArray(char[][]::new));
    }

    public char get(Point point) {
        if (contains(point)) {
            return data[point.y()][point.x()];
        }

        throw new IllegalArgumentException("Point out of bounds: " + point);
    }

    public String extractRange(Point starting, Direction direction, int maxLen) {
        var sb = new StringBuilder();
        var point = starting;

        for (int i = 0; i < maxLen; i++) {
            if (contains(point)) {
                sb.append(get(point));
                point = point.move(direction);
            } else {
                break;
            }
        }

        return sb.toString();
    }

    public boolean contains(Point point) {
        return point.x() >= 0 && point.x() < width() &&
            point.y() >= 0 && point.y() < height();
    }

    public boolean containsAll(Point[] subGrid) {
        return Stream.of(subGrid).allMatch(this::contains);
    }

    public Point ltrNextPoint(Point current) {
        var next = current.move(Direction.E);
        if (contains(next)) {
            return next;
        }

        // Next row, start all the way to the left
        return new Point(0, current.y() + 1);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        var x = 0;
        var y = 0;
        sb.append("  ");
        for (var c : data[0]) {
            sb.append(x).append(" ");
            x++;
        }
        sb.append("\n");
        for (var row : data) {
            sb.append(y).append(" ");
            for (var c : row) {
                sb.append(c);
                sb.append(" ");
            }
            sb.append("\n");
            y++;
        }

        return sb.toString();
    }

    @Override
    public int width() {
        return data[0].length;
    }

    @Override
    public int height() {
        return data.length;
    }
}
