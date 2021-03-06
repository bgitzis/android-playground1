package com.gitzis.android.playground.app;

import java.util.ArrayList;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.format.Time;

import com.gitzis.android.playground.app.model.Sample;
import com.gitzis.android.playground.app.model.SensorResult;
import com.gitzis.android.playground.app.obesrvables.UsageAgnosticDataSetObservable;

public class AccSampleCollector implements SensorEventListener {
    private static final int NUM_OF_EVENTS = 10;
    private Sensor accSensor;
    private SensorManager sensorManager;
    double collectionStart = System.nanoTime();
    private List<SensorResult> lastResults;
    private List<UsageAgnosticDataSetObservable> observerables;
    private Sample lastSample;

    public AccSampleCollector(SensorManager sensorManager) {
        super();
        this.lastResults = new ArrayList<SensorResult>();
        this.lastResults.add(new SensorResult(new float[] { 0.0f }, 0.0));
        this.lastSample = new Sample(Sensor.TYPE_ACCELEROMETER, now(), lastResults);
        this.observerables = new ArrayList<UsageAgnosticDataSetObservable>();
        this.sensorManager = sensorManager;
        this.accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        lastResults.add(new SensorResult(event.values, System.nanoTime() - collectionStart));
        if (lastResults.size() == NUM_OF_EVENTS) {
            sensorManager.unregisterListener(this);
            lastSample = new Sample(Sensor.TYPE_ACCELEROMETER, now(), lastResults);
            for (UsageAgnosticDataSetObservable observable : observerables) {
                observable.notifyChanged();
            }
        }
    }

    private Time now() {
        Time sampleTime = new Time();
        sampleTime.setToNow();
        return sampleTime;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public Sample getLastSample() {
        return lastSample;
    }

    public void collectSample() {
        lastResults.clear();
        collectionStart = System.nanoTime();
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void addObservable(UsageAgnosticDataSetObservable observable) {
        observerables.add(observable);
    }

}
