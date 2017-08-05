package com.oopsmails.service;

import com.oopsmails.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User findById(int id);

    User findByName(String name);

    void create(User user);

    void update(User user);

    void delete(int id);

    boolean exists(User user);

    List<User> getByIds(List<Integer> ids);
}
