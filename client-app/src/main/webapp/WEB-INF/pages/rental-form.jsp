<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 16.8.2025.
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.unibl.etf.clientapp.dto.*" %>
<html>
<head>
    <title>Confirm Rental - Urban Motion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/rental-form-style.css">
</head>
<body>
<div class="container">
    <header class="header">
    </header>

    <%
        Object vehicleObj = request.getAttribute("vehicle");
        double price = (Double) request.getAttribute("price");
        String vehicleId = "";
        String vehicleName = "";

        if (vehicleObj instanceof Car) {
            Car item = (Car) vehicleObj;
            vehicleId = item.getId();
            vehicleName = item.getManufacturer() + " " + item.getModel();
        } else if (vehicleObj instanceof EBike) {
            EBike item = (EBike) vehicleObj;
            vehicleId = item.getId();
            vehicleName = item.getManufacturer() + " " + item.getModel();
        } else if (vehicleObj instanceof EScooter) {
            EScooter item = (EScooter) vehicleObj;
            vehicleId = item.getId();
            vehicleName = item.getManufacturer() + " " + item.getModel();
        }
    %>

    <main class="form-container">
        <div class="summary-card">
            <h2>You are renting:</h2>
            <h3><%= vehicleName %>
            </h3>
            <div class="price-display">
                Price: <span><%= String.format("%.2f", price) %> KM/min</span>
            </div>
        </div>

        <div class="payment-card">
            <h2>Payment Details (Demonstrative)</h2>
            <p>These details will not be saved.</p>

            <form action="Controller" method="post">
                <input type="hidden" name="action" value="start-ride">
                <input type="hidden" name="vehicleId" value="<%= vehicleId %>">

                <div class="form-group">
                    <label for="cardNumber">Card Number</label>
                    <input type="text" id="cardNumber" placeholder="0000 0000 0000 0000" required>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="expiryDate">Expiry Date</label>
                        <input type="text" id="expiryDate" placeholder="MM/YY" required>
                    </div>
                    <div class="form-group">
                        <label for="cvc">CVC</label>
                        <input type="text" id="cvc" placeholder="123" required>
                    </div>
                </div>

                <button type="submit" class="btn">Start Ride</button>
            </form>
        </div>
    </main>
</div>
</body>
</html>
