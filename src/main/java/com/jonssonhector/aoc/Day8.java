package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day8 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var grid = CharGrid.parse(input);
        var map = new HashMap<Character, List<Point>>();

        for (int y = 0; y < grid.data().length; y++) {
            char[] row = grid.data()[y];
            for (int x = 0; x < row.length; x++) {
                char c = row[x];
                if (c == '.' || map.containsKey(c)) {
                    continue;
                }

                map.put(c, grid.findAll(c));
            }
        }

        var antinodes = new HashSet<Point>();
        for (var c : map.keySet()) {
            var points = map.get(c);
            for (var p1 : points) {
                for (var p2 : points) {
                    if (p1.equals(p2)) {
                        continue;
                    }

                    var p3 = new Point(
                        p2.x() + (p2.x() - p1.x()),
                        p2.y() + (p2.y() - p1.y())
                    );

                    if (grid.contains(p3)) {
                        grid.set(p3, '#');
                        antinodes.add(p3);
                    }
                }

            }
        }

        return String.valueOf(antinodes.size());
    }

    @Override
    public String runPart2(String input) {
        var grid = CharGrid.parse(input);
        var map = new HashMap<Character, List<Point>>();

        for (int y = 0; y < grid.data().length; y++) {
            char[] row = grid.data()[y];
            for (int x = 0; x < row.length; x++) {
                char c = row[x];
                if (c == '.' || map.containsKey(c)) {
                    continue;
                }

                map.put(c, grid.findAll(c));
            }
        }

        var antinodes = new HashSet<Point>();
        for (var c : map.keySet()) {
            var points = map.get(c);
            if (points.size() > 1) {
                antinodes.addAll(points);
            }
            for (var p1 : points) {
                for (var p2 : points) {
                    if (p1.equals(p2)) {
                        continue;
                    }

                    var p3 = new Point(
                        p2.x() + (p2.x() - p1.x()),
                        p2.y() + (p2.y() - p1.y())
                    );

                    while (grid.contains(p3)) {
                        if (grid.get(p3) == '.') {
                            grid.set(p3, '#');
                        }

                        antinodes.add(p3);

                        p3 = new Point(
                            p3.x() + (p2.x() - p1.x()),
                            p3.y() + (p2.y() - p1.y())
                        );
                    }
                }

            }
        }

        return String.valueOf(antinodes.size());
    }
}
