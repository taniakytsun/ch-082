package com.softserve.javaweb.webpackage.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;
import com.softserve.javaweb.service.ExperienceService;
import com.softserve.javaweb.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/details")
public class PersonDetailsController extends HttpServlet {
    private PersonService personService = new PersonService();
    private ExperienceService experienceService = new ExperienceService();
    private ObjectMapper mapper = new ObjectMapper();

    public PersonDetailsController(){

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Person> personList = personService.readAll();
        List<Experience> experiences = experienceService.readAll();

        String jsonList = mapper.writeValueAsString(personList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonList);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
