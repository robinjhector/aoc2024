package com.jonssonhector.aoc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

    public static CharGrid fill(int x, int y, char c) {
        var data = new char[y][x];
        for (var row : data) {
            Arrays.fill(row, c);
        }

        return new CharGrid(data);
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

    public String extractUntilNext(Point starting, Direction direction, char... checkChars) {
        var sb = new StringBuilder();
        var point = starting;
        var found = false;
        var cc = new HashSet<Character>();
        for (var c : checkChars) {
            cc.add(c);
        }

        while (!found && contains(point)) {
            var c = get(point);
            if (cc.contains(c)) {
                found = true;
            } else {
                sb.append(c);
                point = point.move(direction);
            }
        }

        return sb.toString();
    }

    public void setRange(Point starting, Direction direction, String range) {
        var point = starting;
        for (var c : range.toCharArray()) {
            set(point, c);
            point = point.move(direction);
        }
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
        var xPadding = String.valueOf(width() - 1).length();
        var yPadding = String.valueOf(height() - 1).length();

        var sb = new StringBuilder();

        for (int row = 0; row < xPadding; row++) {
            sb.append(" ".repeat(yPadding + 1)); // Padding for the row indices (left side)
            for (int col = 0; col < width(); col++) {
                var colStr = String.valueOf(col);
                char c = (row < colStr.length()) ? colStr.charAt(row) : ' ';
                sb.append(c).append(" ");
            }
            sb.append("\n");
        }

        var y = 0;
        for (var row : data) {
            sb.append(y).append(" ".repeat(yPadding - String.valueOf(y).length() + 1));
            for (var c : row) {
                sb.append(c);
                sb.append(" ");
            }
            sb.append("\n");
            y++;
        }

        return sb.toString();
    }

    public String toCompactString() {
        var sb = new StringBuilder();
        for (var row : data) {
            for (var c : row) {
                sb.append(c);
            }
            sb.append("\n");
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

    public void setAll(Collection<Point> points, char c) {
        points.forEach(p -> set(p, c));
    }

    public void set(Point point, char c) {
        if (contains(point)) {
            data[point.y()][point.x()] = c;
            return;
        }

        throw new IllegalArgumentException("Point out of bounds: " + point);
    }
}
