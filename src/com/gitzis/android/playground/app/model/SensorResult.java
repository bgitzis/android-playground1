package com.gitzis.android.playground.app.model;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class SensorResult {
    long id = -1;
    float[] values;
    double timeNanos;

    public SensorResult(float[] values, double timeNanos) {
        this.values = Arrays.copyOf(values, values.length);
        this.timeNanos = timeNanos;
    }

    @Override
    public String toString() {
        return String.format("%s | millis=%d", valuesToString(), TimeUnit.NANOSECONDS.toMillis(Math.round(timeNanos)));
    }

    private String valuesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(String.format("%2.2f", values[i])).append(i == values.length - 1 ? "" : " , ");
        }
        return sb.toString();
    }

    public float[] getValues() {
        return values;
    }

    public double getTimeNanos() {
        return timeNanos;
    }

    public long getId() {
        return id;
    }

    public SensorResult setId(long id) {
        this.id = id;
        return this;
    }

}