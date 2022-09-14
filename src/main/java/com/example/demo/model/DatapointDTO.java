package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * The type Datapoint dto.
 * Defines part of the message received trough rabbitMQ
 */
@Getter
@Setter
@ToString
public class DatapointDTO {

    // optional
    // time in miliseconds from epoch of the start period of measurement
    @JsonProperty("from")
    private long from;

    // optional
    // time in miliseconds from epoch of the measurement
    @JsonProperty("at")
    private long at;

    @JsonProperty("value")
    private double value;

    // optional
    // list of tags
    @JsonProperty("tags")
    private List<String> tags;

}
