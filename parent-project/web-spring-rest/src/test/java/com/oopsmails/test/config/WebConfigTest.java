package com.oopsmails.test.config;

import com.oopsmails.config.WebConfig;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@EnableWebMvc
@Configuration
@ImportResource("classpath:applicationContext-rest-test.xml")
@Import({WebConfig.class })
@ComponentScan("com.oopsmails")
@EnableAspectJAutoProxy
public class WebConfigTest extends //WebMvcConfigurerAdapter {
        WebMvcConfigurationSupport {
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        OopsRequestMappingHandlerMapping rmh = new OopsRequestMappingHandlerMapping();
        return rmh;
    }

}
