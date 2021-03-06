package com.gitzis.android.playground.app;

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
import android.widget.Toast;

import com.gitzis.android.playground.app.analyzers.AccSumSampleAnalyzer;
import com.gitzis.android.playground.app.model.Sample;
import com.gitzis.android.playground.app.model.SensorResult;
import com.gitzis.android.playground.app.obesrvables.DbDataSetObservable;
import com.gitzis.android.playground.app.obesrvables.ViewDataSetObservable;

/**
 * A placeholder fragment containing a simple view.
 */
public class AccelerometerCollectorFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private AccSampleCollector accSampleCollector;
    private ListView accResultsView;
    private ArrayAdapter<SensorResult> resultsArrayAdapter;
    private CollectedDataUploader collectedDataUploader;
    private AccSumSampleAnalyzer sampleAnalyzer;

    public static AccelerometerCollectorFragment newInstance(int sectionNumber) {
        AccelerometerCollectorFragment fragment = new AccelerometerCollectorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SensorManager sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        this.accSampleCollector = new AccSampleCollector(sensorManager);
        this.collectedDataUploader = new CollectedDataUploader(activity);
        Sample lastSample = accSampleCollector.getLastSample();
        resultsArrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1,
                lastSample.getSensorResults());
        accSampleCollector.addObservable(new ViewDataSetObservable(resultsArrayAdapter));
        accSampleCollector.addObservable(new DbDataSetObservable().registerObserverFl(new SamplesWriter(lastSample)));
        this.sampleAnalyzer = new AccSumSampleAnalyzer();
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
        rootView.findViewById(R.id.analyzeButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), sampleAnalyzer.analyze().toString(), Toast.LENGTH_LONG).show();
            }
        });

        accResultsView = (ListView) rootView.findViewById(R.id.accDataListView);
        accResultsView.setAdapter(resultsArrayAdapter);
        return rootView;
    }

}