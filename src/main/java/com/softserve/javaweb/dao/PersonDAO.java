package com.softserve.javaweb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;

public class PersonDAO implements DAO<Person> {


    private static Logger logger = Logger.getLogger(PersonDAO.class.getName());

    private DataBaseConnection connection = DataBaseConnection.getInstance();

    ExperienceDAO experienceDAO = new ExperienceDAO();

    private static String addPerson = "INSERT INTO person (name,age,birthday,address,email,phonenumber,specialization) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static String updatePerson = ("UPDATE person SET name = ?, age = ?, address = ?, email = ?, phonenumber = ? " +
            "WHERE idperson = ?");

    private static String deletePersonById = "DELETE FROM person WHERE idperson = ?";

    private static String getPersonLikeName = "SELECT * FROM person WHERE name LIKE ?";

    private static String getPersonById = "SELECT * FROM person WHERE idperson = ?";

    private static String getAllPersonsWithExperience = "SELECT p.name , e.place\n" +
            "FROM\n" +
            "  (SELECT * FROM person) AS p\n" +
            "  JOIN experience AS e ON p.idperson = e.idperson\n" +
            "GROUP BY p.idperson, p.name, e.place";

    private static String getAllPersons = "SELECT * FROM person";

    public void create(Person person) {
        int result = 0;
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(addPerson)) {

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setDate(3, Date.valueOf(person.getBirthDay()));
            preparedStatement.setString(4, person.getAddress());
            preparedStatement.setString(5, person.getEmail());
            preparedStatement.setString(6, person.getPhoneNumber());
            preparedStatement.setString(7, person.getSpecialization());

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        logger.info(response(result));
    }

    public List<Person> readAll() {
        List<Person> persons = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(getAllPersons);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Person.Builder builder = getBuilderFromResultSet(resultSet);
                Person person = builder.build();
                persons.add(person);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return persons;
    }

    public Person readOneLikeName(String name) {
        Person person = new Person.Builder().build();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(getPersonLikeName)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Person.Builder builder = getBuilderFromResultSet(resultSet);
                    person = builder.build();
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return person;
    }

    public List<Person> readAllPersonsWithExperience() {
        List<Person> persons = new ArrayList<>();
        List<Experience> experiences = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(getAllPersonsWithExperience);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Person.Builder builder = new Person.Builder().withName(resultSet.getString("name"));
                Experience experience = new Experience();
                experience.setPlace(resultSet.getString("place"));
                experiences.add(experience);
                builder.withExperience(experiences);
                Person person = builder.build();
                persons.add(person);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return persons;
    }

    @Override
    public Person readOne(Long id) {
        Person person = new Person.Builder().build();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(getPersonById)
        ) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Person.Builder builder = getBuilderFromResultSet(resultSet);
                    person = builder.build();
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return person;
    }

    public void update(Person person) {
        int result = 0;
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(updatePerson)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getAddress());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setString(5, person.getPhoneNumber());
            preparedStatement.setLong(6, person.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        logger.info(response(result));
    }

    public boolean delete(Long id) {
        int deleted = 0;
        experienceDAO.deleteAllByPersonId(id);
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(deletePersonById)) {
            preparedStatement.setLong(1, id);
            deleted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        if (deleted > 0) {
            return true;
        } else return false;
    }

    private Person.Builder getBuilderFromResultSet(ResultSet rs) {
        Person.Builder builder = new Person.Builder();
        try {
            builder.withName(rs.getString("name"))
                    .withEmail(rs.getString("email"))
                    .withAge(rs.getInt("age"))
                    .withBirthDay(rs.getDate("birthDay").toLocalDate())
                    .withAddress(rs.getString("address"))
                    .withPhoneNumber(rs.getString("phoneNumber"))
                    .withSpecialization(rs.getString("specialization"));

        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return builder;
    }

    private String response(int result) {
        if (result > 0) {
            return "Successfully!";
        } else
            return "Something went wrong!";
    }
}
