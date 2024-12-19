package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Direction;

public class Day16 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var map = CharGrid.parse(input);
        var direction = Direction.E;

        var start = map.find('S');
        var end = map.find('E');
        var minScore = Integer.MAX_VALUE;
        var score = 0;

        var currPoint = start;
//        while (true) {
//            var next = map.get(direction.move());
//            if (next == ' ') {
//                break;
//            }
//
//            if (next == '+') {
//                direction = direction.turn(map.get(direction.turnLeft()) == ' ');
//            } else if (next >= 'A' && next <= 'Z') {
//                score++;
//            }
//
//            score++;
//        }

        return "";
    }

    @Override
    public String runPart2(String input) {
        return "";
    }
}
