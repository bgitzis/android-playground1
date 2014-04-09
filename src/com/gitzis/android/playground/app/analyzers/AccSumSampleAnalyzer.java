package com.gitzis.android.playground.app.analyzers;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.text.format.Time;
import android.util.Log;

import com.gitzis.android.playground.app.SampleAnalyzer;
import com.gitzis.android.playground.app.model.AnalyzeResult;
import com.gitzis.android.playground.app.model.Sample;
import com.gitzis.android.playground.app.model.SensorResult;
import com.gitzis.android.playground.app.persistence.AnalysisResultsDao;
import com.gitzis.android.playground.app.persistence.MyDbHelper;
import com.gitzis.android.playground.app.persistence.SamplesDao;
import com.gitzis.android.playground.app.persistence.SamplesDao.SamplesColumns;

public class AccSumSampleAnalyzer implements SampleAnalyzer {
    public static final AnalyzeResult NO_SAMPLES_ANALYZE_RESULT = new AnalyzeResult("NO_RESULT", new Time(), "0");
    SamplesDao samplesDao = new SamplesDao(MyDbHelper.getINSTANCE());
    AnalysisResultsDao analysisResultsDao = new AnalysisResultsDao(MyDbHelper.getINSTANCE());

    //FIXME: delete this, it is just for demo
    public AnalyzeResult analyze() {
        Cursor cursor = samplesDao.getRowsToUpload();
        if (!cursor.moveToFirst()) {
            Log.w(AccSumSampleAnalyzer.class.getSimpleName(), "nothing to analyze");
            return NO_SAMPLES_ANALYZE_RESULT;
        }
        String createDate = cursor.getString(cursor.getColumnIndex(SamplesColumns.CMN_SAMPLE_CREATE_DATE));
        int sensorType = cursor.getInt(cursor.getColumnIndex(SamplesColumns.CMN_SENSOR_TYPE));
        List<SensorResult> sensorResults = new ArrayList<>();
        Sample sample = new Sample(sensorType, newTime(createDate), sensorResults);
        sample.addSensorResult(SamplesDao.mapSensorResult(cursor));
        int rowCounter = 1;
        while (cursor.moveToNext()) {
            rowCounter++;
            sample.addSensorResult(SamplesDao.mapSensorResult(cursor));
        }
        Log.i(AccSumSampleAnalyzer.class.getSimpleName(), "Analyzed " + rowCounter + " SensorResults.");
        AnalyzeResult analyzeResult = analyze(sample);
        analysisResultsDao.insert(analyzeResult);
        for (SensorResult sensorResult : sensorResults) {
            samplesDao.delete(sensorResult.getId());
        }
        return analyzeResult;
    }

    private Time newTime(String createDate) {
        Time time = new Time();
        time.parse(createDate);
        time.normalize(false);
        return time;
    }

    @Override
    public AnalyzeResult analyze(Sample sample) {
        float result = 0;
        for (SensorResult sensorResult : sample.getSensorResults()) {
            result += sum(sensorResult.getValues());
        }
        return new AnalyzeResult(AccSumSampleAnalyzer.class.getSimpleName(), sample.getSampleDate(),
                Float.toString(result));
    }

    private float sum(float[] values) {
        float result = 0;
        for (float f : values) {
            result += f;
        }
        return result;
    }
}
