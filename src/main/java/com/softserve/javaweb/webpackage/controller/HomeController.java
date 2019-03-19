package com.softserve.javaweb.webpackage.controller;
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

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PersonService personService = new PersonService();
    private ExperienceService experienceService = new ExperienceService();


    public HomeController() {
        // Do Nothing
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Person> personList = personService.readAll();
        List<Experience> experiences = experienceService.readAll();

        request.setAttribute("personList", personList);
        request.setAttribute("experiences", experiences);

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
