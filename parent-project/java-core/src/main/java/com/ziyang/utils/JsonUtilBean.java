package com.ziyang.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by liu on 2017-08-04.
 */
@Component
public class JsonUtilBean {

    public String printJson(Object object) {
        return printAndWriteJson(object, null);
    }

    public String printAndWriteJson(Object object, String writeToFileName) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonInString = "object cannot be converted ?!";
        try {
//            if (writeToFileName != null) {
//                mapper.writeValue(new File(writeToFileName), object);// "src/test/resources/portfolios/portfolios-1.json"
//            }

            // Convert object to JSON string
            jsonInString = mapper.writeValueAsString(object);
//            System.out.println(jsonInString);

            // Convert object to JSON string and pretty print
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
//            System.out.println(jsonInString);

            if (writeToFileName != null) {
                BufferedWriter out = new BufferedWriter(new FileWriter(writeToFileName));
                out.write(jsonInString);
                out.close();
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonInString;
    }

    public <T> T jsonToObject(String fromFileName, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T t = null;
        try {
            // Convert JSON string from file to Object
            t = mapper.readValue(new File(fromFileName), clazz);
            return t;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }
}

