<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 17.8.2025.
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.unibl.etf.clientapp.dto.Rental" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="org.unibl.etf.clientapp.dao.RentalPriceConfigDAO" %>
<%
    Rental activeRental = (Rental) session.getAttribute("activeRental");
    if (activeRental == null) {
        response.sendRedirect("Controller?action=home");
        return;
    }

    String vehicleType = activeRental.getVehicleType();
    double pricePerMinute = RentalPriceConfigDAO.getPriceByVehicleType(vehicleType);
    double pricePerSecond = pricePerMinute / 60.0;


    String startTimeString = activeRental.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    long startTimeMillis = activeRental.getDateTime().atZone(java.time.ZoneId.of("Europe/Sarajevo")).toInstant().toEpochMilli();

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Active Ride - Urban Motion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/active-ride-style.css">
</head>
<body>

<div class="ride-container floating">
    <div class="header">
        <h1>Ride in Progress</h1>
        <p>Started at: <strong><%= startTimeString %>
        </strong></p>
    </div>
    <div class="display-panel">
        <div class="display-box">
            <span class="label">DURATION</span>
            <span id="timer" class="value">00:00:00</span>
        </div>
        <div class="display-box">
            <span class="label">CURRENT PRICE</span>
            <span id="price" class="value">0.00 $</span>
        </div>
    </div>
    <form action="Controller" method="post" onsubmit="return confirm('Are you sure you want to end the ride?');">
        <input type="hidden" name="action" value="end-ride">
        <input type="hidden" name="durationSeconds" id="durationSeconds" value="0">
        <button type="submit" class="btn-end-ride">End ride</button>
    </form>
</div>

<script>
    const startTime = <%= startTimeMillis %>;
    const pricePerSecond = <%= pricePerSecond %>;

    const timerSpan = document.getElementById('timer');
    const priceSpan = document.getElementById('price');
    const durationInput = document.getElementById('durationSeconds');

    const updateTimer = setInterval(function () {
        const now = new Date().getTime();
        let durationMillis = now - startTime;

        if (durationMillis < 0) {
            durationMillis = 0;
        }

        const totalSeconds = Math.floor(durationMillis / 1000);

        durationInput.value = totalSeconds;

        const hours = Math.floor(totalSeconds / 3600);
        const minutes = Math.floor((totalSeconds % 3600) / 60);
        const seconds = totalSeconds % 60;

        timerSpan.textContent = `\${String(hours).padStart(2, '0')}:\${String(minutes).padStart(2, '0')}:\${String(seconds).padStart(2, '0')}`;
        const currentPrice = totalSeconds * pricePerSecond;
        priceSpan.textContent = currentPrice.toFixed(2) + " $";

    }, 1000);
</script>

</body>
</html>
