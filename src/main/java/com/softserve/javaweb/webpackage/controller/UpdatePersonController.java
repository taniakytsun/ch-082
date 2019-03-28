package com.softserve.javaweb.webpackage.controller;

import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;
import com.softserve.javaweb.service.ExperienceService;
import com.softserve.javaweb.service.PersonService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/person/update")
public class UpdatePersonController extends HttpServlet {
    ExperienceService experienceService = new ExperienceService();

    public UpdatePersonController() {
        // Do Nothing
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/home");
        requestDispatcher.forward(request, response);
        String personId = request.getParameter("personId");
        List<Experience> experiences = experienceService.readAllByPersonId(Long.parseLong(personId));
        request.setAttribute("experiencesByPerson", experiences);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String personId = request.getParameter("personId");

        if (personId == "" || personId == null)
            request.getRequestDispatcher("/home").forward(request, response);
        else {
            List<Experience> experiencesByPerson = experienceService.readAllByPersonId(Long.parseLong(personId));
            request.setAttribute("experiencesByPerson", experiencesByPerson);
            Long id = Long.parseLong(personId);
            PersonService personService = new PersonService();
            Person person = personService.readOne(id);

            request.setAttribute("person", person);

            request.getRequestDispatcher("/home").forward(request, response);
        }
    }
}

