package com.softserve.javaweb.service;

import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonServiceTest {

    PersonService personService = new PersonService();

    @DataProvider(name = "addListOfPersons")
    public Object[] addListOfPersons() {
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

    @DataProvider(name = "getNewFullPerson")
    public Object[] getNewFullPerson() {
        Experience experience1 = new Experience("Yuriy Fedkovych Chernivtsi National University",
                LocalDate.parse("2018-06-10"), LocalDate.parse("2019-02-28"));
        Experience experience2 = new Experience("SoftServe", LocalDate.parse("2019-06-02"),
                LocalDate.parse("2019-09-18"));
        List<Experience> experiences = new ArrayList<>();
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

        return new Person[]{person};
    }

    @DataProvider(name = "getNewPersonWithoutExperience")
    public Object[] getNewPersonWithoutExperience() {
        Person person1 = new Person.Builder()
                .withName("Anastasia Lakusta")
                .withAge(20)
                .withBirthDay(LocalDate.parse("1997-06-24"))
                .withAddress("Chernivtsi city, Holovna street, 3")
                .withEmail("nastichka@gmail.com")
                .withPhoneNumber("(050)5754607")
                .withSpecialization("Business analyst")
                .build();

        return new Person[]{person1};
    }

    @Test(dataProvider = "getNewFullPerson")
    public void testParseJSONFullObjectAndAssertEquals(Person expectedPerson) throws SQLException {
        Person person = personService.parsePersonFromJSON("src//test//resources//person.json");
        Assert.assertEquals(person, expectedPerson);
    }

    @Test(dataProvider = "getNewPersonWithoutExperience")
    public void testParseJSONObjectWithNullValuesAndAssertEquals(Person expectedPerson) throws SQLException {
        Person person = personService.parsePersonFromJSON("src//test//resources//personWithoutExp.json");
        Assert.assertEquals(person, expectedPerson);
    }

    @Test(dataProvider = "getNewFullPerson")
    public void testParseXMLFullObjectAndAssertEquals(Person expectedPerson) throws SQLException {
        Person person = personService.parsePersonFromXML("src//test//resources//person.xml");
        Assert.assertEquals(person, expectedPerson);
    }

    @Test(dataProvider = "getNewPersonWithoutExperience")
    public void testParseXMLObjectWithNullValuesAndAssertEquals(Person expectedPerson) throws SQLException {
        Person person = personService.parsePersonFromXML("src//test//resources//personWithoutExp.xml");
        Assert.assertEquals(person, expectedPerson);
    }

    @Test(dataProvider = "getNewFullPerson")
    public void testParseTXTAndAssertEquals(Person expectedPerson) throws SQLException, IOException {
        Person person = personService.parsePersonFromTXT("src//test//resources//person.txt");
        Assert.assertEquals(person, expectedPerson);
    }

    @Test(dataProvider = "getNewPersonWithoutExperience")
    public void testParseTXTObjWithoutExperienceAssertEquals(Person expectedPerson) throws SQLException, IOException {
        Person person = personService.parsePersonFromTXT("src//test//resources//personWithoutExp.txt");
        Assert.assertEquals(person, expectedPerson);
    }

    @BeforeTest
    public List<Person> getListOfPersons() {
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
        return persons;
    }
    @Test
    public void testExportYaml() throws IOException {
        List<Person> persons = getListOfPersons();
       personService.exportYaml("src//test//resources//out//persons.yaml", persons);
    }

    @Test
    public void testValidate() {
//TODO
    }
}