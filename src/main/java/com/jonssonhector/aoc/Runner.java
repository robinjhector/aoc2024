package com.jonssonhector.aoc;

import java.io.InputStream;
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
            try (var resource = readFile(file)) {
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
            """, day, fromCli ? "From CLI" : "From file");

        var dayInstance = clazz.getDeclaredConstructor().newInstance();
        var s = System.currentTimeMillis();

        var p1Output = dayInstance.runPart1(input);
        var p1Time = System.currentTimeMillis() - s;
        System.out.printf("| Part 1 (took %dms): %s%n".formatted(p1Time, out(p1Output)));
        var p2Output = dayInstance.runPart2(input);
        var p2Time = System.currentTimeMillis() - s - p1Time;
        System.out.printf("| Part 2 (took %dms): %s%n".formatted(p2Time, out(p2Output)));
        System.out.printf("| Total time taken: %dms%n", p1Time + p2Time);
        System.out.println("----------------------");
    }

    private static InputStream readFile(String file) {
        var is = Runner.class.getResourceAsStream(file);
        if (is != null) {
            return is;
        }

        // Attempt to load from src/main/resources
        return Runner.class.getClassLoader().getResourceAsStream("src/main/resources" + file);
    }

    private static String out(String output) {
        return output == null || output.isBlank() ? "(No output)" : output;
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
