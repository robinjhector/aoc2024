package com.jonssonhector.aoc;

public record Pair<T>(T a, T b) {

    public static Pair<Integer> ints(int a, int b) {
        return new Pair<>(a, b);
    }

    public static Pair<Integer> ints(String a, String b) {
        return new Pair<>(Integer.parseInt(a), Integer.parseInt(b));
    }

}
