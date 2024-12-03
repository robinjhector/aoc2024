package com.jonssonhector.aoc;

import java.util.regex.Pattern;

public class Day3 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var patt = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        var matcher = patt.matcher(input);

        var sum = 0;
        while (matcher.find()) {
            var match = matcher.group();
            var nums = match.substring(4, match.length() - 1).split(",");
            var a = Integer.parseInt(nums[0]);
            var b = Integer.parseInt(nums[1]);
            sum += a * b;
        }

        return String.valueOf(sum);
    }

    @Override
    public String runPart2(String input) {
        return "";
    }
}
