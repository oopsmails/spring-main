package com.oopsmails.spring.standalone.test.service;

import com.oopsmails.spring.standalone.domain.ConfigProperties;
import com.oopsmails.spring.standalone.service.ConfigProperiesService;
import com.oopsmails.spring.standalone.test.config.ApplicationTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by liu on 2017-09-28.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { //
        ApplicationTestConfig.class //
})
public class ConfigProperiesServiceTest {

    @Autowired
    private ConfigProperiesService configProperiesService;

    @Test
    public void testConfigProperiesService() {
        List<ConfigProperties> result = configProperiesService.getAllConfigProperties();

        assertNotNull(result);
    }
}
