package com.jonssonhector.aoc;

public class Day4 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var grid = CharGrid.parse(input);

        var point = Point.ZERO;

        var xmas = 0;
        while (grid.contains(point)) {
            var c = grid.get(point);

            if (c == 'X') {
                // Start of XMAS?
                for (var direction : Direction.values()) {
                    // Explore all directions
                    var word = grid.extractRange(point, direction, 4);
                    if (word.equals("XMAS")) {
                        xmas++;
                    }
                }
            }

            point = grid.ltrNextPoint(point);
        }

        return String.valueOf(xmas);
    }

    @Override
    public String runPart2(String input) {
        var grid = CharGrid.parse(input);
        var point = Point.ZERO;

        var xmas = 0;
        while (grid.contains(point)) {
            var c = grid.get(point);

            if (c == 'A') {
                // A is always in the middle of an X-MAS
                // Also check if the surrounding 3x3 is inside the grid
                if (grid.containsAll(point.adj3x3())) {
                    // Top left to bottom right diagonal should be either MAS or SAM
                    var nwPoint = point.move(Direction.NW);
                    var diagLtr = grid.extractRange(nwPoint, Direction.SE, 3);
                    var diagLtrMas = diagLtr.equals("MAS") || diagLtr.equals("SAM");

                    // Top right to bottom left diagonal should be either MAS or SAM
                    var nePoint = point.move(Direction.NE);
                    var diagRtl = grid.extractRange(nePoint, Direction.SW, 3);
                    var diagRtlMas = diagRtl.equals("MAS") || diagRtl.equals("SAM");

                    // If both diagonals are MAS or SAM, we have an X-MAS
                    if (diagRtlMas && diagLtrMas) {
                        xmas++;
                    }
                }
            }

            point = grid.ltrNextPoint(point);
        }

        return String.valueOf(xmas);
    }
}
