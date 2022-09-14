package com.example.demo.service;

import com.example.demo.entity.MongoData;
import com.example.demo.utils.ApiConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Demo service.
 * A service that allows to try the process
 */
@Service
@Slf4j
public class DemoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Save data
     * Saves the mongoData object in mongodb database
     *
     * @param dataToSave the object with the data to save
     */
    public void saveData(MongoData dataToSave)
    {

        // parse document as json
        ObjectMapper mapper = new ObjectMapper();

        String mongoDataAsJson = null;

        try {
            // convert user object to json string and return it
            mongoDataAsJson = mapper.writeValueAsString(dataToSave);
        }
        catch (JsonProcessingException e) {
            // catch various errors
            e.printStackTrace();
        }

        log.info("MongoData as Json = {}", mongoDataAsJson);

        // parse string with json as document
        Document doc = Document.parse(mongoDataAsJson);

        // insert document in mongo database
        Document inserted = mongoTemplate.insert(doc, ApiConstants.MONGO_COLLECTION);

        log.info("Created document in mongodb with id = {}", inserted.get("_id").toString());
    }

}
