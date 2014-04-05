package com.gitzis.android.playground.app.model;

import java.util.List;

import android.text.format.Time;

public class Sample {
    int sensorType;
    Time sampleDate;
    List<SensorResult> sensorResults;

    public Sample(int sensorType, Time sampleDate, List<SensorResult> sensorResults) {
        this.sensorType = sensorType;
        this.sampleDate = sampleDate;
        this.sensorResults = sensorResults;
    }

    public int getSensorType() {
        return sensorType;
    }

    public Time getSampleDate() {
        return sampleDate;
    }

    public List<SensorResult> getSensorResults() {
        return sensorResults;
    }

    @Override
    public String toString() {
        final int maxLen = 10;
        return String.format("Sample [sensorType=%s, sampleDate=%s, sensorResults=%s]", sensorType, sampleDate,
                sensorResults != null ? sensorResults.subList(0, Math.min(sensorResults.size(), maxLen)) : null);
    }

}
