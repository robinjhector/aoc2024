package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Direction;
import com.jonssonhector.aoc.util.Point;

import java.util.HashSet;

public class Day6 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var grid = CharGrid.parse(input);
        var guard = grid.find('^');
        var direction = Direction.N;
        var distinctVisits = new HashSet<Point>();

        while (grid.contains(guard)) {
            var onObstacle = grid.get(guard) == '#';

            if (onObstacle) {
                // Step back once, we can't be on an obstacle.
                guard = guard.move(direction.opposite());
                // Rotate 90deg
                direction = direction.rotate90deg();
            } else {
                // We took a safe step, so store it in the set.
                distinctVisits.add(guard);
                // Keep moving in the same direction
                guard = guard.move(direction);
            }
        }

        return String.valueOf(distinctVisits.size());
    }

    @Override
    public String runPart2(String input) {
        var grid = CharGrid.parse(input);
        var guardStartPos = grid.find('^');


        var pos = 0;
        for (int y = 0; y < grid.data().length; y++) {
            char[] row = grid.data()[y];
            for (int x = 0; x < row.length; x++) {
                var c = row[x];
                if (c != '.') {
                    continue;
                }

                // Start simulation from this point
                // Put down an obstacle to simluate
                grid.data()[y][x] = '#';
                var guard = guardStartPos;
                var direction = Direction.N;
                var loopDetector = new HashSet<PointAndDirection>();

                while (grid.contains(guard)) {
                    var onObstacle = grid.get(guard) == '#';

                    if (onObstacle) {
                        // Step back once, we can't be on an obstacle.
                        guard = guard.move(direction.opposite());
                        // Rotate 90deg
                        direction = direction.rotate90deg();
                    } else {
                        // We took a safe step, so store it in the set.
                        var isInLoop = loopDetector.add(new PointAndDirection(guard, direction));
                        if (!isInLoop) {
                            pos++;
                            break;
                        }
                        // Keep moving in the same direction
                        guard = guard.move(direction);
                    }
                }

                // Reset grid
                grid.data()[y][x] = '.';
            }
        }

        return String.valueOf(pos);
    }

    record PointAndDirection(Point point, Direction direction) {

    }
}
