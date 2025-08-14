<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 14.8.2025.
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Urban Motion - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login-style.css">


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
                <div class="hero-content">
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

            <div class="login-section">
                <h2>Sign In</h2>
                <p>Access your Urban Motion account</p>

                <form action="?action=auth" method="post">
                    <div id="error-message" class="error-message">
                        <!-- Error messages will appear here -->
                    </div>

                    <div class="form-group">
                        <label for="username">Username</label>
                        <div class="input-wrapper">
                            <input type="text" id="username" name="username" placeholder="Enter your username" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <div class="input-wrapper">
                            <input type="password" id="password" name="password" placeholder="Enter your password" required>
                        </div>
                    </div>

                    <button type="submit" class="submit-btn">Sign In</button>
                </form>

                <div class="register-link">
                    <p>Don't have an account? <a href="?action=register">Register here</a></p>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>