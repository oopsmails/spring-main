package com.oopsmails.test.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.validation.Schema;

import org.springframework.util.StreamUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ResultVerifyUtils {

	private static final String TEST_EXPECTED_REPLACE = "test.expected.replace";

//	public static <T> void assertResult(List<T> list, Class<T> type, String name) throws Exception {
//		L<T> l = new L<T>();
//		l.elements = list;
//
//		JAXBContext context = JAXBContext.newInstance(type, L.class);
//		Marshaller m = context.createMarshaller();
//		String actualXml = toXml(m, l);
//
//		File resource = new File("src/test/resources/sp", name + ".xml");
//		if(!resource.exists() || Boolean.getBoolean(TEST_EXPECTED_REPLACE)) {
//			resource.getParentFile().mkdirs();
//			try (OutputStream os = new FileOutputStream(resource)) {
//				StreamUtils.copy(actualXml, StandardCharsets.UTF_8, os);
//			}
//		} else {
//			try (InputStream is = new FileInputStream(resource)) {
//				String expected = StreamUtils.copyToString(is, StandardCharsets.UTF_8).replaceAll("\r\n", "\n");
//				String actual = actualXml.replaceAll("\r\n", "\n");
//				assertEquals("Expected:" + expected + "\nActual:" + actual + "\n", expected, actual);
//			}
//		}
//	}
//
//	public static <T> String listToXml(List<T> list, Class<T> type) throws Exception {
//		L<T> l = new L<T>();
//		l.elements = list;
//
//		JAXBContext context = JAXBContext.newInstance(type, L.class);
//		Marshaller m = context.createMarshaller();
//		String actualXml = toXml(m, l);
//		return actualXml;
//	}
//
//	public static <T> void assertResult(T actualValue, String expectedResource) throws Exception {
//		assertResult(actualValue, expectedResource, null);
//	}
//
//	public static <T> void assertResult(T actualValue, String expectedResource, Schema schema) throws Exception {
//		String actualXml = toXml(actualValue, schema);
//
//		File resource = new File("src/test/resources/", expectedResource);
//		if(!resource.exists() || Boolean.getBoolean(TEST_EXPECTED_REPLACE)) {
//			try(OutputStream fs = new FileOutputStream(resource)) {
//				resource.getParentFile().mkdirs();
//				StreamUtils.copy(actualXml, StandardCharsets.UTF_8, fs);
//			}
//		} else {
//			String expected = readExpected(expectedResource);
//			String actual = actualXml.replaceAll("\r\n", "\n");
//			assertEquals("Expected:" + expected + "\nActual:" + actual + "\n", expected, actual);
//		}
//	}

	
	@XmlRootElement(name = "list")
	@XmlAccessorType (XmlAccessType.FIELD)
	public static class L<T> {
	    @XmlElement(name = "element") List<T> elements;
	}


	public static String readExpected(String path) throws IOException {
		try(InputStream is = ResultVerifyUtils.class.getResourceAsStream(path)) {
			if (is == null) {
				return "";
			}
			return StreamUtils.copyToString(is, StandardCharsets.UTF_8).replaceAll("\r\n", "\n");
		}
	}	

}
