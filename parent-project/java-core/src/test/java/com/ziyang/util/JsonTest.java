package com.ziyang.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.json.stream.JsonGenerationException;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziyang.entity.Address;
import com.ziyang.entity.Client;
import com.ziyang.entity.Views;

/**
 * Thanks, https://www.oopsmails.com/java/jackson-2-convert-java-object-to-from-json/
 * 
 * Differences from Jackson 1.x Most of the APIs still maintains the same method
 * name and signature, just the packaging is different.
 * 
 * Jackson 1.x – org.codehaus.jackson.map
 * 
 * Jackson 2.x – com.fasterxml.jackson.databind
 * 
 * 
 * @author liu
 *
 */

public class JsonTest {
	@Test
	public void testObjectToJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		Client client = createDummyObject();

		try {
			// Convert object to JSON string and save into a file directly
			mapper.writeValue(new File("src/test/resources/util/client.json"), client);

			// Convert object to JSON string
			String jsonInString = mapper.writeValueAsString(client);
			System.out.println(jsonInString);

			// Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(client);
			System.out.println(jsonInString);

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
			System.out.println(client);

			// Convert JSON string to Object
			String jsonInString = "{\"name\":\"oopsmails\",\"salary\":1000,\"skills\":[\"java\",\"python\"]}";
			Client client1 = mapper.readValue(jsonInString, Client.class);
			System.out.println(client1);

			//Pretty print
			String prettyClient1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(client);
			System.out.println(prettyClient1);

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
			System.out.println("Normal View");
			String normalView = mapper.writerWithView(Views.Normal.class).writeValueAsString(client);
			System.out.println(normalView);

			String jsonInString = "{\"name\":\"oopsmails\",\"age\":33,\"position\":\"Developer\",\"salary\":1000,\"skills\":[\"java\",\"python\"]}";
			Client normalStaff = mapper.readerWithView(Views.Normal.class).forType(Client.class).readValue(jsonInString);
			System.out.println(normalStaff);

			// Display everything
			System.out.println("\nManager View");
			String managerView = mapper.writerWithView(Views.Manager.class).writeValueAsString(client);
			System.out.println(managerView);

			Client managerStaff = mapper.readerWithView(Views.Manager.class).forType(Client.class).readValue(jsonInString);
			System.out.println(managerStaff);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
