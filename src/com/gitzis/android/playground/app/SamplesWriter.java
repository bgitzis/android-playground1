package com.gitzis.android.playground.app;

import android.database.DataSetObserver;

import com.gitzis.android.playground.app.model.Sample;
import com.gitzis.android.playground.app.persistence.MyDbHelper;
import com.gitzis.android.playground.app.persistence.SamplesDao;

public class SamplesWriter extends DataSetObserver {
    private Sample sample;
    private SamplesDao dao;

    public SamplesWriter(Sample sample) {
        this.sample = sample;
        this.dao = new SamplesDao(MyDbHelper.getINSTANCE());
    }

    @Override
    public void onChanged() {
        dao.insert(sample);
    }

}
