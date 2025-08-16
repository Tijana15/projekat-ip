<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.clientapp.dto.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${requestScope.pageTitle} - Urban Motion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/vehicle-list-style.css">
</head>
<body>
<div class="container">
    <header class="header">
        <div class="logo-container">
            <div class="logo-placeholder">
                <img class="logo-img" src="${pageContext.request.contextPath}/images/logo.png" alt="Urban Motion Logo">
            </div>
            <div class="brand-name">Urban Motion</div>
        </div>
        <nav>
            <a href="Controller?action=home" class="back-link">← Back to Home</a>
            <a href="Controller?action=logout" class="logout-link">Logout</a>
        </nav>
    </header>

    <main class="list-main-content">
        <h1>${requestScope.pageTitle}</h1>
        <p>Choose your ride and start your journey!</p>

        <div class="vehicle-grid">
            <%
                List<?> vehicles = (List<?>) request.getAttribute("vehicles");

                if (vehicles == null || vehicles.isEmpty()) {
            %>
            <div class="no-vehicles-message floating">
                <h3>🚫 No Available Vehicles</h3>
                <p>Sorry, no vehicles of this type are currently available.</p>
                <p>Please check back later or try a different vehicle type.</p>
            </div>
            <%
            } else {
                int cardIndex = 1;
                for (Object vehicleObj : vehicles) {
            %>
            <div class="vehicle-card" style="--delay: <%= cardIndex * 0.1 %>s">
                <div class="vehicle-image-placeholder">
                    <%
                        String vehicleEmoji = "🚗";
                        if (vehicleObj instanceof EBike) {
                            vehicleEmoji = "🚲";
                        } else if (vehicleObj instanceof EScooter) {
                            vehicleEmoji = "🛴";
                        }
                    %>
                    <%= vehicleEmoji %>
                </div>

                <div class="vehicle-details">
                    <%
                        String vehicleId = "";
                        String vehicleTitle = "";
                        String vehicleSpec = "";

                        if (vehicleObj instanceof Car) {
                            Car item = (Car) vehicleObj;
                            vehicleId = item.getId();
                            vehicleTitle = item.getManufacturer() + " " + item.getModel();
                            vehicleSpec = "Premium Car • Available Now";
                    %>
                    <h3><%= vehicleTitle %>
                    </h3>
                    <p class="spec"><%= vehicleSpec %>
                    </p>
                    <%
                    } else if (vehicleObj instanceof EBike) {
                        EBike item = (EBike) vehicleObj;
                        vehicleId = item.getId();
                        vehicleTitle = item.getManufacturer() + " " + item.getModel();
                        vehicleSpec = "Max Range: " + item.getMaxRange() + " km";
                    %>
                    <h3><%= vehicleTitle %>
                    </h3>
                    <p class="spec"><%= vehicleSpec %>
                    </p>
                    <%
                    } else if (vehicleObj instanceof EScooter) {
                        EScooter item = (EScooter) vehicleObj;
                        vehicleId = item.getId();
                        vehicleTitle = item.getManufacturer() + " " + item.getModel();
                        vehicleSpec = "Max Speed: " + item.getMaxSpeed() + " km/h";
                    %>
                    <h3><%= vehicleTitle %>
                    </h3>
                    <p class="spec"><%= vehicleSpec %>
                    </p>
                    <%
                        }
                    %>

                </div>

                <a href="Controller?action=show-rental-form&vehicleId=<%= vehicleId %>"
                   class="btn">
                    🚀 Rent now
                </a>
            </div>
            <%
                        cardIndex++;
                    }
                }
            %>
        </div>
    </main>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const cards = document.querySelectorAll('.vehicle-card');

        cards.forEach((card, index) => {
            card.addEventListener('mouseenter', function () {
                this.style.zIndex = '10';
            });

            card.addEventListener('mouseleave', function () {
                this.style.zIndex = '1';
            });
        });

        const buttons = document.querySelectorAll('.btn');
        buttons.forEach(button => {
            button.addEventListener('click', function () {
                this.style.transform = 'translateY(2px) scale(0.98)';
                setTimeout(() => {
                    this.style.transform = '';
                }, 150);
            });
        });
    });
</script>
</body>
</html>