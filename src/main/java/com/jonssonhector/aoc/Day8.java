package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Point;

import java.util.HashSet;

public class Day8 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var grid = CharGrid.parse(input);
        var map = grid.pointsPerCharacter();

        var antiNodes = new HashSet<Point>();
        for (var c : map.keySet()) {
            if (c == '.') {
                continue;
            }

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
                        // Just for visualization
                        grid.set(p3, '#');
                        antiNodes.add(p3);
                    }
                }

            }
        }

        return String.valueOf(antiNodes.size());
    }

    @Override
    public String runPart2(String input) {
        var grid = CharGrid.parse(input);
        var map = grid.pointsPerCharacter();

        var antiNodes = new HashSet<Point>();
        for (var c : map.keySet()) {
            if (c == '.') {
                continue;
            }

            var points = map.get(c);
            if (points.size() > 1) {
                antiNodes.addAll(points);
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
                        // Just for visualization
                        if (grid.get(p3) == '.') {
                            grid.set(p3, '#');
                        }

                        antiNodes.add(p3);

                        p3 = new Point(
                            p3.x() + (p2.x() - p1.x()),
                            p3.y() + (p2.y() - p1.y())
                        );
                    }
                }

            }
        }

        return String.valueOf(antiNodes.size());
    }
}
