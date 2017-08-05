package com.oopsmails.test.config;

import com.oopsmails.config.WebConfig;
import com.oopsmails.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created by liu on 2017-07-12.
 */

//@EnableWebMvc
//@Configuration
//@ImportResource("classpath:applicationContext-rest-test.xml")
//@Import({WebConfig.class})
//@ComponentScan("com.com.oopsmails")
//@EnableAspectJAutoProxy
public class WebConfigMockitoTest extends WebMvcConfigurationSupport {
    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        OopsRequestMappingHandlerMapping rmh = new OopsRequestMappingHandlerMapping();
        return rmh;
    }

    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }
}
