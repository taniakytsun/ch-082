package com.softserve.javaweb.webpackage.servlet;

import com.softserve.javaweb.dao.PersonDAO;
import com.softserve.javaweb.model.Person;
import com.softserve.javaweb.service.PersonService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class GetPersonServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GetPersonServlet.class.getName());

    PersonService personService = new PersonService();
    List<Person> personList = new ArrayList<>();



    @Override
    public void init(ServletConfig config){
            personList  = personService.readAll();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("personList", personList);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
            doGet(request, response);
        }
    }

