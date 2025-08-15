<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 14.8.2025.
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Urban Motion - Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/registration-style.css">
</head>
<body>
<div class="container">
    <header class="header">
        <div class="logo-container">
            <div class="logo-placeholder">
                <img class="logo-placeholder img" src="${pageContext.request.contextPath}/images/logo.png">
            </div>
            <div class="brand-name">Urban Motion</div>
        </div>
    </header>

    <main class="main-content">
        <div class="content-wrapper">
            <div class="hero-section floating">
                <h2>Welcome to Urban Motion</h2>
                <p>Where on-wheels adventure begins</p>
                <ul class="service-list">
                    <li>
                        <div class="service-icon">🚗</div>
                        <span class="service-text">Rent a car</span>
                    </li>
                    <li>
                        <div class="service-icon">🚲</div>
                        <span class="service-text">Rent an e-bike</span>
                    </li>
                    <li>
                        <div class="service-icon">🛴</div>
                        <span class="service-text">Rent an e-scooter</span>
                    </li>
                </ul>
            </div>

            <div class="registration-section">
                <h2>Register</h2>
                <p>Access all privileges of Urban Motion</p>
                <form action="Controller" method="post">
                    <input type="hidden" name="action" value="register">
                    <% if (session.getAttribute("errorMessage") != null) { %>
                    <div class="error-message" style="display: block;">
                        <%= session.getAttribute("errorMessage") %>
                    </div>
                    <% session.removeAttribute("errorMessage"); %>
                    <% } %>


                    <div class="form-grid">
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" placeholder="Enter username" required>
                        </div>

                        <div class="form-group">
                            <label for="password">Password</label>
                            <%-- PROMJENA: type="password" da sakrije unos --%>
                            <input type="password" name="password" id="password" placeholder="Enter password" required>
                        </div>

                        <div class="form-group">
                            <label for="firstname">First Name</label>
                            <input type="text" name="firstname" id="firstname" placeholder="Enter first name" required>
                        </div>

                        <div class="form-group">
                            <label for="lastname">Last Name</label>
                            <input type="text" name="lastname" id="lastname" placeholder="Enter last name" required>
                        </div>

                        <div class="form-group full-width">
                            <label for="email">Email</label>
                            <input type="email" name="email" id="email" placeholder="Enter email" required>
                        </div>

                        <div class="form-group full-width">
                            <label for="id_document">Identification Document</label>
                            <input type="text" name="id_document" id="id_document" placeholder="Enter ID document number" required>
                        </div>

                        <div class="form-group full-width">
                            <label for="phone">Phone</label>
                            <input type="tel" name="phone" id="phone" placeholder="Enter phone number" required>
                        </div>
                    </div>

                    <button type="submit" class="submit-btn">Register</button>
                </form>

                <div class="register-link-link">
                    <p>Already have an account? <a href="Controller?action=login">Login here</a></p>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>