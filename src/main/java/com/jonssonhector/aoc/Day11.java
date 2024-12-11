package com.jonssonhector.aoc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 extends BaseProblem {

    /*
    Rules:
    - If 0 -> turn into a 1
    - If even amount of digits = split into two stones (in the middle)
    - Else, multiply the value with 2024
     */

    @Override
    public String runPart1(String input) {
        var stones = Stream.of(input.split("\\s"))
            .collect(Collectors.toMap(
                Long::parseLong,
                x -> 1L
            ));

        for (int i = 0; i < 25; i++) {
            stones = blink(stones);
        }

        return stones.values().stream().mapToLong(Long::longValue).sum() + "";
    }

    @Override
    public String runPart2(String input) {
        var stones = Stream.of(input.split("\\s"))
            .collect(Collectors.toMap(
                Long::parseLong,
                x -> 1L
            ));

        for (int i = 0; i < 75; i++) {
            stones = blink(stones);
        }

        return stones.values().stream().mapToLong(Long::longValue).sum() + "";
    }

    public static Map<Long, Long> blink(Map<Long, Long> stones) {
        var newStones = new HashMap<Long, Long>();

        for (var entry : stones.entrySet()) {
            long stone = entry.getKey();
            long count = entry.getValue();

            if (stone == 0) {
                newStones.put(1L, newStones.getOrDefault(1L, 0L) + count);
            } else if (String.valueOf(stone).length() % 2 == 0) {
                String s = String.valueOf(stone);
                String lh = s.substring(0, s.length() / 2);
                String rh = s.substring(s.length() / 2);
                long leftHalf = Long.parseLong(lh);
                long rightHalf = Long.parseLong(rh);
                newStones.put(leftHalf, newStones.getOrDefault(leftHalf, 0L) + count);
                newStones.put(rightHalf, newStones.getOrDefault(rightHalf, 0L) + count);
            } else {
                long newStone = stone * 2024;
                newStones.put(newStone, newStones.getOrDefault(newStone, 0L) + count);
            }
        }

        return newStones;
    }
}
