package com.jonssonhector.aoc;

public class Day2 extends BaseProblem {

    @Override
    public String runPart1(String input) {
        var reports = splitLinesOnSpace(input, toIntArray())
            .toList();

        var safe = 0;
        for (Integer[] report : reports) {
            if (isReportSafe(report)) {
                safe++;
            }
        }

        return String.valueOf(safe);
    }

    @Override
    public String runPart2(String input) {
        var reports = splitLinesOnSpace(input, toIntArray())
            .toList();

        var unsafeReports = reports.stream()
            .filter(report -> !isReportSafe(report))
            .toList();

        var safe = reports.size() - unsafeReports.size();

        for (Integer[] unsafeReport : unsafeReports) {
            boolean canBeMadeSafe = false;
            for (int i = 0; i < unsafeReport.length; i++) {
                // Create a new report without the i-th level
                var modifiedReport = new Integer[unsafeReport.length - 1];
                for (int j = 0, k = 0; j < unsafeReport.length; j++) {
                    if (j != i) {
                        modifiedReport[k++] = unsafeReport[j];
                    }
                }

                // Check if the modified report is safe
                if (isReportSafe(modifiedReport)) {
                    canBeMadeSafe = true;
                    break;
                }
            }

            if (canBeMadeSafe) {
                safe++;
            }
        }

        return String.valueOf(safe);
    }

    public boolean isReportSafe(Integer[] report) {
        var direction = 0;
        for (int i = 0; i < report.length - 1; i++) {
            var it = report[i];
            var it2 = report[i + 1];

            // Determine direction
            if (direction == 0) {
                if (it > it2) {
                    direction = -1;
                } else if (it < it2) {
                    direction = 1;
                } else {
                    // No direction, non-safe report, break straight away.
                    return false;
                }
            }

            if (direction == 1) {
                // We're increasing
                if (it > it2) {
                    // Suddenly decreasing, not safe.
                    return false;
                }

                var diff = it2 - it;
                if (diff == 0 || diff > 3) {
                    // Too big of a jump, not safe.
                    return false;
                }
            } else if (direction == -1) {
                if (it < it2) {
                    // Suddenly increasing, not safe.
                    return false;
                }

                var diff = it - it2;
                if (diff == 0 || diff > 3) {
                    // Too big of a jump, not safe.
                    return false;
                }
            }
        }

        return true;
    }
}
