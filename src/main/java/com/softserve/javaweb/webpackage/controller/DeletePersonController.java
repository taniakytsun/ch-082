package com.softserve.javaweb.webpackage.controller;

import com.softserve.javaweb.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/person/delete")
public class DeletePersonController extends HttpServlet {

    public DeletePersonController() {
        // Do Nothing
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String personId = request.getParameter("personId");

        if (personId == "" || personId == null)
            request.getRequestDispatcher("/home").forward(request, response);
        else {
            Long id = Long.parseLong(personId);
            PersonService personService = new PersonService();

            personService.delete(id);

            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}