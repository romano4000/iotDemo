package com.example.demo.service;

import com.example.demo.entity.MongoData;
import com.example.demo.model.DatapointCollectionDTO;
import com.example.demo.model.DatapointDTO;
import com.example.demo.model.DatastreamDTO;
import com.example.demo.utils.ApiConstants;
import com.example.demo.utils.MathUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * The type RabbitMQ receiver.
 * This class receives and process messages stored in rabbitMQ queue
 */
@Slf4j
@Component
public class RabbitMQReceiver {

    /**
     * The Math utils.
     * Used to calculate the different statistical parameters
     */
    MathUtils mathUtils = new MathUtils();

    @Autowired
    private DemoService service;

    @Value("${spring.rabbitmq.host}")
    private String host;


    /**
     * Retrieve data from queue.
     * Retrieves and process data stored in a rabbitMQ queue
     * The process consist of calculating statistical data and store this calculations in mongodb
     *
     * @throws IOException      the io exception
     * @throws TimeoutException the timeout exception
     */
    // launch it once at the beginning, the thread will keep listening for messages
    @PostConstruct
    public void retrieveDataFromQueue() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);

        // open connection
        Connection connection = factory.newConnection();

        // open channel
        Channel channel = connection.createChannel();

        // declare the queue from which we are going to consume the data
        channel.queueDeclare(ApiConstants.QUEUE_NAME, false, false, false, null);

        log.info(" [*] Waiting for messages...");

        // define callback to be called
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            log.info(" [x] Received message = {}", message);

            // retrieve and process values contained in message received
            retrieveValuesFromMessage(message);
        };

        // retrieve messages from queue
        channel.basicConsume(ApiConstants.QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }

    private void retrieveValuesFromMessage(String message) throws JsonProcessingException {

        // generate list of float
        List<Double> values;

        // map message to object
        ObjectMapper mapper = new ObjectMapper();
        DatapointCollectionDTO messageMapped = mapper.readValue(message, DatapointCollectionDTO.class);

        // retrieve values from message
        int i;
        for(i = 0; i < messageMapped.getDatastreams().size(); i++) {
            DatastreamDTO dataStream = messageMapped.getDatastreams().get(i);

            // retrieve values from dataStream
            values = retrieveValuesFromDatastream(dataStream);

            log.info("Values from dataStream retrieved = {}", values);

            // create object to save data in database
            MongoData dataToStore = calculateData(values);

            // sava data in database
            service.saveData(dataToStore);
        }
    }


    private List<Double> retrieveValuesFromDatastream(DatastreamDTO dataStream) {

        // generate list of float
        List<Double> values = new ArrayList<>();

        // retrieve values from message
        int i;
        for (i = 0; i < dataStream.getDatapoints().size(); i++) {
            DatapointDTO dataPoint = dataStream.getDatapoints().get(i);

            // add value to list
            values.add(dataPoint.getValue());
        }

        return values;
    }

    private MongoData calculateData(List<Double> messageValues)
    {
        // sort data
        Collections.sort(messageValues);

        // convert list of values to array
        double[] valuesArray = messageValues.stream().mapToDouble(Double::doubleValue).toArray();

        // create new mongo data
        MongoData newDataToStore = new MongoData();

        // set current date as creation date
        newDataToStore.setCreationDate(new Date());

        // standard deviation
        newDataToStore.setDesvTipica(mathUtils.calculateStandarDeviation(valuesArray));

        // minimum
        newDataToStore.setMinimo(mathUtils.calculateMinimum(valuesArray));

        // maximum
        newDataToStore.setMaximo(mathUtils.calculateMaximum(valuesArray));

        // mean
        newDataToStore.setMedia(mathUtils.calculateMean(valuesArray));

        // median
        newDataToStore.setMediana(mathUtils.calculateMedian(valuesArray));

        // mode
        newDataToStore.setModa(mathUtils.calculateMode(valuesArray));

        // quartile
        newDataToStore.setCuartil(mathUtils.calculateQuartiles(valuesArray));

        return newDataToStore;
    }
}
