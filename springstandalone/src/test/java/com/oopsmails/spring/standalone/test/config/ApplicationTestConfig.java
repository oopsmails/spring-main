package com.oopsmails.spring.standalone.test.config;

import com.oopsmails.spring.standalone.config.ApplicationConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ComponentScan({"com.oopsmails.spring.standalone" //
})
@ContextConfiguration(classes = { //
        ApplicationConfig.class //
})
public class ApplicationTestConfig {

}
