package com.oopsmails.spring.standalone.dao;

import com.oopsmails.spring.standalone.domain.ClientExample;
import com.oopsmails.spring.standalone.domain.ConfigProperties;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liu on 2017-09-28.
 */
public interface CrudDao {
    List<ConfigProperties> getAllConfigProperties();

    void insertTable1(List<ClientExample> clientAccountDefunctSecurityList);

    ClientExample getClientExampleById(String id);
}
