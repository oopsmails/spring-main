package com.ziyang.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by liu on 2017-07-03.
 */
public final class JsonUtils {
    public static String printJson(Object object) {
        return printAndWriteJson(object, null, null, true);
    }

    public static String getJsonFromObject(Object object) {
        return printAndWriteJson(object, null, null, false);
    }

    public static String printJson(Object object, boolean printToConsole) {
        return printAndWriteJson(object, null, null, printToConsole);
    }

    public static String printJsonWithTitle(Object object, String title) {
        return printAndWriteJson(object, null, title, false);
    }

    public static String printAndWriteJson(Object object, String writeToFileName,
                                           String title, boolean printToConsole) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonInString = "object cannot be converted ?!";
        try {
            // Convert object to JSON string
            jsonInString = mapper.writeValueAsString(object);

            // Convert object to JSON string and pretty print
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            if (printToConsole) {
                if (title != null && !"".equals(title.trim())) {
                    System.out.println("########### " + title + " begin ########### ");
                    System.out.println(jsonInString);
                    System.out.println("########### " + title + " end ########### ");
                } else {
                    System.out.println(jsonInString);
                }
            }

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

    public static <T> T jsonToObject(String fromFileName, Class<T> clazz) {
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
