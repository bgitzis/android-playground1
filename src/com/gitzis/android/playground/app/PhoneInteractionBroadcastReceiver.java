package com.gitzis.android.playground.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.gitzis.android.playground.app.obesrvables.DbDataSetObservable;

public class PhoneInteractionBroadcastReceiver extends BroadcastReceiver {
    public PhoneInteractionBroadcastReceiver() {
        Log.w(PhoneInteractionBroadcastReceiver.class.getSimpleName(), "creating reciever for SCREEN_ON");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context, "SCREEN_ON.Collecting sample", Toast.LENGTH_SHORT);
        toast.setGravity(1, 0, 0);
        toast.show();
        Log.w(PhoneInteractionBroadcastReceiver.class.getSimpleName(), "SCREEN_ON.Collecting sample");
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        AccSampleCollector accSampleCollector = new AccSampleCollector(sensorManager);
        SamplesWriter observer = new SamplesWriter(accSampleCollector.getLastSample());
        DbDataSetObservable observable = new DbDataSetObservable().registerObserverFl(observer);
        accSampleCollector.addObservable(observable);
        accSampleCollector.collectSample();
    }

}
