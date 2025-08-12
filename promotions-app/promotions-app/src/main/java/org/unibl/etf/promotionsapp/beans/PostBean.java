package org.unibl.etf.promotionsapp.beans;

import org.unibl.etf.promotionsapp.dao.PostDAO;
import org.unibl.etf.promotionsapp.dto.PostDTO;

import java.time.LocalDateTime;
import java.util.List;

public class PostBean {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime dateCreated = LocalDateTime.now();
    // CRUD metode
    private String searchTerm;
    private String message;
    private PostDAO postDAO;
    private List<PostDTO> posts;

    public PostBean() {
        this.postDAO = new PostDAO();
        loadAllPosts();
    }

    // CRUD metode
    public String createPost() {
        if (title == null || title.trim().isEmpty() ||
                content == null || content.trim().isEmpty()) {
            message = "Naslov i sadržaj su obavezni!";
            return "error";
        }

        PostDTO post = new PostDTO(title.trim(), content.trim());

        if (postDAO.save(post)) {
            message = "Objava je uspješno kreirana!";
            title = "";
            content = "";
            loadAllPosts(); // Refresh liste
            return "success";
        } else {
            message = "Greška pri kreiranju objave!";
            return "error";
        }
    }

    public void loadAllPosts() {
        posts = postDAO.findAll();
    }

    public String searchPosts() {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            loadAllPosts();
        } else {
            posts = postDAO.searchByContent(searchTerm.trim());
        }
        return "success";
    }

    public String deletePost(Long id) {
        if (postDAO.delete(id)) {
            message = "Objava je uspješno obrisana!";
            loadAllPosts();
            return "success";
        } else {
            message = "Greška pri brisanju objave!";
            return "error";
        }
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}
