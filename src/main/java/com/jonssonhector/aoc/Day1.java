package com.jonssonhector.aoc;

import java.util.Comparator;
import java.util.stream.Collectors;

public class Day1 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var pairs = splitLinesOnSpace(input, arr -> Pair.ints(arr[0], arr[1])).toList();

        var aOrdered = pairs.stream()
            .sorted(Comparator.comparing(Pair::a))
            .toList();
        var bOrdered = pairs.stream()
            .sorted(Comparator.comparing(Pair::b))
            .toList();

        var sum = 0;
        for (int i = 0; i < aOrdered.size(); i++) {
            var a = aOrdered.get(i).a();
            var b = bOrdered.get(i).b();

            sum += Math.abs(a - b);
        }

        return String.valueOf(sum);
    }

    @Override
    public String runPart2(String input) {
        var pairs = splitLinesOnSpace(input, arr -> Pair.ints(arr[0], arr[1])).toList();
        var bCount = pairs.stream().map(Pair::b).collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        var sum = 0;
        for (var pair : pairs) {
            var a = pair.a();
            var bC = bCount.getOrDefault(a, 0L);
            sum += (int) (a * bC);
        }

        return String.valueOf(sum);
    }
}
