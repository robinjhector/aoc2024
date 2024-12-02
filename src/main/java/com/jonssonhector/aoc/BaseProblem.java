package com.jonssonhector.aoc;

import java.util.function.Function;
import java.util.stream.Stream;

public abstract class BaseProblem {

    public abstract String runPart1(String input);

    public abstract String runPart2(String input);

    protected Stream<String> lines(String input) {
        return input.lines().filter(line -> !line.isBlank());
    }

    protected <T> Stream<T> splitLinesOnSpace(String input, Function<String[], T> fn) {
        return lines(input)
            .map(line -> line.split("\\s+"))
            .map(fn);
    }
}
