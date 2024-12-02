package com.jonssonhector.aoc;

import java.nio.charset.StandardCharsets;

public class Runner {

    public static void main(String[] args) throws Exception {
        if (args.length < 1 || args.length > 2) {
            throw new IllegalArgumentException("Expected 1, or 2 arguments, got %d".formatted(args.length));
        }

        var day = Integer.parseInt(args[0]);
        var input = args.length == 2 ? args[1] : null;
        var fromCli = input != null;

        if (input == null) {
            var file = "/day%d.txt".formatted(day);
            try (var resource = Runner.class.getResourceAsStream(file)) {
                if (resource == null) {
                    throw new IllegalArgumentException("Could not find resource %s".formatted(file));
                }
                input = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
            }
        }

        var clazz = loadClazz(day);

        System.out.printf("""
            ----------------------
            |  Running (Day %d)  |
            ----------------------
            | Data: %s
            ----------------------
            """, day, fromCli ? "From CLI" : "From file");

        var dayInstance = clazz.getDeclaredConstructor().newInstance();
        var s = System.currentTimeMillis();
        var output = dayInstance.run(input);
        var time = System.currentTimeMillis() - s;

        System.out.printf("""
            ---------------------
            |  Output (Day %d)  |
            ---------------------
            Part1: %s
            Part2: %s
            Time taken: %dms
            ---------------------
            """,
            day,
            output.part1(),
            output.part2(),
            time
        );
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends BaseProblem> loadClazz(int day) {
        try {
            return (Class<? extends BaseProblem>) Class.forName("com.jonssonhector.aoc.Day%d".formatted(day));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Not yet implemented: Day %d".formatted(day));
        }
    }
}
