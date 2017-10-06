package com.oopsmails.spring.standalone.service;

import com.oopsmails.spring.standalone.domain.ConfigProperties;

import java.util.List;

/**
 * Created by liu on 2017-09-28.
 */
public interface ConfigProperiesService {
    List<ConfigProperties> getAllConfigProperties();
}
