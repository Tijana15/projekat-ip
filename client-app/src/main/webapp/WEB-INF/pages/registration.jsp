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
        </div>
        <div class="registration-section">
            <h2>Register</h2>
            <p>Access all privileges of Urban Motion</p>
            <form action="?action=register" method="post">
                <div id="error-message" class="error-message">

                </div>

                <div class="form-group">
                    <label for="username">Username</label>
                    <div class="input-wrapper">
                        <input type="text" id="username" name="username" placeholder="Insert username" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="firstname">Firstname</label>
                    <div class="input-wrapper">
                        <input type="text" name="firstname" id="firstname" placeholder="Insert firstname" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="lastname">Lastname</label>
                    <div class="input-wrapper">
                        <input type="text" name="lastname" id="lastname" placeholder="Insert lastname" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="input-wrapper">
                        <input type="text" name="password" id="password" placeholder="Insert password" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <div class="input-wrapper">
                        <input type="text" name="email" id="email" placeholder="Insert email" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="id_document">Identification document</label>
                    <div class="input-wrapper">
                        <input type="text" name="id_document" id="id_document"
                               placeholder="Insert identification document" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <div class="input-wrapper">
                        <input type="text" name="phone" id="phone" placeholder="Insert phone number" required>
                    </div>
                </div>
                <button type="submit" class="submit-btn">Register</button>
            </form>
            <div class="register-link-link">
                <p>Don't have an account? <a href="?action=login">Login here</a></p>
            </div>
        </div>
    </main>
</div>


</body>
</html>
