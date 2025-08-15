package org.unibl.etf.clientapp.controller;

import org.unibl.etf.clientapp.bean.UserBean;
import org.unibl.etf.clientapp.dao.UserDAO;
import org.unibl.etf.clientapp.dto.User;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "controller", value = "/Controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if (action == null) {
            action = "login";
        }
        switch (action) {
            case "register":
                request.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(request, response);
                break;
            case "login":
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                break;
            case "home":
                if (session.getAttribute("userBean") != null) {
                    request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);
                } else {
                    response.sendRedirect("Controller?action=login");
                }
                break;
            case "logout":
                session.invalidate();
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute("userBean");


        System.out.println("Action here in post is " + action);

        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UserBean user = new UserBean();
            if (user.login(username, password)) {
                session.setAttribute("userBean", user);
                response.sendRedirect("Controller?action=home");

            } else {
                session.setAttribute("notification", "Invalid credentials");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            }
        } else if (action.equals("register")) {
            String errorMessage = null;
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String id_document = request.getParameter("id_document");
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()
                    || email == null || email.trim().isEmpty() || firstname == null || firstname.trim().isEmpty()) {
                errorMessage = "All fields are required.";
            } else if (UserDAO.existsByUsername(username)) {
                errorMessage = "Username taken. Change and try again.";
            }
            if (errorMessage != null) {
                session.setAttribute("errorMessage", errorMessage);
                response.sendRedirect(request.getContextPath() + "/Controller?action=register");
            } else {
                User newUser = new User(firstname, lastname, username, password, "CLIENT", false, email, phone, id_document);
                UserBean registrationBean = new UserBean();
                boolean registrationSuccess = registrationBean.register(newUser);
                if (registrationSuccess) {
                    session.removeAttribute("errorMessage");
                    session.setAttribute("registrationSuccess", "Account created successfully! Please login.");
                    response.sendRedirect(request.getContextPath() + "/Controller?action=login");
                } else {
                    session.setAttribute("errorMessage", "Registration failed. Please try again.");
                    response.sendRedirect(request.getContextPath() + "/Controller?action=register");

                }
            }
        } else if ("logout".equals(action)) {
            response.sendRedirect(request.getContextPath() + "/Controller?action=logout");
        }

    }
}
