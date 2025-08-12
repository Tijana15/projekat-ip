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

    // CRUD metode
    public String createPromotion() {
        if (title == null || title.trim().isEmpty() ||
                description == null || description.trim().isEmpty() ||
                startDateStr == null || startDateStr.trim().isEmpty() ||
                endDateStr == null || endDateStr.trim().isEmpty()) {
            message = "Svi podaci su obavezni!";
            return "error";
        }

        try {
            LocalDateTime startDate = LocalDateTime.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime endDate = LocalDateTime.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            if (endDate.isBefore(startDate)) {
                message = "Datum završetka mora biti nakon datuma početka!";
                return "error";
            }

            PromotionDTO promotion = new PromotionDTO(title.trim(), description.trim(), startDate, endDate);

            if (promotionDAO.save(promotion)) {
                message = "Promocija je uspješno kreirana!";
                clearForm();
                loadAllPromotions();
                return "success";
            } else {
                message = "Greška pri kreiranju promocije!";
                return "error";
            }
        } catch (DateTimeParseException e) {
            message = "Neispravna format datuma!";
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
            message = "Promocija je uspješno obrisana!";
            loadAllPromotions();
            return "success";
        } else {
            message = "Greška pri brisanju promocije!";
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
}
