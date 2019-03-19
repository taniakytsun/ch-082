package com.softserve.javaweb.service;

import com.softserve.javaweb.dao.ExperienceDAO;
import com.softserve.javaweb.model.Experience;

import java.util.List;

public class ExperienceService {
    ExperienceDAO experienceDAO = new ExperienceDAO();

    public void create(Experience experience) {

        experienceDAO.create(experience);
    }

    public void addExperienceToPerson(Long personId, Long experienceId) {

        experienceDAO.addExperienceToPerson(personId, experienceId);
    }

    public void update(Experience experience) {

        experienceDAO.update(experience);
    }

    public boolean delete(Long experienceId) {

        return experienceDAO.delete(experienceId);
    }

    public Experience readOne(Long id) {

        return experienceDAO.readOne(id);
    }

    public Experience readOneLikeName(String name) {

        return experienceDAO.readOneLikeName(name);
    }

    public List<Experience> readAll() {

        return experienceDAO.readAll();
    }

    public List<Experience> readAllByPersonId(Long personId) {

        return experienceDAO.readAllByPersonId(personId);
    }
}
