<%-- Importujemo sve Java klase koje će nam trebati unutar ovog JSP-a --%>
<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.clientapp.dto.Rental" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - Urban Motion</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/profile-style.css">
</head>
<body>

<div class="container">
    <header class="header">
        <div class="logo-container">
            <div class="logo-placeholder">
                <img class="logo-placeholder img" src="${pageContext.request.contextPath}/images/logo.png" alt="">
            </div>
            <div class="brand-name">Urban Motion</div>
        </div>
        <nav>
            <a href="Controller?action=logout" class="logout-link">Logout</a>
        </nav>
    </header>

    <main class="profile-content">
        <h1>Welcome!</h1>
        <p>Manage your account details and view your rental history below.</p>


        <%
            if (session.getAttribute("profileMessage") != null) {
        %>
        <div class="message">
            <%= session.getAttribute("profileMessage") %>
        </div>
        <% session.removeAttribute("profileMessage");
        }
        %>

        <div class="profile-sections">

            <section class="card">
                <h2>Change Password</h2>
                <form action="Controller" method="post">
                    <input type="hidden" name="action" value="changePassword">
                    <div class="form-group">
                        <label for="oldPassword">Old Password:</label>
                        <input type="password" id="oldPassword" name="oldPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">New Password:</label>
                        <input type="password" id="newPassword" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm New Password:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                    </div>
                    <button type="submit" class="btn">Change Password</button>
                </form>
            </section>

            <section class="card wide-card">
                <h2>My Rentals</h2>
                <table class="rentals-table">
                    <thead>
                    <tr>
                        <th>Vehicle</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Rental> rentals = (List<Rental>) request.getAttribute("rentals");

                        if (rentals == null || rentals.isEmpty()) {
                    %>
                    <tr>
                        <td colspan="4">You have no recorded rentals.</td>
                    </tr>
                    <%
                    } else {
                        for (Rental rental : rentals) {
                    %>
                    <tr>
                        <td><%= rental.getVehicleId() %>
                        </td>
                        <td><%= rental.getDateTime() %>
                        </td>
                        <td><%= rental.getDurationSeconds() %>
                        </td>
                        <td><%= rental.getPrice() %> $</td>
                    </tr>
                    <%
                            }
                        }
                    %>
                    </tbody>
                </table>
            </section>

            <section class="card danger-zone wide-card">
                <h2>Deactivate account</h2>
                <p>This action is permanent and cannot be undone. Your account will be blocked and to access again you need to create new account.</p>
                <form action="Controller" method="post"
                      onsubmit="return confirm('Are you sure you want to deactivate your account?');">
                    <input type="hidden" name="action" value="deactivateAccount">
                    <button type="submit" class="btn btn-danger">Deactivate account</button>
                </form>
            </section>

        </div>
    </main>
</div>

</body>
</html>