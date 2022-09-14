package com.example.demo.utils;

import com.example.demo.model.DatapointCollectionDTO;
import com.example.demo.model.DatapointDTO;
import com.example.demo.model.DatastreamDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Mock data.
 */
@Slf4j
public class MockData {

    /**
     * The Rand.
     */
    Random rand = new Random();

    /**
     * Generates message with the corresponding format with random values
     *
     * @return the new message
     */
    public DatapointCollectionDTO getNewMessage()
    {
        // generate a new rabbitMQ message with random values
        DatapointCollectionDTO message = new DatapointCollectionDTO();

        // fill only mandatory fields
        message.setVersion("1.0.0");

        // generate new dataStream
        List<DatastreamDTO> dataStreams = new ArrayList<>();

        int i;
        int numDataStreams = ApiConstants.NUM_DATASTREAMS;
        log.info("Generating {} dataStreams", numDataStreams);
        for (i = 0; i < numDataStreams; i++) {
            dataStreams.add(generateRandomDatastream());
        }

        // finish filling message data
        message.setDatastreams(dataStreams);

        return message;
    }

    private DatastreamDTO generateRandomDatastream(){

        DatastreamDTO dataStream = new DatastreamDTO();

        dataStream.setId("example");

        // generate new datapoints
        List<DatapointDTO> datapoints = new ArrayList<>();

        int i;
        int numDatapoints = ApiConstants.NUM_DATAPOINTS;
        log.info("Generating {} datapoints", numDatapoints);
        for (i = 0; i < numDatapoints; i++) {
            datapoints.add(generateRandomDatapoint());
        }

        dataStream.setDatapoints(datapoints);

        return dataStream;
    }

    private DatapointDTO generateRandomDatapoint()
    {
        DatapointDTO datapoint = new DatapointDTO();

        // generate a random number between 0 and 10
        datapoint.setValue(rand.nextFloat() * 100);

        return datapoint;
    }
}
