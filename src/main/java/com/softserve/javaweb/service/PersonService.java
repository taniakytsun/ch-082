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

import com.softserve.javaweb.DAO.PersonDAO;

public class PersonService {

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    ObjectMapper mapper = new ObjectMapper();
    ObjectMapper xmlMapper = new ObjectMapper(new XmlFactory());
    PersonDAO personDAO = new PersonDAO();

    private static Logger logger = Logger.getLogger(PersonService.class.getName());
    List<Person> persons = new ArrayList<>();
    private BufferedReader br;

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

    public Person readFile(String fileName) throws IOException, SQLException {

	Person person = new Person();
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
		parsePersonFromTXT(line);
	    }
	    line = br.readLine();
	}
	exportYaml("src//main//resources//out//person.json", persons);
	return person;
    }

    public Person parsePersonFromTXT(String line) throws IOException, SQLException {

	Person person = new Person();
	while (line != null) {
	    if (line.indexOf("name:") != -1) {
		person.setName(line.substring(line.indexOf(':') + 2));
		line = br.readLine();
	    }
	    if (line.indexOf("age:") != -1) {
		person.setAge(Integer.parseInt(line.substring(line.indexOf(':') + 2)));
		line = br.readLine();
	    }
	    if (line.indexOf("birthDay:") != -1) {
		person.setBirthDay(LocalDate.parse(line.substring(line.indexOf(':') + 2)));
		line = br.readLine();
	    }
	    if (line.indexOf("address:") != -1) {
		person.setAddress(line.substring(line.indexOf(':') + 2));
		line = br.readLine();
	    }
	    if (line.indexOf("email:") != -1) {
		person.setEmail(line.substring(line.indexOf(':') + 2));
		line = br.readLine();
	    }
	    if (line.indexOf("phoneNumber:") != -1) {
		person.setPhoneNumber(line.substring(line.indexOf(':') + 2));
		line = br.readLine();
	    }
	    if (line.indexOf("specialization:") != -1) {
		person.setSpecialization(line.substring(line.indexOf(':') + 2));
		line = br.readLine();
	    }
	    if (line.startsWith("experience")) {
		Experience experience = new Experience();
		line = br.readLine();
		while (line != null) {
		    if (line.indexOf("place:") != -1) {
			experience.setPlace(line.substring(line.indexOf(':') + 2));
			line = br.readLine();
		    }
		    if (line.indexOf("dateFrom:") != -1) {
			experience.setDateFrom(LocalDate.parse(line.substring(line.indexOf(':') + 2)));
			line = br.readLine();
		    }
		    if (line.indexOf("dateTo:") != -1) {
			experience.setDateTo(LocalDate.parse(line.substring(line.indexOf(':') + 2)));
			line = br.readLine();
		    } else
			break;
		}
		person.getExperience().add(experience);
	    } else
		break;
	}
	if (validate(person, validator)) {
	   // personDAO.addPerson(person);
	    this.persons.add(person);
	}
	return person;
    }

    public Person parsePersonFromJSON(String fileName) throws SQLException {

	Person person = new Person();
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

    public void exportJson(String fileName, Person person) {

	File file = new File(fileName);
	try {
	    mapper.writeValue(file, person);
	} catch (IOException e) {
	    logger.log(Level.SEVERE, "Can't write JSON", e);
	}
    }

    public Person parsePersonFromXML(String fileName) throws SQLException {

	Person person = new Person();
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

    public void exportXML(String fileName, Person person) throws IOException {

	xmlMapper.writeValue(new File(fileName), person);
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
