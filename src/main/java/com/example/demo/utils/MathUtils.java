package com.example.demo.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;


/**
 * The type Math utils.
 */
public class MathUtils {

    /**
     * Calculate standard deviation
     *
     * @param values the values
     * @return the double
     */
    public double calculateStandarDeviation(double[] values) {
        StandardDeviation deviation = new StandardDeviation();
        return deviation.evaluate(values);
    }

    /**
     * Calculate minimum
     *
     * @param values the values
     * @return the double
     */
    public double calculateMinimum(double[] values) {
        return NumberUtils.min(values);
    }

    /**
     * Calculate maximum
     *
     * @param values the values
     * @return the double
     */
    public double calculateMaximum(double[] values) {
        return NumberUtils.max(values);
    }

    /**
     * Calculate mean
     *
     * @param values the values
     * @return the double
     */
    public double calculateMean(double[] values) {
        // Get a DescriptiveStatistics instance
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the array
        for (double value : values) {
            stats.addValue(value);
        }

        // Compute some statistics
        return stats.getMean();
    }

    /**
     * Calculate median
     *
     * @param values the values
     * @return the double
     */
    public double calculateMedian(double[] values) {
        // Get a DescriptiveStatistics instance
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // Add the data from the array
        for (double value : values) {
            stats.addValue(value);
        }

        // Compute some statistics
        return stats.getPercentile(50);
    }

    /**
     * Calculate quartiles
     *
     * @param values the values
     * @return the double
     */
    public double calculateQuartiles(double[] values) {

        int length = values.length;
        double lq = 0;

        if (length < 4) {
            lq = 0;
        } else if (length % 2 != 0) {
            lq = (values[(length / 4) - 1] + values[length / 4]) / 2;
        } else {
            lq = values[(length / 4) - 1];
        }

        return lq;
    }

    /**
     * Calculate mode
     *
     * @param values the values
     * @return the double
     */
    public double calculateMode(double[] values) {

        double maxValue = values[0];
        int maxCount = 0;
        for (double v : values) {
            int count = 0;
            for (double value : values) {
                if (value == v) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = v;
            }
        }
        return maxValue;

    }
}
