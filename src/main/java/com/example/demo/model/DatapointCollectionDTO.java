package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Datapoint collection dto.
 * Defines the structure of the messages sent and received trough rabbitMQ
 */
@Getter
@Setter
@ToString
public class DatapointCollectionDTO {

    // indicates the version of the structure
    @JsonProperty("version")
    private String version;

    // optional
    // Device Identifier
    @JsonProperty("device")
    private String device;

    // optional
    // Identifiers of the gateways
    @JsonProperty("path")
    private List<String> path;

    // list of datastreams
    @JsonProperty("datastreams")
    private List<DatastreamDTO> datastreams;

}
