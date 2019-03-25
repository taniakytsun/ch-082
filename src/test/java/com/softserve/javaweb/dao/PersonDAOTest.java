package com.softserve.javaweb.dao;

import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PersonDAOTest {

    private static Logger logger = Logger.getLogger(PersonDAOTest.class.getName());

    PersonDAO personDAO = new PersonDAO();

    @DataProvider(name = "addPerson")
    public Object[] addPerson() {
        Experience experience1 = new Experience("Yuriy Fedkovych Chernivtsi National University",
                LocalDate.parse("2018-06-10"), LocalDate.parse("2019-02-28"));
        Experience experience2 = new Experience("SoftServe", LocalDate.parse("2019-06-02"),
                LocalDate.parse("2019-09-18"));
        List<Experience> experiences = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        experiences.add(experience1);
        experiences.add(experience2);
        Person person = new Person.Builder().withName("TetianaTsan")
                .withAge(22)
                .withBirthDay(LocalDate.parse("1997-02-25"))
                .withAddress("Chernivtsi city, Russka street")
                .withEmail("taniakytsun@gmail.com")
                .withPhoneNumber("(097)5395051")
                .withSpecialization("Java")
                .withExperience(experiences)
                .build();
        Person person1 = new Person.Builder()
                .withName("Anastasia Lakusta")
                .withAge(20)
                .withBirthDay(LocalDate.parse("1997-06-24"))
                .withAddress("Chernivtsi city, Holovna street, 3")
                .withEmail("nastichka@gmail.com")
                .withPhoneNumber("(050)5754607")
                .withSpecialization("Business analyst")
                .build();
        persons.add(person);
        persons.add(person1);
        return new Person[]{person, person1};
    }

    @Test
    public void testGetAllPersonsFromDB() {
        List<Person> persons = personDAO.readAllPersonsWithExperience();
        System.out.println(persons.toString());
    }

    @Test(dataProvider = "addPerson")
    public void testAddPersonToDB(Person person) {
        personDAO.create(person);
    }

    @Test
    public void testDeletePersonFromDB() {
        personDAO.delete((long) 187);
    }

    @Test
    public void testReadAll() {
        List<Person> persons = personDAO.readAll();
        for (Person person : persons) {
            logger.info(person.toString());
        }
    }

    @Test
    public void testReadOneLikeName() {
        Person person = personDAO.readOneLikeName("Oksana%");
        logger.info(person.toString());
    }

    @Test
    public void testReadAllPersonsWithExperience() {
        List<Person> persons = personDAO.readAllPersonsWithExperience();
        for (Person person : persons) {
            logger.info(person.getName() + " " + person.getExperience());
        }
    }

    @Test
    public void testReadOne() {
        Person person = personDAO.readOne((long) 1);
        logger.info(person.toString());
    }

    @BeforeTest
    public Person updatePerson() {
        Experience experience1 = new Experience("Yuriy Fedkovych Chernivtsi National University",
                LocalDate.parse("2018-06-10"), LocalDate.parse("2019-02-28"));
        Experience experience2 = new Experience("SoftServe", LocalDate.parse("2019-06-02"),
                LocalDate.parse("2019-09-18"));
        List<Experience> experiences = new ArrayList<>();
        List<Person> persons = new ArrayList<>();
        experiences.add(experience1);
        experiences.add(experience2);
        Person person = new Person.Builder().withName("Tetiana Tsan")
                .withId((long) 157)
                .withAge(22)
                .withBirthDay(LocalDate.parse("1997-02-25"))
                .withAddress("Chernivtsi city, Russka street, 265")
                .withEmail("taniakytsun@gmail.com")
                .withPhoneNumber("(097)5395051")
                .withSpecialization("Java")
                .withExperience(experiences)
                .build();
        return person;
    }

    @Test()
    public void testUpdate() {

        Person person = updatePerson();
        personDAO.update(person);
        Person person1 = personDAO.readOne(person.getId());
        logger.info(person1.toString());
    }

    @Test
    public void testDelete() {

        personDAO.delete((long) 173);
    }
}
