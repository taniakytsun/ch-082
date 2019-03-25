package com.softserve.javaweb.service;

import com.softserve.javaweb.dao.PersonDAO;
import com.softserve.javaweb.model.Person;

import java.util.List;

public class PersonService {

    private PersonDAO personDAO = new PersonDAO();

    public void createPerson(Person person) {
        personDAO.create(person);
    }

    public List<Person> readAll() {

        return personDAO.readAll();
    }

    public Person readOneLikeName(String name) {

        return personDAO.readOneLikeName(name);
    }

    public List<Person> readAllPersonsWithExperience() {

        return personDAO.readAllPersonsWithExperience();
    }

    public Person readOne(Long id) {

        return personDAO.readOne(id);
    }

    public void update(Person person) {

        personDAO.update(person);
    }

    public boolean delete(Long id) {

        return personDAO.delete(id);
    }


}
