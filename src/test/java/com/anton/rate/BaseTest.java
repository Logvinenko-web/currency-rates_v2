package com.anton.rate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseTest {

    @Autowired
    protected ObjectMapper mapper;

    protected String readAsString(String file) {
        try {
            return new String(Files.readAllBytes(new File("src/test/resources/" + file + ".json").toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> String toJson(T value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
