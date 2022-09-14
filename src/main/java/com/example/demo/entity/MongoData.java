package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * The class Mongo data.
 * Defines the data to be stored in mongodb database
 */

@Getter
@Setter
@ToString
public class MongoData {

    private Date creationDate;

    private double media;

    private double mediana;

    private double moda;

    private double desvTipica;

    private double cuartil;

    private double maximo;

    private double minimo;
}
