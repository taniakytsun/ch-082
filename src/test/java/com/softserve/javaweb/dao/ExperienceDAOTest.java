package com.softserve.javaweb.dao;

import com.softserve.javaweb.model.Experience;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ExperienceDAOTest {

    ExperienceDAO experienceDAO = new ExperienceDAO();

    @DataProvider(name = "addExperience")
    public Object[] addExperience() {
        Experience experience1 = new Experience("Yuriy Fedkovych Chernivtsi National University",
                LocalDate.parse("2018-06-10"), LocalDate.parse("2019-02-28"));
        Experience experience2 = new Experience("SoftServe", LocalDate.parse("2019-06-02"),
                LocalDate.parse("2019-09-18"));

        return new Experience[]{experience1, experience2};
    }

    @Test(dataProvider = "addExperience")
    public void testCreate(Experience experience) {
        experienceDAO.create(experience);
    }


    @BeforeTest
    public Experience updateExperience() {
        Experience experience = new Experience((long) 7,"Yuriy Fedkovych Chernivtsi National University",
                LocalDate.parse("2018-08-10"), LocalDate.parse("2019-02-20"));
        return experience;
    }

    @Test
    public void testUpdate() {

        Experience experience = updateExperience();
        experienceDAO.update(experience);
    }

    @Test
    public void testAddExpToPerson() {
        experienceDAO.addExperienceToPerson((long)174 , (long)8);
    }

    @Test
    public void getExpByPersonId(){
        List<Experience> experiences = new ArrayList<>();
             experiences =   experienceDAO.readAllByPersonId((long) 174);
        System.out.println(experiences.toString());
    }
}