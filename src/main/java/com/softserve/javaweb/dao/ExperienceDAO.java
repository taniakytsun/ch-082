package com.softserve.javaweb.dao;

import com.softserve.javaweb.model.Experience;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExperienceDAO implements DAO<Experience> {

    private static Logger logger = Logger.getLogger(PersonDAO.class.getName());

    DataBaseConnection connection = DataBaseConnection.getInstance();

    private static String createExperience = "INSERT INTO experience (place, datefrom, dateto)" +
            "VALUES (?, ?, ?)";

    private static String addExperienceToPerson = "UPDATE experience SET idperson = ? WHERE idexperience = ?";

    private static String readAll = "SELECT place FROM experience";

    private static String readOne = "SELECT * FROM experience WHERE id = ?";

    private static String readOneLikeName = "SELECT * FROM experience WHERE place = ?";

    private static String deleteAllExperienceByPersonId = "DELETE FROM experience WHERE idperson = ?";

    private static String deleteExperienceById = "DELETE FROM experience WHERE idexperience = ?";

    private static String updateExperienceById = "UPDATE experience SET place = ?, datefrom = ?," +
            "dateto = ? WHERE idexperience = ?";


    @Override
    public void create(Experience experience) {

        int inserted = 0;
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(createExperience)) {
            preparedStatement.setString(1, experience.getPlace());
            preparedStatement.setDate(2, Date.valueOf(experience.getDateFrom()));
            preparedStatement.setDate(3, Date.valueOf(experience.getDateTo()));
            inserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        if (inserted > 0) {
            logger.info("A new experience inserted successfully!");
        }
    }

    public void addExperienceToPerson(Long personId, Long experienceId) {

        int added = 0;
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(addExperienceToPerson)) {
            preparedStatement.setLong(1, personId);
            preparedStatement.setLong(2, experienceId);
            added = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        if (added>0){
            logger.info("Added!");
        }
    }

    @Override
    public void update(Experience experience) {

        int updated = 0;
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(updateExperienceById)) {
            preparedStatement.setString(1,experience.getPlace());
            preparedStatement.setDate(2, Date.valueOf(experience.getDateFrom()));
            preparedStatement.setDate(3,Date.valueOf(experience.getDateTo()));
            preparedStatement.setLong(4, experience.getId());
            updated = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        if (updated > 0) {
            logger.info("An experience was updated successfully!");
        }
    }

    @Override
    public boolean delete(Long experienceId) {

        int deleted = 0;
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(deleteExperienceById)) {
            preparedStatement.setLong(1, experienceId);
            deleted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        if (deleted > 0) {
            return true;
        } else return false;
    }

    @Override
    public Experience readOne(Long id) {

        Experience experience = new Experience();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(readOne)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    experience = getExperienceFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return experience;
    }

    @Override
    public Experience readOneLikeName(String name) {

        Experience experience = new Experience();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(readOneLikeName)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    experience = getExperienceFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return experience;
    }

    @Override
    public List<Experience> readAll() {

        List<Experience> experiences = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(readAll);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Experience experience = getExperienceFromResultSet(resultSet);
                experiences.add(experience);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return experiences;
    }

    public Experience getExperienceFromResultSet(ResultSet resultSet) {

        Experience experience = new Experience();
        try {
            experience.setPlace(resultSet.getString("place"));
            experience.setDateFrom(resultSet.getDate("datefrom").toLocalDate());
            experience.setDateTo(resultSet.getDate("dateto").toLocalDate());
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return experience;
    }

    public void deleteAllByPersonId(Long personId) {

        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(deleteAllExperienceByPersonId)) {
            preparedStatement.setLong(1, personId);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
