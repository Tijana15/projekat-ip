package org.unibl.etf.promotionsapp.beans;

import org.unibl.etf.promotionsapp.dao.PromotionDAO;
import org.unibl.etf.promotionsapp.dto.PromotionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PromotionBean {
    private Long id;
    private String title;
    private String description;
    private String startDateStr;
    private String endDateStr;

    private String searchTerm;
    private String message;
    private PromotionDAO promotionDAO;
    private List<PromotionDTO> promotions;

    public PromotionBean() {
        this.promotionDAO = new PromotionDAO();
        loadAllPromotions();
    }

    public String createPromotion() {
        if (title == null || title.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                startDateStr == null || startDateStr.trim().isEmpty() ||
                endDateStr == null || endDateStr.trim().isEmpty()) {
            message = "All data must be filled!";
            return "error";
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            if (endDate.isBefore(startDate)) {
                message = "Date of ending has to be after day of starting!";
                return "error";
            }

            PromotionDTO promotion = new PromotionDTO(title.trim(), description.trim(), startDate, endDate);

            if (promotionDAO.save(promotion)) {
                message = "Promotion created successfully!";
                clearForm();
                loadAllPromotions();
                return "success";
            } else {
                message = "Error creating promotion!";
                return "error";
            }
        } catch (DateTimeParseException e) {
            message = "Bad form date!";
            return "error";
        }
    }

    public void loadAllPromotions() {
        promotions = promotionDAO.findAll();
    }

    public String searchPromotions() {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            loadAllPromotions();
        } else {
            promotions = promotionDAO.searchByContent(searchTerm.trim());
        }
        return "success";
    }

    public String deletePromotion(Long id) {
        if (promotionDAO.delete(id)) {
            message = "Promotion successfully deleted!";
            loadAllPromotions();
            return "success";
        } else {
            message = "Error deleting promotion!";
            return "error";
        }
    }

    private void clearForm() {
        title = "";
        description = "";
        startDateStr = "";
        endDateStr = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<PromotionDTO> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionDTO> promotions) {
        this.promotions = promotions;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public PromotionDAO getPromotionDAO() {
        return promotionDAO;
    }

    public void setPromotionDAO(PromotionDAO promotionDAO) {
        this.promotionDAO = promotionDAO;
    }
}
