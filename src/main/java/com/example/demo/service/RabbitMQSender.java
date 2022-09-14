package com.example.demo.service;

import com.example.demo.model.DatapointCollectionDTO;
import com.example.demo.utils.ApiConstants;
import com.example.demo.utils.MockData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * The type RabbitMQ sender.
 * Generates random messages with the corresponding format and stores them in rabbitMQ queue
 */
@Slf4j
@Component
public class RabbitMQSender {

    /**
     * The Mock data.
     */
    MockData mockData = new MockData();

    @Value("${spring.rabbitmq.host}")
    private String host;

    /**
     * Send data to queue.
     *
     * @throws Exception the exception
     */
    @Scheduled(fixedRate = ApiConstants.INTERVAL_SEND_MILISECONDS)
    public void sendDataToQueue() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(host);

        // open connection
        try (Connection connection = factory.newConnection();

             // open channel
             Channel channel = connection.createChannel()) {

            // declare the queue where data is going to be stored
            channel.queueDeclare(ApiConstants.QUEUE_NAME, false, false, false, null);

            // data to store in queue
            DatapointCollectionDTO dataToSend = mockData.getNewMessage();
            // map to json string
            ObjectMapper mapper = new ObjectMapper();

            String dataToSendAsString = null;

            try {
                // convert user object to json string
                dataToSendAsString = mapper.writeValueAsString(dataToSend);
            } catch (JsonProcessingException e) {
                // catch various errors
                e.printStackTrace();
            }

            // push data to queue
            if(dataToSendAsString != null) {
                channel.basicPublish("", ApiConstants.QUEUE_NAME, null,
                        dataToSendAsString.getBytes(StandardCharsets.UTF_8));

                log.info(" [x] Sent '" + dataToSendAsString + "'");
            }
        }
    }
}
