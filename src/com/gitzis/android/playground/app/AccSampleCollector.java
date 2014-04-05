package com.gitzis.android.playground.app;

import java.util.ArrayList;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.format.Time;
import android.widget.BaseAdapter;

import com.gitzis.android.playground.app.model.Sample;
import com.gitzis.android.playground.app.model.SensorResult;

public class AccSampleCollector implements SensorEventListener {
    private static final int NUM_OF_EVENTS = 10;
    private Sensor accSensor;
    private SensorManager sensorManager;
    double collectionStart = System.nanoTime();
    private List<SensorResult> lastResults;
    private List<BaseAdapter> observers;
    private Sample lastSample;

    public AccSampleCollector() {
        super();
        this.lastResults = new ArrayList<SensorResult>();
        lastResults.add(new SensorResult(new float[] { 0.0f }, 0.0));
        this.observers = new ArrayList<BaseAdapter>();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        lastResults.add(new SensorResult(event.values, System.nanoTime() - collectionStart));
        if (lastResults.size() == NUM_OF_EVENTS) {
            sensorManager.unregisterListener(this);
            lastSample = new Sample(Sensor.TYPE_ACCELEROMETER, now(), lastResults);
            for (BaseAdapter observer : observers) {
                observer.notifyDataSetChanged();
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

    public void initSensor(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        this.accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void addObserver(BaseAdapter adapter) {
        observers.add(adapter);
    }

}
