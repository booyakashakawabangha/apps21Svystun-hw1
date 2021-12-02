package ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

import static java.lang.Math.*;

public class TemperatureSeriesAnalysis {

    private double[] temperatureSeries = new double[2];
    int length = 0;
    final int LowestTemp = -273;

    public TemperatureSeriesAnalysis() {}

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        addTemps(temperatureSeries);
    }

    public double average() {
        checkIfEmpty();
        double total = 0;
        for (double temperature: temperatureSeries) {
            total += temperature;
        }
        return total / length;
    }

    public double deviation() {
        checkIfEmpty();
        double total = 0;
        double avg = average();
        for (double temperature: temperatureSeries) {total += pow((temperature - avg), 2);}
        return sqrt(total / length);
    }

    public double min() {
        checkIfEmpty();
        double local_min = temperatureSeries[0];
        for (double temperature: temperatureSeries) {
            if (temperature < local_min) {local_min = temperature;}
        }
        return local_min;
    }

    public double max() {
        checkIfEmpty();
        double local_max = temperatureSeries[0];
        for (double temperature: temperatureSeries) {
            if (temperature > local_max) {local_max = temperature;}
        }
        return local_max;
    }

    public double findTempClosestToZero() {
        return  findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        checkIfEmpty();
        int closest_idx = 0;
        double minimum_abs = abs(temperatureSeries[0] - tempValue);
        for (int idx = 1; idx < length; idx ++){
            double currentDifference = abs(temperatureSeries[idx] - tempValue);
            if (currentDifference < minimum_abs || (currentDifference == minimum_abs && temperatureSeries[idx] > 0)) {
                closest_idx = idx;
                minimum_abs = currentDifference;
            }
        }
        return temperatureSeries[closest_idx];
    }

    public double[] findTempsLessThen(double tempValue) {
        checkIfEmpty();
        int idx = 0;
        for (double temperature : temperatureSeries) {
            if (temperature < tempValue) {idx++;}
        }
        double[] fewerTemps = new double[idx];
        idx = 0;
        for (double temperature : temperatureSeries) {
            if (temperature < tempValue) {
                fewerTemps[idx++] = temperature;
            }
        }
        return fewerTemps;
    }

    public double[] findTempsGreaterThen(double tempValue) {
        checkIfEmpty();
        int idx = 0;
        for (double temperature : temperatureSeries) {
            if (temperature > tempValue) {idx++;}
        }
        double[] greaterTemps = new double[idx];
        idx = 0;
        for (double temperature : temperatureSeries) {
            if (temperature > tempValue) {
                greaterTemps[idx++] = temperature;
            }
        }
        return greaterTemps;
    }

    public TempSummaryStatistics summaryStatistics() {
        checkIfEmpty();
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        for (double temperature : temps) {
            if (temperature < LowestTemp) throw new InputMismatchException();
        }
        if (length + temps.length > temperatureSeries.length) {
            double[] newSeries = new double[2 * (temperatureSeries.length + temps.length)];
            for (int idx = 0; idx < temperatureSeries.length; idx++){newSeries[idx] = temperatureSeries[idx];}
            temperatureSeries = newSeries.clone();
        }

        for (int idx = 0; idx < temps.length; idx++) {
            temperatureSeries[idx + length] = temps[idx];
        }
        length += temps.length;
        return length;
    }

    private void checkIfEmpty(){
        if (length < 1) {
            throw new IllegalArgumentException();
        }
    }
}