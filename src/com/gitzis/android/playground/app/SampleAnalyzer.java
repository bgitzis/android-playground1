package com.gitzis.android.playground.app;

import com.gitzis.android.playground.app.model.AnalyzeResult;
import com.gitzis.android.playground.app.model.Sample;

public interface SampleAnalyzer {

    public abstract AnalyzeResult analyze(Sample sample);

}