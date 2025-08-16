package org.unibl.etf.clientapp.controller;

import org.unibl.etf.clientapp.bean.UserBean;
import org.unibl.etf.clientapp.bean.VehicleBean;
import org.unibl.etf.clientapp.dao.UserDAO;
import org.unibl.etf.clientapp.dto.Car;
import org.unibl.etf.clientapp.dto.EBike;
import org.unibl.etf.clientapp.dto.EScooter;
import org.unibl.etf.clientapp.dto.User;

import java.io.*;
import java.util.List;

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
                session = request.getSession(true);
                session.setAttribute("notification", "You have been successfully logged out.");
                request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
                break;
            case "profile":
                request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
                break;
            case "car-rental":
                showCarRentalPage(request, response);
                break;
            case "bike-rental":
                showBikeRentalPage(request, response);
                break;
            case "scooter-rental":
                showScooterRentalPage(request, response);
                break;

        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
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
        } else if ("changePassword".equals(action)) {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            UserBean userBean = (UserBean) session.getAttribute("userBean");

            String statusMessage = null;
            boolean success = false;

            if (newPassword == null || newPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
                statusMessage = "Error: Both password fields are required.";
            }
            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("profileMessage", "Error. Password does not match.");
            } else {
                success = userBean.changePassword(oldPassword, newPassword);
                if (success) {
                    statusMessage = "Success: Password changed successfully!";
                    System.out.println("Password changed for user ID: " + userBean.getId());
                } else {
                    statusMessage = "Error: Could not change password due to a server error.";
                    System.err.println("Failed to change password for user ID: " + userBean.getId());
                }
            }
            session.setAttribute("passwordChangeMessage", statusMessage);
            response.sendRedirect(request.getContextPath() + "/Controller?action=profile");
        } else if ("deactivateAccount".equals(action)) {
            UserBean userBean = (UserBean) session.getAttribute("userBean");
            boolean success = userBean.deactivateAccount();
            if (success) {
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("notification", "Your account has been successfully deactivated.");
                response.sendRedirect("Controller?action=login");
            } else {
                session.setAttribute("profileMessage", "Error: Account deactivation failed. Please try again.");
                response.sendRedirect("Controller?action=profile");
            }
        }

    }

    private void showScooterRentalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userBean") != null) {
            VehicleBean vehicleBean = new VehicleBean();
            List<EScooter> eScooters = vehicleBean.getAvailableEScooters();
            request.setAttribute("vehicles", eScooters);
            request.setAttribute("pageTitle", "Available E-Scooters");
            request.getRequestDispatcher("/WEB-INF/pages/vehicle-list.jsp").forward(request, response);
        } else {
            response.sendRedirect("Controller?action=login");
        }
    }

    private void showCarRentalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userBean") != null) {
            VehicleBean vehicleBean = new VehicleBean();
            List<Car> cars = vehicleBean.getAvailableCars();
            request.setAttribute("vehicles", cars);
            request.setAttribute("pageTitle", "Available Cars");
            request.getRequestDispatcher("/WEB-INF/pages/vehicle-list.jsp").forward(request, response);
        } else {
            response.sendRedirect("Controller?action=login");
        }
    }

    private void showBikeRentalPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userBean") != null) {
            VehicleBean vehicleBean = new VehicleBean();
            List<EBike> eBikes = vehicleBean.getAvailableEBikes();
            request.setAttribute("vehicles", eBikes);
            request.setAttribute("pageTitle", "Available E-Bikes");
            request.getRequestDispatcher("/WEB-INF/pages/vehicle-list.jsp").forward(request, response);
        } else {
            response.sendRedirect("Controller?action=login");
        }
    }
}
