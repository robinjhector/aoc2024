package com.jonssonhector.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day7 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var lines = splitLinesOnColon(input, Function.identity()).toList();

        var total = 0L;
        for (String[] line : lines) {
            var sum = Long.parseLong(line[0]);
            var components = line[1].trim();
            var permutations = permutate(components, Set.of("*", "+"));

            if (permutations.stream().anyMatch(perm -> sum == evaluate(perm))) {
                total += sum;
            }
        }

        return String.valueOf(total);
    }

    @Override
    public String runPart2(String input) {
        var lines = splitLinesOnColon(input, Function.identity()).toList();

        var total = 0L;
        for (String[] line : lines) {
            var sum = Long.parseLong(line[0]);
            var components = line[1].trim();
            var permutations = permutate(components, Set.of("*", "+", "|")).stream()
                .toList();

            if (permutations.stream().anyMatch(perm -> sum == evaluate(perm))) {
                total += sum;
            }
        }

        return String.valueOf(total);
    }

    private List<String> permutate(String spacedNumbers, Set<String> operators) {
        var permutations = new ArrayList<String>();
        var spacePositions = new ArrayList<Integer>();

        for (int i = 0; i < spacedNumbers.length(); i++) {
            if (spacedNumbers.charAt(i) == ' ') {
                spacePositions.add(i);
            }
        }

        // Generate permutations by replacing spaces with operators
        permuteHelper(spacedNumbers, operators, spacePositions, 0, permutations);
        return permutations;
    }

    private static void permuteHelper(
        String spacedNumbers,
        Set<String> operators,
        List<Integer> spacePositions,
        int index,
        List<String> permutations
    ) {
        // Base case: if all spaces have been replaced, add to results
        if (index == spacePositions.size()) {
            permutations.add(spacedNumbers);
            return;
        }

        // Replace the current space with each operator and recurse
        int spacePos = spacePositions.get(index);
        for (var operator : operators) {
            var modified = spacedNumbers.substring(0, spacePos) + operator + spacedNumbers.substring(spacePos + 1);
            permuteHelper(modified, operators, spacePositions, index + 1, permutations);
        }
    }

    private long evaluate(String stringEval) {
        var numbs = stringEval.split("[+*|]");
        var sum = Long.parseLong(numbs[0]);
        var opPos = numbs[0].length();

        for (int i = 1; i < numbs.length; i++) {
            var daNumb = numbs[i];
            var operator = stringEval.charAt(opPos);

            if (operator == '+') {
                sum += Long.parseLong(daNumb);
            } else if (operator == '*') {
                sum *= Long.parseLong(daNumb);
            } else if (operator == '|') {
                sum = Long.parseLong(sum + daNumb);
            }

            opPos += daNumb.length() + 1;
        }

        return sum;
    }
}
