package com.softserve.javaweb.DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;
import com.softserve.javaweb.web.DataBaseConnection;

public class PersonDAO {

    private String name = "name";
    private String age = "age";
    private String birthDay = "birthday";
    private String address = "address";
    private String email = "email";
    private String phoneNumber = "phonenumber";
    private String specialization = "specialization";

    private Person person = new Person();
    private Statement statement = null;
    private ResultSet rs = null;
    private static Logger logger = Logger.getLogger(PersonDAO.class.getName());
    private Connection connection = new DataBaseConnection().connect();
    private PreparedStatement preparedStatement = null;

    public void addPerson(Person person) throws SQLException {

        Long id = 0L;
        try {
            statement = connection.createStatement();
            String query = "INSERT INTO person (name,age,birthday,address,email,phonenumber,specialization) "
                    + "VALUES " + "(" + "\'" + person.getName() + "\', '" + person.getAge() + "', '"
                    + person.getBirthDay() + "', '" + person.getAddress() + "', '" + person.getEmail() + "', '"
                    + person.getPhoneNumber() + "', '" + person.getSpecialization() + "')";
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong("idperson");
            }
            person.setId(id);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            statement.close();
            rs.close();
        }
    }

    public void addExperienceToPerson(Long idPerson, String place, LocalDate dateFrom, LocalDate dateTo)
            throws SQLException {
        try {
            statement = connection.createStatement();
            String query = "INSERT INTO experience (place,datefrom,dateto,idperson) " + "VALUES " + "(" + "\'" + place
                    + "\', '" + dateFrom + "', '" + dateTo + "', '" + idPerson + "')";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.log(Level.CONFIG, e.getMessage());
        } finally {
            statement.close();
        }
    }

    public void updatePerson(Long personId) throws SQLException {

        preparedStatement = connection.prepareStatement("UPDATE Person SET name=?, age=?, address=? email=? "
                +"phonenumber=? WHERE idperson="+ personId);
        try {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getAddress());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setString(5, person.getPhoneNumber());
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            preparedStatement.close();
            preparedStatement.close();
        }
    }

    public Person getPersonByName(String name) throws SQLException {
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM person WHERE name LIKE '"+name +"%'");
            while (rs.next()) {
                setPersonFromResultSet(rs);
                logger.log(Level.INFO, person.toString());
            }

        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            rs.close();
            statement.close();
        }
        return person;
    }

    public Person setPersonFromResultSet(ResultSet rs) {

        try {
            person.setEmail(rs.getString(this.email));
            person.setName(rs.getString(this.name));
            person.setBirthDay(rs.getDate(this.birthDay).toLocalDate());
            person.setAge(rs.getInt(this.age));
            person.setAddress(rs.getString(this.address));
            person.setPhoneNumber(rs.getString(this.phoneNumber));
            person.setSpecialization(rs.getString(this.specialization));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public List<Person> getAllPersonsWithoutExperience() throws SQLException {

        List<Person> persons = new ArrayList<>();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("Select * from person");
            while (rs.next()) {
                setPersonFromResultSet(rs);
                logger.log(Level.INFO, person.toString());
                persons.add(person);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            rs.close();
            statement.close();
        }
        return persons;
    }

    public void getPersonWithExperience() throws SQLException {
        try {
            statement = connection.createStatement();

            rs = statement.executeQuery("SELECT p.*, e.place, e.datefrom, e.dateto \n" +
                    "FROM person AS  p \n" +
                    "JOIN experience AS e ON \n" +
                    "p.idperson = e.idperson \n" +
                    "WHERE p.name = 'Oksana Odochuk'");
            while (rs.next()) {
                List<Experience> experience = new ArrayList<>();
                Experience exp = new Experience();
                exp.setPlace(rs.getString("place"));
                exp.setDateFrom(rs.getDate("datefrom").toLocalDate());
                exp.setDateTo(rs.getDate("dateto").toLocalDate());
                experience.add(exp);
                setPersonFromResultSet(rs);
                person.setExperience(experience);
            }
            logger.info(this.person.toString());
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            rs.close();
            statement.close();
        }
    }

    public void deletePersonById(Long id) throws SQLException {
        try {
            deleteAllExperienceByPersonId(id);
            preparedStatement = connection.prepareStatement("DELETE FROM person WHERE idperson = ?");
            preparedStatement.setLong(1, id);
            int deleted = preparedStatement.executeUpdate();
            if (deleted > 0) {
                logger.info("Successful deleted!");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            rs.close();
            statement.close();
        }
    }

    public void deleteAllExperienceByPersonId(Long personId) throws SQLException {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM experience WHERE idperson = ?");
            preparedStatement.setLong(1, personId);
            int deleted = preparedStatement.executeUpdate();
            if (deleted > 0) {
                logger.info("Successful deleted experience!");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            rs.close();
            statement.close();
        }
    }

    public void deleteExperienceByPersonId(Long personId, Long experienceId) throws SQLException {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM experience WHERE idperson = ? AND idexperience = ?");
            preparedStatement.setLong(1, personId);
            preparedStatement.setLong(2, experienceId);
            int deleted = preparedStatement.executeUpdate();
            if (deleted > 0) {
                logger.info("Successful deleted experience!");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        } finally {
            rs.close();
            statement.close();
        }
    }
}
