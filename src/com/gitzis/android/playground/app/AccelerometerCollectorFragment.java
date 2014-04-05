package com.gitzis.android.playground.app;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gitzis.android.playground.app.model.SensorResult;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccelerometerCollectorFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private AccSampleCollector accSampleCollector;
    private ListView accResultsView;
    private ArrayAdapter<SensorResult> resultsArrayAdapter;
    private CollectedDataUploader collectedDataUploader;

    public static AccelerometerCollectorFragment newInstance(int sectionNumber) {
        AccelerometerCollectorFragment fragment = new AccelerometerCollectorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AccelerometerCollectorFragment() {
        this.accSampleCollector = new AccSampleCollector();
        this.collectedDataUploader = new CollectedDataUploader();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SensorManager sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accSampleCollector.initSensor(sensorManager);
        List<SensorResult> lastResults = accSampleCollector.getLastSample().getSensorResults();
        resultsArrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, lastResults);
        accSampleCollector.addObserver(resultsArrayAdapter);
        collectedDataUploader.setContext(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("Accelerometer Demo Fragment");

        rootView.findViewById(R.id.collectButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                accSampleCollector.collectSample();
            }
        });
        rootView.findViewById(R.id.uploadButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                collectedDataUploader.upload();
            }
        });

        accResultsView = (ListView) rootView.findViewById(R.id.accDataListView);
        accResultsView.setAdapter(resultsArrayAdapter);
        return rootView;
    }

}