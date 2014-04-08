package com.gitzis.android.playground.app.obesrvables;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

public class DbDataSetObservable extends DataSetObservable implements UsageAgnosticDataSetObservable {

    /**
     * fluent API for registering observers.
     * 
     * @param observer to register
     * @return this
     */
    public DbDataSetObservable registerObserverFl(DataSetObserver observer) {
        super.registerObserver(observer);
        return this;
    }
}
