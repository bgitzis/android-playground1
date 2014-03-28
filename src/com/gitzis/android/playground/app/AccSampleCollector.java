package com.gitzis.android.playground.app;

import java.util.ArrayList;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.BaseAdapter;

import com.gitzis.android.playground.app.model.SensorResult;

public class AccSampleCollector implements SensorEventListener {
    private static final int NUM_OF_EVENTS = 10;
    private Sensor accSensor;
    private SensorManager sensorManager;
    double collectionStart = System.nanoTime();
    private List<SensorResult> lastResults;
    private List<BaseAdapter> observers;

    //    private int throttleCount;

    public AccSampleCollector() {
        super();
        this.lastResults = new ArrayList<SensorResult>();
        this.observers = new ArrayList<BaseAdapter>();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //        if (throttleCount++ % 10 == 0) {
        lastResults.add(new SensorResult(event.values, System.nanoTime() - collectionStart));
        //        }
        if (lastResults.size() == NUM_OF_EVENTS) {
            sensorManager.unregisterListener(this);
            for (BaseAdapter observer : observers) {
                observer.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public List<SensorResult> getLastResults() {
        return lastResults;
    };

    public void collectSample() {
        lastResults.clear();
        collectionStart = System.nanoTime();
        //        throttleCount = 0;
        //        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_UI);
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
