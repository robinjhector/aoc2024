package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Direction;
import com.jonssonhector.aoc.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Day10 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var grid = CharGrid.parse(input);

        System.out.println(grid);

        var starts = grid.findAll('0');
        var totalSum = 0L;
        for (Point start : starts) {
            var score = walk(grid, start, new ArrayList<>());
            totalSum += score;
        }

        return String.valueOf(totalSum);
    }

    @Override
    public String runPart2(String input) {
        var grid = CharGrid.parse(input);

        System.out.println(grid);

        var starts = grid.findAll('0');
        var totalSum = 0L;
        for (Point start : starts) {
            var score = walk(grid, start, null);
            totalSum += score;
        }

        return String.valueOf(totalSum);
    }

    // Return number of paths from this point to the end (9)
    private long walk(CharGrid grid, Point p, List<Point> seenNines) {
        var pathsFromHere = 0L;
        var c = grid.getInt(p);

        if (c == -1) {
            return 0L;
        }
        if (c == 9) {
            if (seenNines == null) {
                return 1L;
            } else if (seenNines.contains(p)) {
                return 0L;
            } else {
                seenNines.add(p);
                return 1L;
            }
        }

        var n = p.move(Direction.N);
        var e = p.move(Direction.E);
        var s = p.move(Direction.S);
        var w = p.move(Direction.W);

        if (grid.contains(n) && grid.getInt(n) == (c + 1)) {
            pathsFromHere += walk(grid, n, seenNines);
        }
        if (grid.contains(e) && grid.getInt(e) == (c + 1)) {
            pathsFromHere += walk(grid, e, seenNines);
        }
        if (grid.contains(s) && grid.getInt(s) == (c + 1)) {
            pathsFromHere += walk(grid, s, seenNines);
        }
        if (grid.contains(w) && grid.getInt(w) == (c + 1)) {
            pathsFromHere += walk(grid, w, seenNines);
        }

        return pathsFromHere;
    }
}
