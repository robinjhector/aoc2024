package com.jonssonhector.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        var freeSpace = blocks.stream()
            .filter(DiskBlock::freeSpace)
            .collect(Collectors.toCollection(ArrayList::new));
        var organized = new ArrayList<>(blocks);

        for (int i = blocks.size() - 1; i >= 0; i--) {
            if (freeSpace.isEmpty()) {
                // Out of free space
                break;
            }

            var targetPos = blocks.size() - i - 1;
            var block = blocks.get(i);
            var toMove = block.size();

            var freeBlock = freeSpace.getFirst();
            if (freeBlock.fitsExactly(block)) {
                organized.set(targetPos, freeBlock);
                freeSpace.removeFirst();
            } else {
                if (freeBlock.size() > toMove) {
                    organized.set(targetPos, new DiskBlock(block.id, toMove, false));
                    freeSpace.set(0, new DiskBlock(-1, freeBlock.size() - toMove, true));
                } else {
                    organized.set(targetPos, freeBlock);
                    freeSpace.removeFirst();
                }
            }
        }

        System.out.println(toStringChars(organized));
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
                sb.append(("" + block.id).repeat(block.size));
            }
        }

        return sb;
    }

    record DiskBlock(long id, int size, boolean freeSpace) {
        public boolean fitsExactly(DiskBlock otherBlock) {
            if (!freeSpace) {
                throw new IllegalArgumentException("This block is not free space");
            }

            return size == otherBlock.size;
        }
    }
}
