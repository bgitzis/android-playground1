package com.gitzis.android.playground.app.obesrvables;

import android.widget.BaseAdapter;

/**
 * this is class follows adapter pattern to enable registering {@link BaseAdapter} observers
 * a hack.
 * 
 * @author bgitzis
 */
public class ViewDataSetObservable implements UsageAgnosticDataSetObservable {
    private BaseAdapter baseAdapter;

    public ViewDataSetObservable(BaseAdapter baseAdapter) {
        super();
        this.baseAdapter = baseAdapter;
    }

    @Override
    public void notifyChanged() {
        baseAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyInvalidated() {
        baseAdapter.notifyDataSetInvalidated();
    }
}
