package com.softserve.javaweb.webpackage.servlet;

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

@WebServlet(urlPatterns = "/add")
public class AddPersonServlet extends HttpServlet {

    PersonService personService = new PersonService();

    public AddPersonServlet() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/add.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {


        System.out.println();
        Person.Builder builder = new Person.Builder();
        builder.withName(req.getParameter("name"));
        builder.withAge(Integer.parseInt(req.getParameter("age")));
        builder.withBirthDay(LocalDate.parse(req.getParameter("birthday")));
        builder.withAddress(req.getParameter("address"));
        builder.withEmail(req.getParameter("email"));
        builder.withPhoneNumber(req.getParameter("phonenumber"));
        builder.withSpecialization(req.getParameter("specialization"));
        Person person = builder.build();

        personService.createPerson(person);

    }

}
