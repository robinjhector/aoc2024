package com.jonssonhector.aoc;

import com.jonssonhector.aoc.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day5 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var split = input.split("\n\n");
        var rules1 = split[0];
        var pages1 = split[1];

        var rules = splitLinesOnPipe(rules1, toIntArray()).map(Point::fromIntArr).toList();
        var pages = splitLinesOnComma(pages1, toIntArray()).toList();
        var correctPages = new ArrayList<Integer[]>();

        for (Integer[] page : pages) {
            if (firstFailingRule(page, rules).isEmpty()) {
                correctPages.add(page);
            }
        }

        var midSum = 0;
        for (Integer[] page : correctPages) {
            var len = page.length;
            var middle = page[len / 2];
            midSum += middle;
        }

        return String.valueOf(midSum);
    }

    @Override
    public String runPart2(String input) {
        var split = input.split("\n\n");
        var rules1 = split[0];
        var pages1 = split[1];

        var rules = splitLinesOnPipe(rules1, toIntArray()).map(Point::fromIntArr).toList();
        var pages = splitLinesOnComma(pages1, toIntArray()).toList();
        var fixedPages = new ArrayList<Integer[]>();

        for (Integer[] page : pages) {
            var failedRule = firstFailingRule(page, rules);
            if (failedRule.isEmpty()) {
                continue;
            }

            while (failedRule.isPresent()) {
                var rule = failedRule.get();
                var xIndex = List.of(page).indexOf(rule.x());
                var yIndex = List.of(page).indexOf(rule.y());
                var temp = page[xIndex];
                page[xIndex] = page[yIndex];
                page[yIndex] = temp;
                failedRule = firstFailingRule(page, rules);
            }

            fixedPages.add(page);
        }

        var midSum = 0;
        for (Integer[] page : fixedPages) {
            var len = page.length;
            var middle = page[len / 2];
            midSum += middle;
        }

        return String.valueOf(midSum);
    }

    private Optional<Point> firstFailingRule(Integer[] page, List<Point> rules) {
        var pageList = List.of(page);
        for (Point rule : rules) {
            var isRuleApplicable = pageList.contains(rule.x()) && pageList.contains(rule.y());
            if (isRuleApplicable) {
                var isRuleCorrect = pageList.indexOf(rule.x()) < pageList.indexOf(rule.y());
                if (!isRuleCorrect) {
                    return Optional.of(rule);
                }
            }
        }

        return Optional.empty();
    }
}
