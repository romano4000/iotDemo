package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Datastream dto.
 * Defines part of the message received trough rabbitMQ
 */
@Getter
@Setter
@ToString
public class DatastreamDTO {

    // Identifier of the datastream
    @JsonProperty("id")
    private String id;

    // Feed Identifier
    @JsonProperty("feed")
    private String feed;

    // list of datapoints
    @JsonProperty("datapoints")
    private List<DatapointDTO> datapoints;
}
