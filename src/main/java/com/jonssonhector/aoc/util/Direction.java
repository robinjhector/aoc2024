package com.jonssonhector.aoc.util;

public enum Direction {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;

    public Direction opposite() {
        return switch (this) {
            case N -> S;
            case NE -> SW;
            case E -> W;
            case SE -> NW;
            case S -> N;
            case SW -> NE;
            case W -> E;
            case NW -> SE;
        };
    }

    public Direction rotate90deg() {
        return switch (this) {
            case N -> E;
            case NE -> SE;
            case E -> S;
            case SE -> SW;
            case S -> W;
            case SW -> NW;
            case W -> N;
            case NW -> NE;
        };
    }
}
