package com.jonssonhector.aoc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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

    public int getInt(Point point) {
        if (contains(point)) {
            var c = data[point.y()][point.x()];
            if (c >= '0' && c <= '9') {
                return c - '0';
            } else {
                return -1;
            }
        }

        throw new IllegalArgumentException("Point out of bounds: " + point);
    }

    public Point find(char c) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                if (data[y][x] == c) {
                    return new Point(x, y);
                }
            }
        }

        throw new IllegalArgumentException("Character not found: " + c);
    }

    public List<Point> findAll(char c) {
        var points = new ArrayList<Point>();
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                if (data[y][x] == c) {
                    points.add(new Point(x, y));
                }
            }
        }

        return points;
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

    public Map<Character, List<Point>> pointsPerCharacter() {
        var map = new HashMap<Character, List<Point>>();
        for (int y = 0; y < data.length; y++) {
            char[] row = data[y];
            for (int x = 0; x < row.length; x++) {
                char c = row[x];
                var points = map.computeIfAbsent(c, k -> new ArrayList<>());
                points.add(new Point(x, y));
            }
        }

        return map;
    }

    public void forEach(Consumer<Point> point) {
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                point.accept(new Point(x, y));
            }
        }
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

    public void set(Point point, char c) {
        if (contains(point)) {
            data[point.y()][point.x()] = c;
            return;
        }

        throw new IllegalArgumentException("Point out of bounds: " + point);
    }
}
