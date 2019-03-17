package com.softserve.javaweb.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.javaweb.model.Experience;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExperienceDAO {

    private static Logger logger = Logger.getLogger(PersonDAO.class.getName());
    DataBaseConnection connection = DataBaseConnection.getInstance();
    ResultSet resultSet = null;
    ObjectMapper objectMapper = new ObjectMapper();
    PreparedStatement preparedStatement = null;

    private static String addExperienceToPerson = "INSERT INTO experience (place, datefrom, dateto, idperson)" +
            "VALUES (?, ?, ?, ?)";
    private static String deleteAllExperienceByPersonId = "DELETE FROM experience WHERE idperson = ?";
    private static String deleteExperienceById = "DELETE FROM experience WHERE idexperience = ?";
    private static String updateExperienceById = "UPDATE experience SET place = ?, datefrom = ?," +
            "dateto = ? WHERE idexperience = ?";

    public void updateExperience(Experience experience) throws SQLException {

        setDataToPreparedStatement(experience, updateExperienceById);
        preparedStatement.setLong(4, experience.getId());
        int updated = preparedStatement.executeUpdate();
        if (updated > 0) {
            logger.info("An experience was updated successfully!");
        }
    }

    public void addExperienceToPerson(Experience experience) throws SQLException {

        setDataToPreparedStatement(experience, addExperienceToPerson);
        int inserted = preparedStatement.executeUpdate();
        if (inserted > 0) {
            logger.info("A new experience inserted successfully!");
        }
    }

    private void setDataToPreparedStatement(Experience experience, String query) throws SQLException {

        preparedStatement = connection.getConnection().prepareStatement(query);
        preparedStatement.setString(1, experience.getPlace());
        preparedStatement.setDate(2, Date.valueOf(experience.getDateFrom()));
        preparedStatement.setDate(3, Date.valueOf(experience.getDateTo()));
    }


    public void getExperienceFromResultSet(ResultSet resultSet, Experience experience) {

        try {
            experience.setPlace(resultSet.getString("place"));
            experience.setDateFrom(resultSet.getDate("datefrom").toLocalDate());
            experience.setDateTo(resultSet.getDate("dateto").toLocalDate());
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public void deleteAllExperienceByPersonId(Long personId) throws SQLException {
        deleteExperience(personId, deleteAllExperienceByPersonId);
    }


    public void deleteExperienceById(Long experienceId) throws SQLException {
        deleteExperience(experienceId, deleteExperienceById);
    }

    private void deleteExperience(Long experienceId, String query) throws SQLException {
        preparedStatement = connection.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, experienceId);
        int deleted = preparedStatement.executeUpdate();
        if (deleted > 0) {
            logger.info("Successful deleted experience!");
        }
    }
}
