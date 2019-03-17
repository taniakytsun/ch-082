package com.softserve.javaweb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;

import com.softserve.javaweb.dao.PersonDAO;

public class PersonService {

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    ObjectMapper mapper = new ObjectMapper();
    ObjectMapper xmlMapper = new ObjectMapper(new XmlFactory());
    PersonDAO personDAO = new PersonDAO();

    private static Logger logger = Logger.getLogger(PersonService.class.getName());
    List<Person> persons = new ArrayList<>();
    BufferedReader br;

    public static boolean validate(Object object, Validator validator) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations.isEmpty()) {
            return true;
        } else {
            for (ConstraintViolation<Object> cv : constraintViolations)
                logger.log(Level.WARNING, (String.format("Error! property: [%s], value: [%s], message: [%s]",
                        cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage())));

            return false;
        }
    }

    public Person parseFileToObjects(String fileName) throws IOException, SQLException {

        Person person = new Person.Builder().build();
        FileReader fr = new FileReader(fileName);
        br = new BufferedReader(fr);
        String line = br.readLine();

        while (line != null) {

            if (line.indexOf('{') != -1) {
                parsePersonFromJSON(fileName);
                break;
            }
            if (line.indexOf('<') != -1) {
                parsePersonFromXML(fileName);
                break;
            }
            if (line.indexOf("name:") != -1) {
               person =  parsePersonFromTXT(line);
                if (validate(person, validator)) {
                    this.persons.add(person);
                }
            }
            line = br.readLine();
        }
        return person;
    }

    public Person parsePersonFromTXT(String line) throws IOException, SQLException {

        String name = null;
        int age = 0;
        LocalDate birthDay = null;
        String address = null;
        String email = null;
        String phoneNumber = null;
        String specialization = null;
        List<Experience> experiences = null;

        while (line != null) {
            if (line.contains("name:")) {
                name = line.substring(line.indexOf(':') + 2);
                line = br.readLine();
            }
            if (line.contains("age:")) {
                age = Integer.parseInt(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }
            if (line.contains("birthDay:")) {
                birthDay = LocalDate.parse(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }
            if (line.contains("address:")) {
                address = line.substring(line.indexOf(':') + 2);
                line = br.readLine();
            }
            if (line.contains("email:")) {
                email = line.substring(line.indexOf(':') + 2);
                line = br.readLine();
            }
            if (line.contains("phoneNumber:")) {
                phoneNumber = line.substring(line.indexOf(':') + 2);
                line = br.readLine();
            }
            if (line.contains("specialization:")) {
                specialization = line.substring(line.indexOf(':') + 2);
                line = br.readLine();
            }

            if (line.startsWith("experience")) {
                line = br.readLine();
                experiences = new ArrayList<>();
                Experience experience = new Experience();
                experience = parseExperienceFromTXT(line, experience);
                experiences.add(experience);
            } else
                break;
        }
        Person person = new Person.Builder()
                .withName(name)
                .withAge(age)
                .withBirthDay(birthDay)
                .withAddress(address)
                .withEmail(email)
                .withPhoneNumber(phoneNumber)
                .withSpecialization(specialization)
                .withExperience(experiences)
                .build();
        return person;
    }


    public Experience parseExperienceFromTXT(String line, Experience experience) throws IOException {
        while (line != null) {
            if (line.contains("place:")) {
                experience.setPlace(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }
            if (line.contains("dateFrom:")) {
                experience.setDateFrom(LocalDate.parse(line.substring(line.indexOf(':') + 2)));
                line = br.readLine();
            }
            if (line.contains("dateTo:")) {
                experience.setDateTo(LocalDate.parse(line.substring(line.indexOf(':') + 2)));
                line = br.readLine();
            } else
                break;
        }
        return experience;
    }
    public Person parsePersonFromJSON(String fileName) throws SQLException {

        Person person = new Person.Builder().build();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(fileName));
            person = mapper.readValue(jsonData, Person.class);
            if (validate(person, validator)) {
                personDAO.addPerson(person);
                this.persons.add(person);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read JSON", e);
        }
        return person;
    }

    public Person parsePersonFromXML(String fileName) throws SQLException {

        Person person = new Person.Builder().build();
        try {
            byte[] xmlData = Files.readAllBytes(Paths.get(fileName));
            person = xmlMapper.readValue(xmlData, Person.class);
            if (validate(person, validator)) {
                personDAO.addPerson(person);
                this.persons.add(person);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read XML", e);
        }
        return person;
    }

    public void exportYaml(String fileName, List<Person> persons) throws IOException {
        ObjectMapper mapperYAML = new ObjectMapper(new YAMLFactory());
        File file = new File(fileName);
        try {
            mapperYAML.writeValue(file, persons);
        } catch (JsonGenerationException e) {
            logger.log(Level.SEVERE, "Something went wrong..", e);
        }
    }


}
