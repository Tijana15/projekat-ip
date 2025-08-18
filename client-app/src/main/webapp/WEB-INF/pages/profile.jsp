<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.clientapp.dto.Rental" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="Urban Motion Logo">
            </div>
            <div class="brand-name">Urban Motion</div>
        </div>
        <nav>
            <a href="Controller?action=home" class="back-link">← Back to home</a>
            <a href="Controller?action=logout" class="logout-link">Logout</a>
        </nav>
    </header>

    <main class="profile-content">
        <h1>My profile</h1>
        <p>Manage your account and view your rental history</p>

        <%
            if (session.getAttribute("profileMessage") != null) {
        %>
        <div class="message">
            🎉 <%= session.getAttribute("profileMessage") %>
        </div>
        <% session.removeAttribute("profileMessage");
        }
        %>

        <div class="profile-sections">
            <section class="card">
                <h2>My Avatar</h2>
                <div class="avatar-preview">
                    <img src="avatars/${sessionScope.userBean.user.avatarPicture != null ? sessionScope.userBean.user.avatarPicture : 'default.png'}" alt="Your avatar">
                </div>
                <form action="Controller" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="uploadAvatar">
                    <div class="form-group">
                        <label for="avatar">Choose a new image:</label>
                        <input type="file" id="avatar" name="avatar" accept="image/png, image/jpeg" required>
                    </div>
                    <button type="submit" class="btn">Upload Avatar</button>
                </form>
            </section>

            <section class="card wide-card floating">
                <h2>🚀 My rentals</h2>
                <div class="table-wrapper">
                    <table class="rentals-table">
                        <thead>
                        <tr>
                            <th>🚗 Vehicle ID</th>
                            <th>📅 Start date & time</th>
                            <th>⏱️ Duration</th>
                            <th>💰 Total price</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Rental> rentals = (List<Rental>) request.getAttribute("rentals");

                            if (rentals == null || rentals.isEmpty()) {
                        %>
                        <tr>
                            <td colspan="4">
                                <div style="text-align: center; padding: 2rem;">
                                    <div style="font-size: 3rem; margin-bottom: 1rem;">🚫</div>
                                    <strong>No rentals yet!</strong><br>
                                    <span style="color: #6B7280;">Start your first journey with Urban Motion.</span>
                                </div>
                            </td>
                        </tr>
                        <%
                        } else {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

                            for (Rental rental : rentals) {
                                int totalSeconds = rental.getDurationSeconds();
                                int minutes = totalSeconds / 60;
                                int remainingSeconds = totalSeconds % 60;
                                String durationFormatted = minutes + " min " + remainingSeconds + " sec";

                                String priceFormatted = String.format("%.2f $", rental.getPrice());
                        %>
                        <tr>
                            <td><strong><%= rental.getVehicleId() %></strong></td>
                            <td><%= rental.getDateTime().format(formatter) %></td>
                            <td><%= durationFormatted %></td>
                            <td><strong style="color: #10B981;"><%= priceFormatted %></strong></td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </section>

            <section class="card">
                <h2>🔒 Change password</h2>
                <form action="Controller" method="post">
                    <input type="hidden" name="action" value="changePassword">
                    <div class="form-group">
                        <label for="oldPassword">Current password:</label>
                        <input type="password" id="oldPassword" name="oldPassword"
                               placeholder="Enter your current password" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">New password:</label>
                        <input type="password" id="newPassword" name="newPassword"
                               placeholder="Enter your new password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm new password:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword"
                               placeholder="Confirm your new password" required>
                    </div>
                    <button type="submit" class="btn">🔐 Update password</button>
                </form>
            </section>

            <section class="card danger-zone wide-card">
                <h2>⚠️ Danger zone</h2>
                <p style="color: #6B7280; margin-bottom: 1.5rem;">
                    <strong>Account deactivation:</strong> This action is permanent and cannot be undone.
                    Your account will be blocked and you'll need to create a new account to access Urban Motion again.
                </p>
                <form action="Controller" method="post"
                      onsubmit="return confirm('⚠️ Are you absolutely sure you want to deactivate your account? This action cannot be undone!');">
                    <input type="hidden" name="action" value="deactivateAccount">
                    <button type="submit" class="btn btn-danger">🗑️ Deactivate account</button>
                </form>
            </section>

        </div>
    </main>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const cards = document.querySelectorAll('.card');

        cards.forEach((card, index) => {
            card.style.animationDelay = `${index * 0.1}s`;

            card.addEventListener('mouseenter', function() {
                this.style.zIndex = '10';
            });

            card.addEventListener('mouseleave', function() {
                this.style.zIndex = '1';
            });
        });

        const passwordForm = document.querySelector('form[action*="changePassword"]');
        if (passwordForm) {
            passwordForm.addEventListener('submit', function(e) {
                const newPassword = document.getElementById('newPassword').value;
                const confirmPassword = document.getElementById('confirmPassword').value;

                if (newPassword !== confirmPassword) {
                    e.preventDefault();
                    alert('🚫 New passwords do not match!');
                }
            });
        }
    });
</script>

</body>
</html>