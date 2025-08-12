<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<html lang="sr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Aplikacija za promocije</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
            line-height: 1.6;
        }

        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 1rem 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h1 {
            font-size: 1.5rem;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 1rem;
        }

        .logout-btn {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: 1px solid rgba(255, 255, 255, 0.3);
            padding: 0.5rem 1rem;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .logout-btn:hover {
            background: rgba(255, 255, 255, 0.3);
        }

        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }

        .tabs {
            display: flex;
            background: white;
            border-radius: 10px 10px 0 0;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .tab {
            flex: 1;
            padding: 1rem;
            background: #f8f9fa;
            border: none;
            cursor: pointer;
            font-size: 1rem;
            font-weight: 600;
            color: #666;
            transition: all 0.3s ease;
        }

        .tab.active {
            background: white;
            color: #667eea;
            border-bottom: 3px solid #667eea;
        }

        .tab:hover {
            background: #e9ecef;
        }

        .tab-content {
            background: white;
            border-radius: 0 0 10px 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            display: none;
        }

        .tab-content.active {
            display: block;
        }

        .section {
            padding: 2rem;
        }

        .form-container {
            background: #f8f9fa;
            padding: 1.5rem;
            border-radius: 10px;
            margin-bottom: 2rem;
        }

        .form-container h3 {
            margin-bottom: 1rem;
            color: #333;
        }

        .form-row {
            display: flex;
            gap: 1rem;
            margin-bottom: 1rem;
        }

        .form-group {
            flex: 1;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #333;
            font-weight: 500;
        }

        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 0.75rem;
            border: 2px solid #e1e1e1;
            border-radius: 5px;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }

        .form-group input:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #667eea;
        }

        .form-group textarea {
            min-height: 100px;
            resize: vertical;
        }

        .btn {
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            font-weight: 600;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
        }

        .btn-danger {
            background: #dc3545;
            color: white;
        }

        .btn-danger:hover {
            background: #c82333;
        }

        .search-container {
            margin-bottom: 2rem;
        }

        .search-form {
            display: flex;
            gap: 1rem;
            align-items: end;
        }

        .items-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 1.5rem;
        }

        .item-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-left: 4px solid #667eea;
        }

        .item-card h4 {
            color: #333;
            margin-bottom: 0.5rem;
            font-size: 1.2rem;
        }

        .item-card p {
            color: #666;
            margin-bottom: 1rem;
        }

        .item-meta {
            font-size: 0.9rem;
            color: #888;
            margin-bottom: 1rem;
        }

        .item-actions {
            display: flex;
            gap: 0.5rem;
        }

        .message {
            padding: 1rem;
            border-radius: 5px;
            margin-bottom: 1rem;
        }

        .message.success {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
        }

        .message.error {
            background: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
        }

        .no-items {
            text-align: center;
            color: #666;
            font-style: italic;
            margin: 2rem 0;
        }

        @media (max-width: 768px) {
            .container {
                padding: 0 1rem;
            }

            .tabs {
                flex-direction: column;
            }

            .form-row {
                flex-direction: column;
            }

            .search-form {
                flex-direction: column;
            }

            .items-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
<div class="header">
    <div class="header-content">
        <h1>Aplikacija za promocije</h1>
        <div class="user-info">
            <span>Dobro došli, <%= userBean.getUsername() != null ? userBean.getUsername() : "Menadžer" %>!</span>
            <a href="dashboard.jsp?action=logout" class="logout-btn">Odjaviti se</a>
        </div>
    </div>
</div>

<div class="container">
    <div class="tabs">
        <button class="tab active" onclick="switchTab(event, 'posts')">Objave</button>
        <button class="tab" onclick="switchTab(event, 'promotions')">Promocije</button>
    </div>

    <!-- Tab za objave -->
    <div id="posts" class="tab-content active">
        <div class="section">
            <!-- Forma za kreiranje objave -->
            <div class="form-container">
                <h3>Kreiraj novu objavu</h3>
                <form method="post" action="dashboard.jsp">
                    <input type="hidden" name="action" value="createPost">

                    <div class="form-group">
                        <label for="postTitle">Naslov:</label>
                        <input type="text" id="postTitle" name="title" required>
                    </div>

                    <div class="form-group">
                        <label for="postContent">Sadržaj:</label>
                        <textarea id="postContent" name="content" required></textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Kreiraj objavu</button>
                </form>
            </div>

            <% if (postBean.getMessage() != null) { %>
            <div class="message <%= postBean.getMessage().contains("uspješno") ? "success" : "error" %>">
                <%= postBean.getMessage() %>
            </div>
            <% } %>

            <!-- Pretraga objava -->
            <div class="search-container">
                <form method="post" action="dashboard.jsp" class="search-form">
                    <input type="hidden" name="action" value="searchPosts">
                    <div class="form-group">
                        <label for="postSearchTerm">Pretraži objave:</label>
                        <input type="text" id="postSearchTerm" name="searchTerm"
                               value="<%= postBean.getSearchTerm() != null ? postBean.getSearchTerm() : "" %>"
                               placeholder="Unesite pojam za pretragu...">
                    </div>
                    <button type="submit" class="btn btn-primary">Pretraži</button>
                </form>
            </div>

            <!-- Lista objava -->
            <div class="items-grid">
                <%
                    List<PostDTO> posts = postBean.getPosts();
                    if (posts != null && !posts.isEmpty()) {
                        for (PostDTO post : posts) {
                %>
                <div class="item-card">
                    <h4><%= post.getTitle() %>
                    </h4>
                    <p><%= post.getContent() %>
                    </p>
                    <div class="item-meta">
                        Kreirano: <%= post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) %>
                    </div>
                    <div class="item-actions">
                        <a href="dashboard.jsp?action=deletePost&postId=<%= post.getId() %>"
                           class="btn btn-danger"
                           onclick="return confirm('Da li ste sigurni da želite obrisati ovu objavu?')">
                            Obriši
                        </a>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <div class="no-items">Nema objava za prikaz.</div>
                <% } %>
            </div>
        </div>
    </div>

    <!-- Tab za promocije -->
    <div id="promotions" class="tab-content">
        <div class="section">
            <!-- Forma za kreiranje promocije -->
            <div class="form-container">
                <h3>Kreiraj novu promociju</h3>
                <form method="post" action="dashboard.jsp">
                    <input type="hidden" name="action" value="createPromotion">

                    <div class="form-group">
                        <label for="promotionTitle">Naslov:</label>
                        <input type="text" id="promotionTitle" name="title" required>
                    </div>

                    <div class="form-group">
                        <label for="promotionDescription">Opis:</label>
                        <textarea id="promotionDescription" name="description" required></textarea>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="startDate">Datum početka:</label>
                            <input type="datetime-local" id="startDate" name="startDateStr" required>
                        </div>

                        <div class="form-group">
                            <label for="endDate">Datum završetka:</label>
                            <input type="datetime-local" id="endDate" name="endDateStr" required>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">Kreiraj promociju</button>
                </form>
            </div>

            <% if (promotionBean.getMessage() != null) { %>
            <div class="message <%= promotionBean.getMessage().contains("uspješno") ? "success" : "error" %>">
                <%= promotionBean.getMessage() %>
            </div>
            <% } %>

            <!-- Pretraga promocija -->
            <div class="search-container">
                <form method="post" action="dashboard.jsp" class="search-form">
                    <input type="hidden" name="action" value="searchPromotions">
                    <div class="form-group">
                        <label for="promotionSearchTerm">Pretraži promocije:</label>
                        <input type="text" id="promotionSearchTerm" name="searchTerm"
                               value="<%= promotionBean.getSearchTerm() != null ? promotionBean.getSearchTerm() : "" %>"
                               placeholder="Unesite pojam za pretragu...">
                    </div>
                    <button type="submit" class="btn btn-primary">Pretraži</button>
                </form>
            </div>

            <!-- Lista promocija -->
            <div class="items-grid">
                <%
                    List<PromotionDTO> promotions = promotionBean.getPromotions();
                    if (promotions != null && !promotions.isEmpty()) {
                        for (PromotionDTO promotion : promotions) {
                %>
                <div class="item-card">
                    <h4><%= promotion.getTitle() %>
                    </h4>
                    <p><%= promotion.getDescription() %>
                    </p>
                    <div class="item-meta">
                        Period: <%= promotion.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) %>
                        -
                        <%= promotion.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) %>
                    </div>
                    <div class="item-actions">
                        <a href="dashboard.jsp?action=deletePromotion&promotionId=<%= promotion.getId() %>"
                           class="btn btn-danger"
                           onclick="return confirm('Da li ste sigurni da želite obrisati ovu promociju?')">
                            Obriši
                        </a>
                    </div>
                </div>
                <%
                    }
                } else {
                %>
                <div class="no-items">Nema promocija za prikaz.</div>
                <% } %>
            </div>
        </div>
    </div>
</div>

<script>
    function switchTab(evt, tabName) {
        var i, tabcontent, tabs;

        // Sakrij sve tab sadržaje
        tabcontent = document.getElementsByClassName("tab-content");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].classList.remove("active");
        }

        // Ukloni active klasu sa svih tabova
        tabs = document.getElementsByClassName("tab");
        for (i = 0; i < tabs.length; i++) {
            tabs[i].classList.remove("active");
        }

        // Prikaži odgovarajući tab i označi ga kao aktivan
        document.getElementById(tabName).classList.add("active");
        evt.currentTarget.classList.add("active");
    }

    // Postavi minimalne datume za promocije (ne može biti u prošlosti)
    window.onload = function () {
        var now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        var dateString = now.toISOString().slice(0, 16);

        document.getElementById('startDate').setAttribute('min', dateString);
        document.getElementById('endDate').setAttribute('min', dateString);

        // Kada se promeni start datum, update end datum minimum
        document.getElementById('startDate').addEventListener('change', function () {
            document.getElementById('endDate').setAttribute('min', this.value);
        });
    }
</script>
</body>
</html>