<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="net.etfbl.ip.smartridemanager.beans.UserBean" %>
<%@ page import="net.etfbl.ip.smartridemanager.dao.PostDAO" %>
<%@ page import="net.etfbl.ip.smartridemanager.dao.PromotionDAO" %>
<%@ page import="net.etfbl.ip.smartridemanager.dto.Post" %>
<%@ page import="net.etfbl.ip.smartridemanager.dto.Promotion" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.LocalDate" %>

<jsp:useBean id="userBean" class="net.etfbl.ip.smartridemanager.beans.UserBean" scope="session"/>


<%
    if (userBean == null || !userBean.isLoggedIn()) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%
    String successMessage = null;
    String errorMessage = null;

    successMessage = (String) session.getAttribute("successMessage");
    if (successMessage != null) {
        session.removeAttribute("successMessage");
    }
    errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) {
        session.removeAttribute("errorMessage");
    }


    if ("POST".equalsIgnoreCase(request.getMethod())) {
        if (request.getParameter("createPost") != null) {
            String title = request.getParameter("postTitle");
            String content = request.getParameter("postContent");

            if (title != null && !title.trim().isEmpty() && content != null && !content.trim().isEmpty()) {
                try {
                    Post newPost = new Post();
                    newPost.setTitle(title);
                    newPost.setContent(content);
                    newPost.setCreatedAt(LocalDateTime.now());

                    boolean success = PostDAO.insert(newPost);
                    if (success) {
                        session.setAttribute("successMessage", "Post created successfully!");
                    } else {
                        session.setAttribute("errorMessage", "Failed to create post.");
                    }
                } catch (Exception e) {
                    session.setAttribute("errorMessage", "Error creating post: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                session.setAttribute("errorMessage", "Title and content are required for posts.");
            }
            response.sendRedirect("dashboard.jsp");
            return;
        } else if (request.getParameter("createPromotion") != null) {
            String title = request.getParameter("promotionTitle");
            String description = request.getParameter("promotionDescription");
            String endDateStr = request.getParameter("promotionEndDate");
            LocalDate endDate = null;

            if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                try {
                    endDate = LocalDate.parse(endDateStr);
                } catch (IllegalArgumentException e) {
                    errorMessage = "Invalid end date format (use yyyy-mm-dd).";
                    e.printStackTrace();
                }
            }

            if (errorMessage == null && title != null && !title.trim().isEmpty() &&
                    description != null && !description.trim().isEmpty() &&
                    endDate != null) {
                try {
                    Promotion newPromotion = new Promotion();
                    newPromotion.setTitle(title);
                    newPromotion.setDescription(description);
                    newPromotion.setValidUntil(endDate);
                    newPromotion.setCreatedAt(LocalDateTime.now());

                    boolean success = PromotionDAO.insert(newPromotion);
                    if (success) {
                        session.setAttribute("successMessage", "Promotion created successfully!");
                    } else {
                        session.setAttribute("errorMessage", "Failed to create promotion.");
                    }
                } catch (Exception e) {
                    session.setAttribute("errorMessage", "Error creating promotion: " + e.getMessage());
                    e.printStackTrace();
                }
            } else if (errorMessage == null) {
                errorMessage = "Title, description and end date are required for promotions.";
            }
            if (errorMessage != null) {
                session.setAttribute("errorMessage", errorMessage);
            }
            response.sendRedirect("dashboard.jsp");
            return;
        }
    }
%>

<%
    List<Post> posts = null;
    List<Promotion> promotions = null;
    String searchQuery = request.getParameter("search");

    try {
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            posts = PostDAO.search(searchQuery);
            promotions = PromotionDAO.search(searchQuery);
        } else {
            posts = PostDAO.findAll();
            promotions = PromotionDAO.findAll();
        }
    } catch (Exception e) {
        errorMessage = "Error loading data: " + e.getMessage();
        e.printStackTrace();
        if (posts == null) posts = new ArrayList<>();
        if (promotions == null) promotions = new ArrayList<>();
    }
    DateTimeFormatter postDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
    DateTimeFormatter promotionDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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
            --primary-color: #d8e422;
            --primary-dark: #e3b238;
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
            border-radius: 12px;
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

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            flex-wrap: wrap;
            gap: 10px;
        }

        .card-title {
            font-size: 1.5rem;
            color: var(--text-color);
        }

        .card-actions {
            display: flex;
            gap: 10px;
        }

        .btn {
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s;
            border: none;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 5px;
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

        .item-content {
            font-size: 1rem;
            color: var(--text-color);
            line-height: 1.5;
            margin-bottom: 15px;
            flex-grow: 1;
        }

        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 1050;
            overflow: auto;
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background-color: var(--white);
            margin: 50px auto;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            width: 100%;
            max-width: 600px;
            position: relative;
        }

        .close-btn {
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 1.5rem;
            font-weight: bold;
            color: #aaa;
            cursor: pointer;
            transition: color 0.3s;
        }

        .close-btn:hover {
            color: var(--text-color);
        }

        .modal-header {
            padding-bottom: 15px;
            margin-bottom: 15px;
            border-bottom: 1px solid #ddd;
        }

        .modal-title {
            font-size: 1.5rem;
            color: var(--text-color);
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

        .modal-footer {
            padding-top: 15px;
            margin-top: 15px;
            border-top: 1px solid #ddd;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }
    </style>
</head>
<body>
<header>
    <div class="header-content">
        <img src="<%=request.getContextPath()%>/images/logo.png" alt="Smart Ride Logo" class="logo">
        <div class="app-title">Smart Ride - Manager Portal</div>
    </div>
    <div class="user-info">
        <span class="user-name">Welcome, <%= userBean.getFirstName() %> <%= userBean.getLastName() %></span>
        <a href="<%=request.getContextPath()%>/pages/login.jsp?action=logout" class="logout-btn">Logout</a>
    </div>
</header>

<main>
    <div class="dashboard-header">
        <h1 class="dashboard-title">Dashboard</h1>
        <form class="search-container" action="<%=request.getContextPath()%>/pages/dashboard.jsp" method="GET">
            <input type="text" name="search" placeholder="Search posts and promotions..."
                   value="<%= searchQuery != null ? searchQuery : "" %>">
            <button type="submit"><i class="fas fa-search"></i></button>
        </form>
    </div>

    <%-- Prikaz poruka (iz sesije, prebačene u request scope gore) --%>
    <% if (successMessage != null) { %>
    <div class="alert alert-success">
        <i class="fas fa-check-circle"></i> <%= successMessage %>
    </div>
    <% } %>
    <% if (errorMessage != null) { %>
    <div class="alert alert-danger">
        <i class="fas fa-exclamation-circle"></i> <%= errorMessage %>
    </div>
    <% } %>

    <div class="content-tabs">
        <button class="tab-button active" onclick="openTab(event, 'posts-tab')">Posts</button>
        <button class="tab-button" onclick="openTab(event, 'promotions-tab')">Promotions</button>
    </div>

    <%-- Tab sa objavama --%>
    <div id="posts-tab" class="tab-content active">
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Posts</h2>
                <div class="card-actions">
                    <button class="btn btn-primary" onclick="openModal('create-post-modal')">
                        <i class="fas fa-plus"></i> New Post
                    </button>
                </div>
            </div>

            <% if (posts != null && !posts.isEmpty()) { %>
            <div class="item-list">
                <% for (Post post : posts) { %>
                <div class="item-card">
                    <div>
                        <h3 class="item-title"><%= post.getTitle() != null ? post.getTitle() : "No Title" %>
                        </h3>
                        <div class="item-meta">
                            <span><i class="far fa-calendar"></i>
                                <%= post.getCreatedAt() != null ? post.getCreatedAt().format(postDateTimeFormatter) : "N/A" %>
                            </span>
                        </div>
                        <div class="item-content">
                            <%= post.getContent() != null ? post.getContent() : "" %>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <div class="empty-state">
                <i class="far fa-newspaper"></i>
                <h3>No posts found</h3>
                <% if (searchQuery != null && !searchQuery.isEmpty()) { %>
                <p>No posts match your search criteria.</p>
                <% } else { %>
                <p>Create a new post using the button above.</p>
                <% } %>
            </div>
            <% } %>
        </div>
    </div>

    <%-- Tab sa promocijama --%>
    <div id="promotions-tab" class="tab-content">
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Promotions</h2>
                <div class="card-actions">
                    <button class="btn btn-primary" onclick="openModal('create-promotion-modal')">
                        <i class="fas fa-plus"></i> New Promotion
                    </button>
                </div>
            </div>

            <% if (promotions != null && !promotions.isEmpty()) { %>
            <div class="item-list">
                <% for (Promotion promotion : promotions) { %>
                <div class="item-card">
                    <div> <%-- Dodat div za sadržaj --%>
                        <h3 class="item-title"><%= promotion.getTitle() != null ? promotion.getTitle() : "No Title" %>
                        </h3>
                        <div class="item-meta">
                            <span><i class="far fa-calendar-check"></i> Valid until:
                                <%= promotion.getValidUntil() != null ? promotion.getValidUntil().format(promotionDateFormatter) : "N/A" %>
                            </span>
                            <span><i class="far fa-calendar-alt"></i> Created:
                                 <%= promotion.getCreatedAt() != null ? promotion.getCreatedAt().format(postDateTimeFormatter) : "N/A" %>
                             </span>
                            <%-- <span><i class="far fa-user"></i> Author Name</span> --%> <%-- Ime autora izbačeno --%>
                        </div>
                        <div class="item-content">
                            <%= promotion.getDescription() != null ? promotion.getDescription() : "" %>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <div class="empty-state">
                <i class="fas fa-percentage"></i>
                <h3>No promotions found</h3>
                <% if (searchQuery != null && !searchQuery.isEmpty()) { %>
                <p>No promotions match your search criteria.</p>
                <% } else { %>
                <p>Create a new promotion using the button above.</p>
                <% } %>
            </div>
            <% } %>
        </div>
    </div>
</main>

<div id="create-post-modal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal('create-post-modal')">&times;</span>
        <div class="modal-header">
            <h2 class="modal-title">Create New Post</h2>
        </div>
        <form action="<%=request.getContextPath()%>/pages/dashboard.jsp" method="POST">
            <div class="form-group">
                <label for="postTitle">Title</label>
                <input type="text" class="form-control" id="postTitle" name="postTitle" required>
            </div>
            <div class="form-group">
                <label for="postContent">Content</label>
                <textarea class="form-control" id="postContent" name="postContent" required></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" onclick="closeModal('create-post-modal')">Cancel</button>
                <button type="submit" name="createPost" value="1" class="btn btn-primary">Create Post</button>
            </div>
        </form>
    </div>
</div>

<div id="create-promotion-modal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeModal('create-promotion-modal')">&times;</span>
        <div class="modal-header">
            <h2 class="modal-title">Create New Promotion</h2>
        </div>
        <form action="<%=request.getContextPath()%>/pages/dashboard.jsp" method="POST">
            <div class="form-group">
                <label for="promotionTitle">Title</label>
                <input type="text" class="form-control" id="promotionTitle" name="promotionTitle" required>
            </div>
            <div class="form-group">
                <label for="promotionDescription">Description</label>
                <textarea class="form-control" id="promotionDescription" name="promotionDescription"
                          required></textarea>
            </div>
            <div class="form-group">
                <label for="promotionEndDate">End Date</label>
                <input type="date" class="form-control" id="promotionEndDate" name="promotionEndDate" required>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" onclick="closeModal('create-promotion-modal')">Cancel</button>
                <button type="submit" name="createPromotion" value="1" class="btn btn-primary">Create Promotion</button>
            </div>
        </form>
    </div>
</div>

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

    function openModal(modalId) {
        document.getElementById(modalId).style.display = "flex";
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = "none";
        }
    }
</script>
</body>
</html>