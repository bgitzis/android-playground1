package com.gitzis.android.playground.app.model;

import android.text.format.Time;

public class AnalyzeResult {
    long id = -1;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("AnalyzeResult \n id=%s \n analyzerName=%s \n sampleDate=%s \n result=%s", id,
                analyzerName, sampleDate.format2445(), result);
    }

}
