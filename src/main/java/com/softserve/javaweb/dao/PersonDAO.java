package com.softserve.javaweb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;

public class PersonDAO {

    DataBaseConnection connection = DataBaseConnection.getInstance();
    ResultSet resultSet = null;
    private static Logger logger = Logger.getLogger(PersonDAO.class.getName());
    ObjectMapper objectMapper = new ObjectMapper();
    private PreparedStatement preparedStatement = null;
    ExperienceDAO experienceDAO = new ExperienceDAO();

    private static String addPerson = "INSERT INTO person (name,age,birthday,address,email,phonenumber,specialization) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static String updatePerson = ("UPDATE person SET name = ?, age = ?, address = ?, email = ?, phonenumber = ? " +
            "WHERE idperson = ?");
    private static String deletePersonById = "DELETE FROM person WHERE idperson = ?";
    private static String getPersonByName = "SELECT * FROM person WHERE name LIKE ?'%'";
    private static String getAllPersonsWithoutExperience = "Select * from person";
    private static String getAllPersonsWithExperience = "SELECT p.name, array_agg(e.place) AS experiences \n" +
            "FROM \n" +
            "(SELECT * FROM person) AS p \n" +
            "JOIN experience AS e ON p.idperson = e.idperson\n" +
            "GROUP BY p.idperson, p.name";

    public void addPerson(Person person) throws SQLException {

        Long id = 0L;

        preparedStatement = connection.getConnection().prepareStatement(addPerson);
        preparedStatement.setString(1, person.getName());
        preparedStatement.setInt(2, person.getAge());
        preparedStatement.setDate(3, Date.valueOf(person.getBirthDay()));
        preparedStatement.setString(4, person.getAddress());
        preparedStatement.setString(5, person.getEmail());
        preparedStatement.setString(6, person.getPhoneNumber());
        preparedStatement.setString(7, person.getSpecialization());
        resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            id = resultSet.getLong("idperson");
        }
        person.setId(id);
    }



    public void updatePerson(Person person) throws SQLException {

        preparedStatement = connection.getConnection().prepareStatement(updatePerson);
        preparedStatement.setString(1, person.getName());
        preparedStatement.setInt(2, person.getAge());
        preparedStatement.setString(3, person.getAddress());
        preparedStatement.setString(4, person.getEmail());
        preparedStatement.setString(5, person.getPhoneNumber());
        preparedStatement.setLong(6, person.getId());

        int updated = preparedStatement.executeUpdate();
        if (updated > 0) {
            logger.info("An experience was updated successfully!");
        }
    }

    public Person getPersonByName(String name) throws SQLException {

        Person person = new Person();
        preparedStatement = connection.getConnection().prepareStatement(getPersonByName);
        preparedStatement.setString(1, name);
        resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            getPersonFromResultSet(resultSet, person);
            logger.log(Level.INFO, person.toString());
        }
        return person;
    }

    public Person getPersonFromResultSet(ResultSet rs, Person person) {

        try {
            person.setEmail(rs.getString("email"));
            person.setName(rs.getString("name"));
            person.setBirthDay(rs.getDate("birthDay").toLocalDate());
            person.setAge(rs.getInt("age"));
            person.setAddress(rs.getString("address"));
            person.setPhoneNumber(rs.getString("phoneNumber"));
            person.setSpecialization(rs.getString("specialization"));
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return person;
    }




    public List<Person> getAllPersonsWithoutExperience() throws SQLException {

        List<Person> persons = new ArrayList<>();
        Person person = new Person();

        preparedStatement = connection.getConnection().prepareStatement(getAllPersonsWithoutExperience);
        resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            getPersonFromResultSet(resultSet, person);
            persons.add(person);
        }
        try {
            logger.info(objectMapper.writeValueAsString(persons));
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return persons;
    }

    public List<Person> getAllPersonsWithExperience() throws SQLException {

        List<Person> persons = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();
        Person person = new Person();
        preparedStatement = connection.getConnection().prepareStatement(getAllPersonsWithExperience);
        resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            Experience experience = new Experience();
            experienceDAO.getExperienceFromResultSet(resultSet,experience);
            experiences.add(experience);
            getPersonFromResultSet(resultSet, person);
            person.setExperience(experiences);
        }
        try {
            logger.info(objectMapper.writeValueAsString(experiences));
        } catch (JsonProcessingException e) {
            logger.warning(e.getMessage());
        }
        return persons;
    }

    public void deletePersonById(Long id) throws SQLException {

        experienceDAO.deleteAllExperienceByPersonId(id);
        preparedStatement = connection.getConnection().prepareStatement(deletePersonById);
        preparedStatement.setLong(1, id);
        int deleted = preparedStatement.executeUpdate();
        if (deleted > 0) {
            logger.info("Successful deleted!");
        }
    }

}