package com.gitzis.android.playground.app.persistence;

import android.provider.BaseColumns;

public class SamplesTable implements BaseColumns {

    private SamplesTable() {
    }

    public static final String TABLE_NAME = "samples";

    public static final String DEFAULT_SORT_ORDER = "modified DESC";

    /*
     * columns
     */
    public static final String CMN_SENSOR = "sensor_type";
    public static final String CMN_SAMPLE_CREATE_DATE = "create_date";
    public static final String CMN_UPLOAD_DATE = "upload_date";
    public static final String CMN_X = "x";
    public static final String CMN_Y = "y";
    public static final String CMN_Z = "z";
    public static final String CMN_OFFSET_MILLIS = "offset_millis";
}
