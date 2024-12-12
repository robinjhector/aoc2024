package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Direction;
import com.jonssonhector.aoc.util.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Day12 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var grid = CharGrid.parse(input);
        var totalValue = new AtomicLong();
        var visited = new HashSet<Point>();
        grid.forEach(p -> {
            if (visited.contains(p)) {
                return;
            }

            var expanded = expand(grid, p, visited);
            var circ = calcCirc(expanded);
            var val = circ * expanded.size();
            totalValue.addAndGet(val);
        });

        return String.valueOf(totalValue.get());
    }

    @Override
    public String runPart2(String input) {
        var grid = CharGrid.parse(input);
        var totalValue = new AtomicLong();
        var visited = new HashSet<Point>();

        grid.forEach(p -> {
            if (visited.contains(p)) {
                return;
            }

            var expanded = expand(grid, p, visited);
            var corners = countCorners(expanded);
            var val = corners * expanded.size();
            totalValue.addAndGet(val);
        });

        return String.valueOf(totalValue.get());
    }

    private int calcCirc(List<Point> area) {
        var totSides = 0;
        for (var point : area) {
            var sides = 4;
            for (var point1 : area) {
                if (point1.isAdjacentVH(point)) {
                    sides--;
                }
            }

            totSides += sides;
        }

        return totSides;
    }

    private int countCorners(List<Point> points) {
        var corners = 0;
        points.sort(Point.compareByYX());
        for (var point : points) {
            var n = point.move(Direction.N);
            var ne = point.move(Direction.NE);
            var e = point.move(Direction.E);
            var se = point.move(Direction.SE);
            var s = point.move(Direction.S);
            var sw = point.move(Direction.SW);
            var w = point.move(Direction.W);
            var nw = point.move(Direction.NW);

            var pc = 0;
            // Outside corners
            if (!points.contains(n) && !points.contains(e)) {
                pc++;
            }
            if (!points.contains(e) && !points.contains(s)) {
                pc++;
            }
            if (!points.contains(s) && !points.contains(w)) {
                pc++;
            }
            if (!points.contains(w) && !points.contains(n)) {
                pc++;
            }

            // Inside corners
            if (points.contains(e) && points.contains(s) && !points.contains(se)) {
                pc++;
            }
            if (points.contains(s) && points.contains(w) && !points.contains(sw)) {
                pc++;
            }
            if (points.contains(w) && points.contains(n) && !points.contains(nw)) {
                pc++;
            }
            if (points.contains(n) && points.contains(e) && !points.contains(ne)) {
                pc++;
            }

            corners += pc;
        }

        return corners;
    }

    private List<Point> expand(CharGrid grid, Point p, Set<Point> visited) {
        var points = new ArrayList<Point>();
        if (visited.contains(p)) {
            return points;
        }

        visited.add(p);
        points.add(p);

        var c = grid.get(p);
        var n = p.move(Direction.N);
        var e = p.move(Direction.E);
        var s = p.move(Direction.S);
        var w = p.move(Direction.W);
        if (grid.contains(n) && grid.get(n) == c) {
            points.addAll(expand(grid, n, visited));
        }
        if (grid.contains(e) && grid.get(e) == c) {
            points.addAll(expand(grid, e, visited));
        }
        if (grid.contains(s) && grid.get(s) == c) {
            points.addAll(expand(grid, s, visited));
        }
        if (grid.contains(w) && grid.get(w) == c) {
            points.addAll(expand(grid, w, visited));
        }

        return points;
    }
}
