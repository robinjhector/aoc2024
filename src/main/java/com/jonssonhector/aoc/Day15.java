package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Direction;
import com.jonssonhector.aoc.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Day15 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var spl = input.split("\\n\\n");
        var map = CharGrid.parse(spl[0]);
        var moves = spl[1].replace("\n", "");

        var botPos = map.find('@');
        for (var moveC : moves.toCharArray()) {
            var dir = cToDir(moveC);
            var next = botPos.move(dir);

            if (map.get(next) == '#') {
                // Walking into a wall, skip
                continue;
            } else if (map.get(next) == 'O') {
                // Box found, see if we can push it
                var extract = map.extractUntilNext(next, dir, '#');
                if (!extract.contains(".")) {
                    // No available space, not a valid move
                    continue;
                } else {
                    var compactedRange = compact(extract);
                    map.setRange(next, dir, compactedRange);
                    // Valid move, update bot position
                    map.set(botPos, '.');
                    map.set(next, '@');
                    botPos = next;
                }
            } else {
                // Valid move, update bot position
                map.set(botPos, '.');
                map.set(next, '@');
                botPos = next;
            }
        }

        var score = map.findAll('O').stream()
            .mapToLong(p -> 100L * p.y() + p.x())
            .sum();

        return String.valueOf(score);
    }

    @Override
    public String runPart2(String input) {
        var spl = input.split("\\n\\n");
        var tmpMap = CharGrid.parse(spl[0]);
        var map = CharGrid.fill(tmpMap.width() * 2, tmpMap.height(), '.');

        for (int y = 0; y < tmpMap.height(); y++) {
            for (int x = 0; x < tmpMap.width(); x++) {
                var copyPos1 = new Point(x * 2, y);
                var copyPos2 = new Point(x * 2 + 1, y);

                var c = tmpMap.get(new Point(x, y));
                switch (c) {
                    case 'O' -> {
                        map.set(copyPos1, '[');
                        map.set(copyPos2, ']');
                    }
                    case '@' -> map.set(copyPos1, '@');
                    case '#' -> {
                        map.set(copyPos1, '#');
                        map.set(copyPos2, '#');
                    }
                }
            }
        }

        var moves = spl[1].replace("\n", "");
        var botPos = map.find('@');
        for (var moveC : moves.toCharArray()) {
            var dir = cToDir(moveC);
            var next = botPos.move(dir);
            var nextC = map.get(next);

            if (nextC == '#') {
                // Walking into a wall, skip
                continue;
            } else {
                if (nextC == '[' || nextC == ']') {
                    if (dir.vertical()) {
                        var boxPos = next.dx(nextC == ']' ? -1 : 0);
                        var connectedBoxes = extractConnectedBoxes(map, boxPos, dir);
                        if (canPushCluster(map, connectedBoxes, dir)) {
                            pushCluster(map, connectedBoxes, dir);

                            map.set(botPos, '.');
                            map.set(next, '@');
                            botPos = next;
                        } else {
                            continue;
                        }
                    } else {
                        // Pushing the box horizontally works the same way as before.
                        var extract = map.extractUntilNext(next, dir, '#');
                        if (extract.contains(".")) {
                            var compactedRange = compact(extract);
                            map.setRange(next, dir, compactedRange);
                            // Valid move, update bot position
                            map.set(botPos, '.');
                            map.set(next, '@');
                            botPos = next;
                        }
                    }
                } else {
                    // Valid move, update bot position
                    map.set(botPos, '.');
                    map.set(next, '@');
                    botPos = next;
                }
            }
        }

        var score = map.findAll('[').stream()
            .mapToLong(p -> 100L * p.y() + p.x())
            .sum();

        return String.valueOf(score);
    }

    private ArrayList<Point> extractConnectedBoxes(CharGrid map, Point check, Direction dir) {
        var result = new ArrayList<Point>();
        result.add(check);
        var n1 = check.move(dir);
        var n1c = map.get(n1);
        var n2 = n1.move(Direction.E);
        var n2c = map.get(n2);
        /*
        4 scenarios to allow for:
        1.
          ...[]...
          ..[][]..
        2.
          ...[]...
          ..[]....
        3.
          ...[]...
          ....[]..
        4.
          ...[]...
          ...[]...
         */

        if (n1c == ']') {
            // scenario 1 & 2
            result.addAll(extractConnectedBoxes(map, n1.move(Direction.W), dir));
        } else if (n1c == '[') {
            // scenario 4
            result.addAll(extractConnectedBoxes(map, n1, dir));
        }

        // scenario 1 & 3
        if (n2c == '[') {
            result.addAll(extractConnectedBoxes(map, n2, dir));
        }

        return result;
    }

    private boolean canPushCluster(CharGrid map, List<Point> cluster, Direction dir) {
        for (Point point : cluster) {
            var next = point.move(dir);
            var next2 = next.move(Direction.E);
            if (map.get(next) == '#' || map.get(next2) == '#') {
                return false;
            }
        }

        return true;
    }

    private void pushCluster(CharGrid map, ArrayList<Point> cluster, Direction dir) {
        var dy = dir == Direction.N ? -1 : 1;

        // Clear previous box positions
        for (Point point : cluster) {
            map.set(point, '.');
            map.set(point.move(Direction.E), '.');
        }

        // Set the new positions
        for (var p : cluster) {
            map.set(p.dy(dy), '[');
            map.set(p.dy(dy).move(Direction.E), ']');
        }
    }

    private String compact(String extract) {
        // Shift "0"s to the right, removing the first "." found
        var dotIdx = extract.indexOf('.');
        var p1 = extract.substring(0, dotIdx);
        var p2 = extract.substring(dotIdx + 1);
        return "." + p1 + p2;
    }

    private Direction cToDir(char moveC) {
        return switch (moveC) {
            case '^' -> Direction.N;
            case 'v' -> Direction.S;
            case '<' -> Direction.W;
            case '>' -> Direction.E;
            default -> throw new IllegalArgumentException("Invalid move: \"%s\"".formatted(moveC));
        };
    }
}
