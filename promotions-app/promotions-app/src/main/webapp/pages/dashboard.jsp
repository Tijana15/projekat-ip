<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.unibl.etf.promotionsapp.dto.PostDTO" %>
<%@ page import="org.unibl.etf.promotionsapp.dto.PromotionDTO" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<jsp:useBean id="userBean" class="org.unibl.etf.promotionsapp.beans.UserBean" scope="session"/>
<jsp:useBean id="postBean" class="org.unibl.etf.promotionsapp.beans.PostBean" scope="session"/>
<jsp:useBean id="promotionBean" class="org.unibl.etf.promotionsapp.beans.PromotionBean" scope="session"/>

<jsp:setProperty name="postBean" property="*"/>
<jsp:setProperty name="promotionBean" property="*"/>

<%
    if (!userBean.isLoggedIn()) {
        response.sendRedirect("login.jsp");
        return;
    }

    if ("logout".equals(request.getParameter("action"))) {
        userBean.logout();
        response.sendRedirect("login.jsp");
        return;
    }

    if ("createPost".equals(request.getParameter("action"))) {
        postBean.createPost();
    }

    if ("createPromotion".equals(request.getParameter("action"))) {
        promotionBean.createPromotion();
    }

    if ("searchPosts".equals(request.getParameter("action"))) {
        postBean.searchPosts();
    }

    if ("searchPromotions".equals(request.getParameter("action"))) {
        promotionBean.searchPromotions();
    }

    if ("deletePost".equals(request.getParameter("action"))) {
        String postId = request.getParameter("postId");
        if (postId != null) {
            postBean.deletePost(Long.parseLong(postId));
        }
    }

    if ("deletePromotion".equals(request.getParameter("action"))) {
        String promotionId = request.getParameter("promotionId");
        if (promotionId != null) {
            promotionBean.deletePromotion(Long.parseLong(promotionId));
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Smart Ride - Dashboard</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        :root {
            --primary-color: #e3b238;
            --primary-dark: #c8c014;
            --secondary-color: #f8f9fa;
            --text-color: #333;
            --light-gray: #e9ecef;
            --white: #ffffff;
            --shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            --danger: #dc3545;
            --success: #28a745;
            --warning: #ffc107;
            --info: #17a2b8;
        }

        body {
            font-family: 'Roboto', 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(135deg, #f6f9fc, #e9f2f9);
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        header {
            width: 100%;
            height: 80px;
            background: var(--primary-color);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 20px;
            box-shadow: var(--shadow);
            z-index: 1000;
        }

        header .header-content {
            display: flex;
            align-items: center;
        }

        header .logo {
            height: 60px;
            width: 60px;
            margin-right: 15px;

        }

        header .app-title {
            font-size: 2rem;
            color: var(--white);
            font-weight: bold;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
        }

        .user-info {
            display: flex;
            align-items: center;
            color: var(--white);
        }

        .user-info .user-name {
            margin-right: 15px;
            font-weight: 500;
        }

        .logout-btn {
            background-color: rgba(255, 255, 255, 0.2);
            border: 1px solid rgba(255, 255, 255, 0.3);
            color: var(--white);
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            font-weight: 500;
        }

        .logout-btn:hover {
            background-color: rgba(255, 255, 255, 0.3);
        }

        main {
            flex: 1;
            padding: 20px;
            max-width: 1200px;
            margin: 0 auto;
            width: 100%;
        }

        .dashboard-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            flex-wrap: wrap;
            gap: 10px;
        }

        .dashboard-title {
            font-size: 1.8rem;
            color: var(--text-color);
        }

        .search-container {
            display: flex;
            max-width: 400px;
            width: 100%;
        }

        .search-container input {
            flex: 1;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 5px 0 0 5px;
            font-size: 16px;
        }

        .search-container button {
            padding: 10px 15px;
            background-color: var(--primary-color);
            color: var(--white);
            border: none;
            border-radius: 0 5px 5px 0;
            cursor: pointer;
            transition: all 0.3s;
        }

        .search-container button:hover {
            background-color: var(--primary-dark);
        }

        .alert {
            padding: 12px 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
        }

        .alert i {
            margin-right: 10px;
            font-size: 1.2rem;
        }

        .alert-success {
            background-color: rgba(40, 167, 69, 0.1);
            border: 1px solid rgba(40, 167, 69, 0.2);
            color: var(--success);
        }

        .alert-danger {
            background-color: rgba(220, 53, 69, 0.1);
            border: 1px solid rgba(220, 53, 69, 0.2);
            color: var(--danger);
        }

        .content-tabs {
            display: flex;
            margin-bottom: 15px;
            border-bottom: 1px solid #ddd;
        }

        .tab-button {
            padding: 12px 20px;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            color: #666;
            border-bottom: 3px solid transparent;
            transition: all 0.3s;
        }

        .tab-button.active {
            color: var(--primary-color);
            border-bottom: 3px solid var(--primary-color);
        }

        .tab-button:hover:not(.active) {
            color: var(--primary-dark);
            border-bottom: 3px solid #ddd;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
        }

        .card {
            background-color: var(--white);
            border-radius: 8px;
            box-shadow: var(--shadow);
            padding: 20px;
            margin-bottom: 20px;
        }

        .form-container {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 30px;
        }

        .form-container h3 {
            margin-bottom: 20px;
            color: var(--text-color);
            font-size: 1.4rem;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--text-color);
        }

        .form-control {
            width: 100%;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        .form-control:focus {
            border-color: var(--primary-color);
            outline: none;
            box-shadow: 0 0 0 3px rgba(20, 168, 157, 0.1);
        }

        textarea.form-control {
            min-height: 120px;
            resize: vertical;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s;
            border: none;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 5px;
            font-size: 16px;
        }

        .btn i {
            font-size: 1em;
        }

        .btn-primary {
            background-color: var(--primary-color);
            color: var(--white);
        }

        .btn-primary:hover {
            background-color: var(--primary-dark);
        }

        .btn-danger {
            background-color: var(--danger);
            color: var(--white);
        }

        .btn-danger:hover {
            background-color: #bd2130;
        }

        .empty-state {
            text-align: center;
            padding: 40px;
            color: #666;
        }

        .empty-state i {
            font-size: 3rem;
            margin-bottom: 15px;
            color: #aaa;
        }

        .item-list {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }

        .item-card {
            background-color: var(--white);
            border-radius: 8px;
            box-shadow: var(--shadow);
            padding: 15px;
            transition: transform 0.3s;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }

        .item-card:hover {
            transform: translateY(-5px);
        }

        .item-title {
            font-size: 1.3rem;
            margin-bottom: 10px;
            color: var(--text-color);
        }

        .item-meta {
            font-size: 0.9rem;
            color: #666;
            margin-bottom: 10px;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 5px;
        }

        .item-actions {
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }

        @media (max-width: 768px) {
            .container {
                padding: 0 1rem;
            }

            .dashboard-header {
                flex-direction: column;
            }

            .search-container {
                max-width: 100%;
            }

            .item-list {
                grid-template-columns: 1fr;
            }

            form[style*="display: flex"] {
                flex-direction: column;
            }

            form[style*="display: flex"] .form-group {
                flex: 1 !important;
            }
        }
    </style>
</head>
<body>
<header>
    <div class="header-content">
        <img src="<%= request.getContextPath() %>/images/logo.png" alt="Logo" class="logo">

        <div class="app-title">Promotions app</div>
    </div>
    <div class="user-info">
        <span class="user-name">Welcome, <%= userBean.getUsername() != null ? userBean.getUsername() : "Manager" %>!</span>
        <a href="dashboard.jsp?action=logout" class="logout-btn">Log out</a>
    </div>
</header>

<main>
    <div class="dashboard-header">
        <h1 class="dashboard-title">Dashboard</h1>
        <form class="search-container" action="dashboard.jsp" method="GET">
            <input type="text" name="search" placeholder="Search posts and promotions..."
                   value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
            <button type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>

    <% if (postBean.getMessage() != null) { %>
    <div class="alert <%= postBean.getMessage().toLowerCase().contains("successful") ? "alert-success" : "alert-danger" %>">
        <i class="fas <%= postBean.getMessage().toLowerCase().contains("successful") ? "fa-check-circle" : "fa-exclamation-circle" %>"></i> <%= postBean.getMessage() %>
    </div>
    <% } %>
    <% if (promotionBean.getMessage() != null) { %>
    <div class="alert <%= promotionBean.getMessage().toLowerCase().contains("successful") ? "alert-success" : "alert-danger" %>">
        <i class="fas <%= promotionBean.getMessage().toLowerCase().contains("successful") ? "fa-check-circle" : "fa-exclamation-circle" %>"></i> <%= promotionBean.getMessage() %>
    </div>
    <% } %>

    <div class="content-tabs">
        <button class="tab-button active" onclick="openTab(event, 'posts-tab')">Posts</button>
        <button class="tab-button" onclick="openTab(event, 'promotions-tab')">Promotions</button>
    </div>

    <div id="posts-tab" class="tab-content active">
        <div class="form-container">
            <h3>Create new post</h3>
            <form action="dashboard.jsp" method="POST">
                <input type="hidden" name="action" value="createPost">
                <div class="form-group">
                    <label for="postTitle">Title:</label>
                    <input type="text" class="form-control" id="postTitle" name="title" required>
                </div>
                <div class="form-group">
                    <label for="postContent">Content:</label>
                    <textarea class="form-control" id="postContent" name="content" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Create post
                </button>
            </form>
        </div>

        <div class="form-container">
            <form method="post" action="dashboard.jsp">
                <input type="hidden" name="action" value="searchPosts">
                <div class="form-group">
                    <label for="postSearchTerm">Search posts:</label>
                    <input type="text" class="form-control" id="postSearchTerm" name="searchTerm"
                           value="<%= postBean.getSearchTerm() != null ? postBean.getSearchTerm() : "" %>"
                           placeholder="Enter search term...">
                </div>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-search"></i> Search
                </button>
            </form>
        </div>

        <div class="card">
            <h3>Posts</h3>
            <%
                List<PostDTO> posts = postBean.getPosts();
                if (posts != null && !posts.isEmpty()) {
            %>
            <div class="item-list">
                <% for (PostDTO post : posts) { %>
                <div class="item-card">
                    <div>
                        <h3 class="item-title"><%= post.getTitle() != null ? post.getTitle() : "No Title" %>
                        </h3>
                        <div class="item-meta">
                            <span><i class="far fa-calendar"></i>
                                <%= post.getCreatedAt() != null ? post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) : "N/A" %>
                            </span>
                        </div>
                        <div class="item-content">
                            <%= post.getContent() != null ? post.getContent() : "" %>
                        </div>
                        <div class="item-actions" style="margin-top: 10px;">
                            <a href="dashboard.jsp?action=deletePost&postId=<%= post.getId() %>"
                               class="btn btn-danger"
                               onclick="return confirm('Are you sure you want to delete this post?')">
                                <i class="fas fa-trash"></i> Delete
                            </a>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <div class="empty-state">
                <i class="far fa-newspaper"></i>
                <h3>No posts found</h3>
                <% if (postBean.getSearchTerm() != null && !postBean.getSearchTerm().isEmpty()) { %>
                <p>No posts match your search criteria.</p>
                <% } else { %>
                <p>Create your first post using the form above.</p>
                <% } %>
            </div>
            <% } %>
        </div>
    </div>

    <div id="promotions-tab" class="tab-content">
        <div class="form-container">
            <h3>Create new promotion</h3>
            <form action="dashboard.jsp" method="POST">
                <input type="hidden" name="action" value="createPromotion">
                <div class="form-group">
                    <label for="promotionTitle">Title:</label>
                    <input type="text" class="form-control" id="promotionTitle" name="title" required>
                </div>
                <div class="form-group">
                    <label for="promotionDescription">Description:</label>
                    <textarea class="form-control" id="promotionDescription" name="description" required></textarea>
                </div>
                <div style="display: flex; gap: 15px;">
                    <div class="form-group" style="flex: 1;">
                        <label for="startDate">Start date:</label>
                        <input type="datetime-local" class="form-control" id="startDate" name="startDateStr" required>
                    </div>
                    <div class="form-group" style="flex: 1;">
                        <label for="endDate">End date:</label>
                        <input type="datetime-local" class="form-control" id="endDate" name="endDateStr" required>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Create promotion
                </button>
            </form>
        </div>

        <div class="form-container">
            <form method="post" action="dashboard.jsp">
                <input type="hidden" name="action" value="searchPromotions">
                <div class="form-group">
                    <label for="promotionSearchTerm">Search promotions:</label>
                    <input type="text" class="form-control" id="promotionSearchTerm" name="searchTerm"
                           value="<%= promotionBean.getSearchTerm() != null ? promotionBean.getSearchTerm() : "" %>"
                           placeholder="Enter search term...">
                </div>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-search"></i> Search
                </button>
            </form>
        </div>

        <div class="card">
            <h3>Promotions</h3>
            <%
                List<PromotionDTO> promotions = promotionBean.getPromotions();
                if (promotions != null && !promotions.isEmpty()) {
            %>
            <div class="item-list">
                <% for (PromotionDTO promotion : promotions) { %>
                <div class="item-card">
                    <div>
                        <h3 class="item-title"><%= promotion.getTitle() != null ? promotion.getTitle() : "No Title" %>
                        </h3>
                        <div class="item-meta">
                            <span><i class="far fa-calendar-check"></i> Period:
                                <%= promotion.getStartDate() != null ? promotion.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) : "N/A" %>
                                -
                                <%= promotion.getEndDate() != null ? promotion.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) : "N/A" %>
                            </span>
                        </div>
                        <div class="item-content">
                            <%= promotion.getDescription() != null ? promotion.getDescription() : "" %>
                        </div>
                        <div class="item-actions" style="margin-top: 10px;">
                            <a href="dashboard.jsp?action=deletePromotion&promotionId=<%= promotion.getId() %>"
                               class="btn btn-danger"
                               onclick="return confirm('Are you sure you want to delete this promotion?')">
                                <i class="fas fa-trash"></i> Delete
                            </a>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <div class="empty-state">
                <i class="fas fa-percentage"></i>
                <h3>No promotions found</h3>
                <% if (promotionBean.getSearchTerm() != null && !promotionBean.getSearchTerm().isEmpty()) { %>
                <p>No promotions match your search criteria.</p>
                <% } else { %>
                <p>Create your first promotion using the form above.</p>
                <% } %>
            </div>
            <% } %>
        </div>
    </div>
</main>

<script>
    function openTab(evt, tabName) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tab-content");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].classList.remove("active");
        }
        tablinks = document.getElementsByClassName("tab-button");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].classList.remove("active");
        }
        document.getElementById(tabName).classList.add("active");
        evt.currentTarget.classList.add("active");
    }

    window.onload = function() {
        var now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        var dateString = now.toISOString().slice(0, 16);

        document.getElementById('startDate').setAttribute('min', dateString);
        document.getElementById('endDate').setAttribute('min', dateString);

        document.getElementById('startDate').addEventListener('change', function() {
            document.getElementById('endDate').setAttribute('min', this.value);
        });
    }
</script>
</body>
</html>