package com.korostel.counters.data;

import android.provider.BaseColumns;

/**
 * Created by korostel on 07.09.14.
 */

public class CountersContract {

    public static final String DB_NAME = "CountersDB";
    public static final int DB_VERSION = 1;


    public class CountersEntry implements BaseColumns {

        public static final String TABLE_NAME = "counters";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COUNT_INT_BITS = "count_int_bits";
        public static final String COLUMN_COUNT_FRAC_BITS = "count_dec_bits";
        public static final String COLUMN_UNITS_MEASURE = "units_measure";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_CURRENCY = "currency";

    }

    public class ValuesEntry implements BaseColumns {

        public static final String TABLE_NAME = "indications";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_INDICATION = "indication";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_COUNTER_ID = "counter_id";
    }
}
