package com.ziyang.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyang.entity.Address;
import com.ziyang.entity.Client;
import com.ziyang.entity.Views;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.stream.JsonGenerationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Thanks, https://www.oopsmails.com/java/jackson-2-convert-java-object-to-from-json/
 * <p>
 * Differences from Jackson 1.x Most of the APIs still maintains the same method
 * name and signature, just the packaging is different.
 * <p>
 * Jackson 1.x – org.codehaus.jackson.map
 * <p>
 * Jackson 2.x – com.fasterxml.jackson.databind
 *
 * @author liu
 */

public class JsonTest {
    private static final Logger logger = LoggerFactory.getLogger(JsonTest.class);

    @Test
    public void testObjectToJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Client client = createDummyObject();

        try {
            // Convert object to JSON string and save into a file directly
            mapper.writeValue(new File("src/test/resources/util/client.json"), client);

            // Convert object to JSON string
            String jsonInString = mapper.writeValueAsString(client);
            logger.info(jsonInString);

            // Convert object to JSON string and pretty print
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(client);
            logger.info(jsonInString);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Client createDummyObject() {

        Client client = new Client();

        client.setName("oopsmails");
        client.setAge(33);
        client.setPosition("Developer");
        client.setSalary(new BigDecimal("7500"));

        List<String> skills = new ArrayList<>();
        skills.add("java");
        skills.add("python");

        client.setSkills(skills);

        List<Address> addresses = new ArrayList<>();
        Address primaryAddress = new Address();
        primaryAddress.setAddressType("primary");
        primaryAddress.setStreetName("123");
        addresses.add(primaryAddress);

        client.setAddresses(addresses);

        return client;
    }

    @Test
    public void testJsonToObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try {

            // Convert JSON string from file to Object
            Client client = mapper.readValue(new File("src/test/resources/util/client.json"), Client.class);
            logger.info("{}", client);

            // Convert JSON string to Object
            String jsonInString = "{\"name\":\"oopsmails\",\"salary\":1000,\"skills\":[\"java\",\"python\"]}";
            Client client1 = mapper.readValue(jsonInString, Client.class);
            logger.info("{}", client1);

            //Pretty print
            String prettyClient1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(client);
            logger.info(prettyClient1);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJsonView() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Client client = createDummyObject();

        try {

            // Salary will be hidden
            logger.info("Normal View");
            String normalView = mapper.writerWithView(Views.Normal.class).writeValueAsString(client);
            logger.info(normalView);

            String jsonInString = "{\"name\":\"oopsmails\",\"age\":33,\"position\":\"Developer\",\"salary\":1000,\"skills\":[\"java\",\"python\"]}";
            Client normalStaff = mapper.readerWithView(Views.Normal.class).forType(Client.class).readValue(jsonInString);
            logger.info("{}", normalStaff);

            // Display everything
            logger.info("\nManager View");
            String managerView = mapper.writerWithView(Views.Manager.class).writeValueAsString(client);
            logger.info(managerView);

            Client managerStaff = mapper.readerWithView(Views.Manager.class).forType(Client.class).readValue(jsonInString);
            logger.info("{}", managerStaff);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
