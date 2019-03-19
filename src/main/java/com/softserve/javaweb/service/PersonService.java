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


public class PersonService {

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    ObjectMapper mapper = new ObjectMapper();
    ObjectMapper xmlMapper = new ObjectMapper(new XmlFactory());

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

    public String openFile(String fileName) throws IOException {

        FileReader fr = new FileReader(fileName);
        br = new BufferedReader(fr);
        String line = br.readLine();
        return line;
    }

    public Person parseFileToObjects(String fileName) throws IOException, SQLException {

        Person person = new Person.Builder().build();
        String line = openFile(fileName);

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
                person = parsePersonFromTXT(line);
                if (validate(person, validator)) {
                    this.persons.add(person);
                }
            }
            line = br.readLine();
        }
        return person;
    }

    public Person parsePersonFromTXT(String fileName) throws IOException, SQLException {

        String line = openFile(fileName);

        Person.Builder builder = new Person.Builder();
        while (line != null) {
            if (line.contains("name:")) {
                builder.withName(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }
            if (line.contains("age:")) {
                builder.withAge(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
                line = br.readLine();
            }
            if (line.contains("birthDay:")) {
                builder.withBirthDay(LocalDate.parse(line.substring(line.indexOf(':') + 2)));
                line = br.readLine();
            }
            if (line.contains("address:")) {
                builder.withAddress(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }
            if (line.contains("email:")) {
                builder.withEmail(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }
            if (line.contains("phoneNumber:")) {
                builder.withPhoneNumber(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }
            if (line.contains("specialization:")) {
                builder.withSpecialization(line.substring(line.indexOf(':') + 2));
                line = br.readLine();
            }else
                break;
        }
        if ((line!=null) &&(line.contains("experience"))) {
            line = br.readLine();
            builder.withExperience(parseExperienceFromTXT(line));
        }
        Person person = builder.build();
        return person;
    }


    public List<Experience> parseExperienceFromTXT(String line) throws IOException {
        List <Experience> experiences = new ArrayList<>();

        while (line != null) {
            Experience experience = new Experience();
            if (line.contains("experience")){
                line = br.readLine();
            }
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
            experiences.add(experience); }
        return experiences;
    }

    public Person parsePersonFromJSON(String fileName) throws SQLException {

        Person person = new Person.Builder().build();
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(fileName));
            person = mapper.readValue(jsonData, Person.class);
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
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read XML", e);
        }
        return person;
    }

    public String exportYaml(String fileName, List<Person> persons) throws IOException {
        ObjectMapper mapperYAML = new ObjectMapper(new YAMLFactory());
        File file = new File(fileName);
        String execute = "Something went wrong!";
        try {
            mapperYAML.writeValue(file, persons);
            execute = "Successful!";
        } catch (JsonGenerationException e) {
            logger.log(Level.SEVERE, "Something went wrong..", e);
        }
        return execute;
    }
}
