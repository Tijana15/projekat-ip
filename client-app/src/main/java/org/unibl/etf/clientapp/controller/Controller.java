package org.unibl.etf.clientapp.controller;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controller", value = "/Controller")
public class Controller extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        System.out.println("GET Action = " + action);

        session.setAttribute("notification", session.getAttribute("notification") != null ? session.getAttribute("notification") : "");


        if ("register".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
        }
    }



    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}