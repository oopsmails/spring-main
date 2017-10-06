package com.oopsmails.spring.standalone.service.impl;

import com.oopsmails.spring.standalone.dao.CrudDao;
import com.oopsmails.spring.standalone.domain.ConfigProperties;
import com.oopsmails.spring.standalone.request.Homes;
import com.oopsmails.spring.standalone.service.ConfigProperiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liu on 2017-09-28.
 */
@Service
public class ConfigProperiesServiceImpl implements ConfigProperiesService {
    private static final Logger logger = LoggerFactory.getLogger(ConfigProperiesServiceImpl.class);

    @Autowired
    private CrudDao crudDao;

    @Override
    public List<ConfigProperties> getAllConfigProperties() {
        return crudDao.getAllConfigProperties();
    }

    @Scheduled(cron = "*/5 * * * * MON-FRI")
    public void everyThurTask() {
        logger.info("============ */5 * * * * MON-FRI =============");
        Homes menus = new Homes();
        logger.info("============ testing generated-sources, Menu =============");
    }
}
