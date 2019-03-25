package com.softserve.javaweb.webpackage.controller;

import com.softserve.javaweb.model.Person;
import com.softserve.javaweb.service.PersonService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = "/person/register")
public class AddUpdatePersonController extends HttpServlet {

    PersonService personService = new PersonService();

    public AddUpdatePersonController() {

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String personId = req.getParameter("id");
        System.out.println();
        Person.Builder builder = new Person.Builder();
        builder.withName(req.getParameter("name"));

        System.out.println(req.getParameter("name"));
        System.out.println(req.getParameter("age"));
        builder.withAge(Integer.parseInt(req.getParameter("age")));
        builder.withBirthDay(LocalDate.parse(req.getParameter("birthDay")));
        builder.withAddress(req.getParameter("address"));
        builder.withEmail(req.getParameter("email"));
        builder.withPhoneNumber(req.getParameter("phonenumber"));
        builder.withSpecialization(req.getParameter("specialization"));
        Person person = builder.build();

        if (personId == null || personId == "")
            personService.createPerson(person);
        else {
            Long id = Long.parseLong(personId);
            builder.withId(id);
            person = builder.build();
            personService.update(person);
        }

        resp.sendRedirect(req.getContextPath() + "/home");
    }

}
