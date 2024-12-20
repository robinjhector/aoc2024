package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.AStar;
import com.jonssonhector.aoc.util.CharGrid;
import com.jonssonhector.aoc.util.Direction;

public class Day16 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var map = CharGrid.parse(input);
        var direction = Direction.E;

        var start = map.find('S');
        var end = map.find('E');

        var aStar = new AStar(map)
            .setImpassableChar('#')
            .setStepCost(1)
            .setTurnCost(1000);

        System.out.println(map);
        var result = aStar.findPath(start, end, direction);
        map.setAll(result.path(), '█');
        map.set(start, 'S');
        map.set(end, 'E');
        System.out.println(map);

        return String.valueOf(result.totalCost());
    }

    @Override
    public String runPart2(String input) {
        return "";
    }
}
