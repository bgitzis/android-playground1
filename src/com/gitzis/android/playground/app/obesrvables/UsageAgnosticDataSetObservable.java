package com.gitzis.android.playground.app.obesrvables;

/**
 * this is implementation of adapter pattern to enable registering observers related to View and DB
 * basically it is a hack to enable client using BaseAdapter instances and other observers, because BaseAdapter wrapps
 * its DataSetObserver member
 * 
 * @author bgitzis
 */
public interface UsageAgnosticDataSetObservable {

    public void notifyChanged();

    public void notifyInvalidated();

}