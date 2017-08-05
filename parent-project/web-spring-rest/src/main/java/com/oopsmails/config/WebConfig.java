package com.oopsmails.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
//@ImportResource("file:src/main/webapp/WEB-INF/spring/applicationContext-rest.xml")
@ComponentScan("com.oopsmails")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }

}
