<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="userBean" class="org.unibl.etf.promotionsapp.beans.UserBean" scope="session"/>
<jsp:setProperty name="userBean" property="*"/>

<%
    if (userBean.isLoggedIn()) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    String action = request.getParameter("action");
    if ("login".equals(action)) {
        String result = userBean.login();
        if ("success".equals(result)) {
            response.sendRedirect("dashboard.jsp");
            return;
        }
    }
%>

<!DOCTYPE html>
<html lang="sr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Promotion app</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-container {
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }

        .login-header h1 {
            color: #333;
            margin-bottom: 0.5rem;
            font-size: 1.8rem;
        }

        .login-header p {
            color: #666;
            font-size: 0.9rem;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #333;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            padding: 0.75rem;
            border: 2px solid #e1e1e1;
            border-radius: 5px;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus {
            outline: none;
            border-color: #667eea;
        }

        .login-button {
            width: 100%;
            padding: 0.75rem;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s ease;
        }

        .login-button:hover {
            transform: translateY(-2px);
        }

        .error-message {
            background: #fee;
            border: 1px solid #fcc;
            color: #c66;
            padding: 0.75rem;
            border-radius: 5px;
            margin-bottom: 1rem;
            text-align: center;
        }

        .info-note {
            background: #f8f9fa;
            border-left: 4px solid #667eea;
            padding: 1rem;
            margin-top: 1.5rem;
            border-radius: 0 5px 5px 0;
        }

        .info-note h4 {
            color: #333;
            margin-bottom: 0.5rem;
        }

        .info-note p {
            color: #666;
            font-size: 0.9rem;
            line-height: 1.4;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <h1>Login</h1>
        <p>Promotion app</p>
    </div>

    <% if (userBean.getErrorMessage() != null) { %>
    <div class="error-message">
        <%= userBean.getErrorMessage() %>
    </div>
    <% } %>

    <form method="post" action="login.jsp">
        <input type="hidden" name="action" value="login">

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username"
                   value="<%= userBean.getUsername() != null ? userBean.getUsername() : "" %>"
                   required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="login-button">Sign in</button>
    </form>

    <div class="info-note">
        <h4>Note:</h4>
        <p>Only managers have access to this application. Contact the administrator for credentials.</p>
    </div>
</div>
</body>
</html>