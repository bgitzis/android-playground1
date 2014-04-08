package com.gitzis.android.playground.app.model;

import android.text.format.Time;

public class AnalyzeResult {
    String analyzerName;
    Time sampleDate;
    String result;

    public AnalyzeResult(String analyzerName, Time sampleDate, String result) {
        super();
        this.analyzerName = analyzerName;
        this.sampleDate = sampleDate;
        this.result = result;
    }

    public String getAnalyzerName() {
        return analyzerName;
    }

    public Time getSampleDate() {
        return sampleDate;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return String.format("AnalyzeResult [analyzerName=%s, sampleDate=%s, result=%s]", analyzerName, sampleDate,
                result);
    }

}
