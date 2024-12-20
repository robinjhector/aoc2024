package com.jonssonhector.aoc.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {

    private final CharGrid grid;
    private final PriorityQueue<Node> openSet = new PriorityQueue<>();
    private final Set<Point> visited = new HashSet<>();
    private final Map<Point, Integer> costMap = new HashMap<>();

    private char impassableChar = '#';
    private int stepCost = 1;
    private int turnCost = 0;

    public AStar(CharGrid grid) {
        this.grid = grid;
    }

    public AStar setImpassableChar(char impassableChar) {
        this.impassableChar = impassableChar;
        return this;
    }

    public AStar setStepCost(int stepCost) {
        this.stepCost = stepCost;
        return this;
    }

    public AStar setTurnCost(int turnCost) {
        this.turnCost = turnCost;
        return this;
    }

    public Result findPath(Point start, Point end, Direction initialDirection) {
        // Reset
        openSet.clear();
        visited.clear();
        costMap.clear();

        // Start
        openSet.add(Node.start(start, start.distance(end), initialDirection));
        costMap.put(start, 0);
        while (!openSet.isEmpty()) {
            var current = openSet.poll();

            if (current.point().equals(end)) {
                return reconstructPath(current);
            }

            visited.add(current.point());

            var possibleDirections = new Direction[] {
                current.direction(),
                current.direction().turn90Left(),
                current.direction().turn90Right()
            };

            for (var dir : possibleDirections) {
                var targetPoint = current.point().move(dir);

                if (!grid.contains(targetPoint)) {
                    // Impossible move, skip.
                    continue;
                } else if (grid.get(targetPoint) == impassableChar) {
                    // Impassable terrain, skip.
                    continue;
                } else if (visited.contains(targetPoint)) {
                    // Already visited, skip.
                    continue;
                }

                var isTurn = dir != current.direction();
                var newCostFromStart = current.costFromStart() + stepCost + (isTurn ? turnCost : 0);
                var newCostToEnd = targetPoint.distance(end);

                if (costMap.containsKey(targetPoint) && newCostFromStart >= costMap.get(targetPoint)) {
                    // We already have a better path to this point.
                    continue;
                }

                costMap.put(targetPoint, newCostFromStart);
                openSet.add(new Node(targetPoint, current, newCostFromStart, newCostToEnd, dir));
            }
        }

        return new Result(List.of(), -1);
    }

    public record Result(List<Point> path, int totalCost) {
    }

    private Result reconstructPath(Node node) {
        var path = new ArrayList<Point>();
        var current = node;
        var currentDirection = node.direction();
        var cost = 0;

        while (current != null) {
            path.add(current.point());
            current = current.parent();
            if (current != null) {
                var isTurn = currentDirection != current.direction();
                cost += stepCost + (isTurn ? turnCost : 0);
                currentDirection = current.direction();
            }
        }

        Collections.reverse(path);

        return new Result(path, cost);
    }

    // Equal only on the point, not the cost.
    private record Node(
        Point point,
        Node parent,
        int costFromStart,
        int costToEnd,
        Direction direction
    ) implements Comparable<Node> {

        public static Node start(Point p, int costToEnd, Direction direction) {
            return new Node(p, null, 0, costToEnd, direction);
        }

        public int getCost() {
            return costFromStart + costToEnd;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.getCost(), other.getCost());
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node node)) {
                return false;
            }
            return Objects.equals(point, node.point);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(point);
        }
    }
}
