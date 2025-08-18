<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 15.8.2025.
  Time: 10:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Urban Motion - Home</title>
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

        <div class="logout-container">
            <a href="Controller?action=logout" class="logout-btn">Logout</a>
        </div>
    </header>

    <%
        String notification = (String) session.getAttribute("notification");
        Long lastRentalId = (Long) session.getAttribute("lastRentalId");
        if (notification != null) {
    %>
    <div class="notification success">
        <%= notification %>
        <% if (lastRentalId != null) { %>
        <br>
        <a href="Controller?action=download-invoice&rentalId=<%= lastRentalId %>" class="download-link">
            Download Invoice (PDF)
        </a>
        <% } %>
    </div>
    <%
            session.removeAttribute("notification");
            session.removeAttribute("lastRentalId");
        }
    %>
    <main class="main-content">
        <div class="dashboard-wrapper">
            <div class="dashboard-header">
                <h1>Choose your vehicle</h1>
                <p>Select from our premium fleet of rental options</p>
            </div>

            <div class="options-grid">
                <div class="option-card floating">
                    <div class="option-icon">
                        <span class="vehicle-emoji">🛴</span>
                    </div>
                    <h3>E-Scooter rental</h3>
                    <p>Quick and eco-friendly city rides</p>
                    <ul class="feature-list">
                        <li>✓ Up to 30 km/h</li>
                        <li>✓ 30km range</li>
                        <li>✓ Perfect for short trips</li>
                    </ul>
                    <a href="Controller?action=scooter-rental" class="option-btn">Rent e-scooter</a>
                </div>

                <div class="option-card floating">
                    <div class="option-icon">
                        <span class="vehicle-emoji">🚲</span>
                    </div>
                    <h3>E-Bike rental</h3>
                    <p>Powered cycling for longer journeys</p>
                    <ul class="feature-list">
                        <li>✓ Electric assistance</li>
                        <li>✓ 70km range</li>
                        <li>✓ Fitness & fun combined</li>
                    </ul>
                    <a href="Controller?action=bike-rental" class="option-btn">Rent e-bike</a>
                </div>

                <div class="option-card floating">
                    <div class="option-icon">
                        <span class="vehicle-emoji">🚗</span>
                    </div>
                    <h3>Car rental</h3>
                    <p>Comfortable rides for any distance</p>
                    <ul class="feature-list">
                        <li>✓ Various models</li>
                        <li>✓ Full insurance</li>
                        <li>✓ 24/7 support</li>
                    </ul>
                    <a href="Controller?action=car-rental" class="option-btn">Rent car</a>
                </div>

                <div class="option-card floating">
                    <div class="option-icon">
                        <span class="vehicle-emoji">👤</span>
                    </div>
                    <h3>My profile</h3>
                    <p>Manage your account and rental history</p>
                    <ul class="feature-list">
                        <li>✓ View rental history</li>
                        <li>✓ Update personal info</li>
                        <li>✓ Payment methods</li>
                        <li>x Profile deactivation</li>
                    </ul>
                    <a href="Controller?action=profile" class="option-btn">View profile</a>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const cards = document.querySelectorAll('.option-card');

        cards.forEach((card, index) => {
            card.style.animationDelay = `${index * 0.1}s`;

            card.addEventListener('mouseenter', function () {
                this.style.transform = 'translateY(-10px) scale(1.02)';
            });

            card.addEventListener('mouseleave', function () {
                this.style.transform = 'translateY(0) scale(1)';
            });
        });
    });
</script>
</body>
</html>