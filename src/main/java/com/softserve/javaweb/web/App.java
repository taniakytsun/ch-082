package com.softserve.javaweb.web;

import com.softserve.javaweb.DAO.PersonDAO;
import com.softserve.javaweb.service.PersonService;

public class App {
	public static void main(String[] args) throws Exception {

		PersonService service = new PersonService();
		service.readFile("src//main//resources//person.xml");
		PersonDAO personDAO = new PersonDAO();
		//personDAO.getAllPersonsWithoutExperience();
		personDAO.getPersonByName("Uliana Tkacuk");
		personDAO.deletePersonById((long) 113);
		personDAO.deleteExperienceByPersonId((long)1,(long)1);
	}
}