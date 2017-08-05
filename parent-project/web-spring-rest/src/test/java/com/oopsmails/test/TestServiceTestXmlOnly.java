package com.oopsmails.test;

import com.oopsmails.legcybean.LogLevelType;
import com.oopsmails.legcybean.TestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by liu on 2017-07-01.
 */

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-rest-test.xml"})
public class TestServiceTestXmlOnly {
    @Autowired
    private TestService testService;

    @Before
    public void init() {
    }

    @Test
    public void test() throws Exception {
        LogLevelType logLevel = testService.determineLogLevelType(1);
        System.out.println("================= logLevel = " + logLevel);
    }
}
