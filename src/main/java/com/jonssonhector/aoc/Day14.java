package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Direction;
import com.jonssonhector.aoc.util.Point;

import java.util.TreeSet;

public class Day14 extends BaseProblem {

    class Robot {
        private Point pos;
        private Point vel;
        public Robot(Point pos, Point vel) {
            this.pos = pos;
            this.vel = vel;
        }
    }

    @Override
    public String runPart1(String input) {
        var robots = input.lines()
            .map(l -> {
                var spl = l.split("\\s+");
                var p = spl[0].substring(2);
                var v = spl[1].substring(2);
                return new Robot(Point.fromCsv(p), Point.fromCsv(v));
            })
            .toList();

        // real input vs test input
        var max = robots.size() > 20 ? new Point(101, 103) : new Point(11, 7);
        var iterations = 100;

        var visualize = CharGrid.fill(max.x(), max.y(), '.');
        for (var robot : robots) {
            var newX = robot.pos.x() + (robot.vel.x() * iterations);
            var newY = robot.pos.y() + (robot.vel.y() * iterations);
            robot.pos = new Point(newX, newY).wrapAround(max);
            visualize.set(robot.pos, '#');
        }

        System.out.println(visualize);
        var halfXLossy = max.x() / 2;
        var halfYLossy = max.y() / 2;

        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
        for (var simulatedRobot : robots) {
            var x = simulatedRobot.pos.x();
            var y = simulatedRobot.pos.y();
            if (x < halfXLossy && y < halfYLossy) {
                q1++;
            } else if (x > halfXLossy && y < halfYLossy) {
                q2++;
            } else if (x < halfXLossy && y > halfYLossy) {
                q3++;
            } else if (x > halfXLossy && y > halfYLossy) {
                q4++;
            }
        }

        return String.valueOf(q1 * q2 * q3 * q4);
    }

    @Override
    public String runPart2(String input) {
        var robots = input.lines()
            .map(l -> {
                var spl = l.split("\\s+");
                var p = spl[0].substring(2);
                var v = spl[1].substring(2);
                return new Robot(Point.fromCsv(p), Point.fromCsv(v));
            })
            .toList();

        // real input vs test input
        var max = new Point(101, 103);

        var iter = 0;
        var run = true;
        while (run) {
            var points = new TreeSet<>(Point.compareByYX());
            var visualize = CharGrid.fill(max.x(), max.y(), '.');
            for (var robot : robots) {
                var newX = robot.pos.x() + robot.vel.x();
                var newY = robot.pos.y() + robot.vel.y();
                var newPos = new Point(newX, newY).wrapAround(max);
                robot.pos = newPos;
                visualize.set(newPos, '#');
                points.add(newPos);
            }
            iter++;

            if (checkXmas(points)) {
                System.out.println(visualize.toCompactString());
                System.out.println("Iteration: " + iter);
                var x = System.console().readLine();
                if (x.equals("q")) {
                    run = false;
                }
            } else if (iter % 1000 == 0) {
                System.out.println("Iteration: " + iter);
            }
        }

        return String.valueOf(iter);
    }

    private boolean checkXmas(TreeSet<Point> robots) {
        var threshold = 6;
        var hits = 0;

        for (var robot : robots) {
            var n = robot.move(Direction.N);
            var s = robot.move(Direction.S);
            if (robots.contains(n) && robots.contains(s)) {
                hits++;
            }

            if (hits > threshold) {
                return true;
            }
        }

        return false;
    }
}
