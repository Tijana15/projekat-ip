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

    private String searchTerm;
    private String message;
    private PostDAO postDAO;
    private List<PostDTO> posts;

    public PostBean() {
        this.postDAO = new PostDAO();
        loadAllPosts();
    }

    public String createPost() {
        if (title == null || title.trim().isEmpty() ||
                content == null || content.trim().isEmpty()) {
            message = "Title and content are necessary!";
            return "error";
        }

        PostDTO post = new PostDTO(title.trim(), content.trim());

        if (postDAO.save(post)) {
            message = "Post created successfully!";
            title = "";
            content = "";
            loadAllPosts();
            return "success";
        } else {
            message = "Error creating post!";
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
            message = "Post deleted successfully!";
            loadAllPosts();
            return "success";
        } else {
            message = "Error deleting post!";
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public PostDAO getPostDAO() {
        return postDAO;
    }

    public void setPostDAO(PostDAO postDAO) {
        this.postDAO = postDAO;
    }
}
