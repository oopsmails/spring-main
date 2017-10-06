package com.oopsmails.spring.standalone.main;

import com.oopsmails.spring.standalone.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMain {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        ApplicationMain applicationMain = context.getBean(ApplicationMain.class);
        applicationMain.start();
    }

    private void start() {
        logger.info("####################### Application started! ###########################");
    }

    @Scheduled(fixedRate = 5000)
    public void printMessage() {
        logger.info("I am called by Spring scheduler ...");
    }
}
