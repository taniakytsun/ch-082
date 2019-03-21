package com.softserve.javaweb.dao;

import java.util.List;

public interface DAO<T> {

    void create(T t);

    List<T> readAll();

    void update(T t);

    boolean delete(Long id);

    T readOne(Long id);

    T readOneLikeName(String name);

}
