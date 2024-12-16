package com.jonssonhector.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day9 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var isFreeSpace = false;
        var blocks = new ArrayList<DiskBlock>();
        var idCounter = 0L;

        for (char c : input.trim().toCharArray()) {
            var digit = Character.getNumericValue(c);
            if (isFreeSpace) {
                blocks.add(new DiskBlock(-1, digit, true));
            } else {
                blocks.add(new DiskBlock(idCounter++, digit, false));
            }

            // Flip
            isFreeSpace = !isFreeSpace;
        }

        System.out.println(toStringChars(blocks));

        return "";
    }

    @Override
    public String runPart2(String input) {
        return "";
    }

    private StringBuilder toStringChars(List<DiskBlock> blocks) {
        var sb = new StringBuilder();
        for (DiskBlock block : blocks) {
            if (block.freeSpace) {
                sb.append(".".repeat(block.size));
            } else {
                sb.append(("[" + block.id + "]").repeat(block.size));
            }
        }

        return sb;
    }

    record DiskBlock(long id, int size, boolean freeSpace) {
    }
}
