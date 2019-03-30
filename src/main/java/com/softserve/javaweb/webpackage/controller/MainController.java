package com.softserve.javaweb.webpackage.controller;

import com.softserve.javaweb.model.Person;
import com.softserve.javaweb.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cards")
public class MainController extends HttpServlet {

    private PersonService personService = new PersonService();


    public MainController() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Person> personList = personService.readAll();

        request.setAttribute("personList", personList);

        request.getRequestDispatcher("cards.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}