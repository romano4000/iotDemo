package com.example.demo.utils;

/**
 * The type Api constants.
 */
public class ApiConstants {

    /**
     * The constant MONGO_COLLECTION.
     */
    public static final String MONGO_COLLECTION = "ampliaDemo";

    /**
     * The constant QUEUE_NAME.
     */
    public static final String QUEUE_NAME = "iot";

    /**
     * The constant NUM_DATAPOINTS.
     */
    public static final int NUM_DATAPOINTS = 10;

    /**
     * The constant NUM_DATASTREAMS.
     */
    public static final int NUM_DATASTREAMS = 3;

    /**
     * The constant INTERVAL_SEND_MILISECONDS.
     */
    public static final int INTERVAL_SEND_MILISECONDS = 10000;

    // private constructor to avoid instantiation
    private ApiConstants() throws Exception {
        throw new InstantiationException("Class can't be instantiated");
    }
}
